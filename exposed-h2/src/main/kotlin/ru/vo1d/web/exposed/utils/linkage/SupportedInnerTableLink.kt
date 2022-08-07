@file:Suppress("UNCHECKED_CAST", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package ru.vo1d.web.exposed.utils.linkage

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.entityCache
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SupportedInnerTableLink<SID, Source, TID, Target, LID>(
    private val linkedTable: IdTable<LID>,
    private val supportTable: Table,
    private val target: EntityClass<TID, Target>,
    private val sourceColumn: Column<EntityID<SID>>? = null,
    targetColumn: Column<EntityID<TID>>? = null,
    linkedColumn: Column<EntityID<LID>>? = null
) : ReadOnlyProperty<Source, SizedIterable<Target>>
        where SID : Comparable<SID>,
              Source : Entity<SID>,
              TID : Comparable<TID>,
              Target : Entity<TID>,
              LID : Comparable<LID> {

    init {
        targetColumn?.let {
            requireNotNull(sourceColumn) { "Both source and target columns should be specified" }
            require(it.referee?.table == target.table) {
                "Column $it point to wrong table, expected ${target.table.tableName}"
            }
            require(it.table == linkedTable) {
                "Both source and target columns should be from the same table"
            }
        }

        linkedColumn?.let {
            requireNotNull(sourceColumn) { "Both source and linked columns should be specified" }
            require(it.referee?.table == linkedTable) {
                "Column $it point to wrong table, expected ${linkedTable.tableName}"
            }
            require(it.table == supportTable) {
                "Both source and linked columns should be from the same table"
            }
        }

        sourceColumn?.let {
            requireNotNull(targetColumn) { "Both source and target columns should be specified" }
            requireNotNull(linkedColumn) { "Both source and linked columns should be specified" }
        }
    }

    private val targetColumn = targetColumn
        ?: linkedTable.columns.singleOrNull { it.referee == target.table.id } as? Column<EntityID<TID>>
        ?: error("Linked table does not reference target")

    private val linkedColumn = linkedColumn
        ?: supportTable.columns.singleOrNull { it.referee == linkedTable.id } as? Column<EntityID<LID>>
        ?: error("Support table does not reference linked table")

    private fun sourceRefColumn(source: Source) = sourceColumn
        ?: supportTable.columns.singleOrNull { it.referee == source.klass.table.id } as? Column<EntityID<SID>>
        ?: error("Support table does not reference source")


    override fun getValue(thisRef: Source, property: KProperty<*>): SizedIterable<Target> {
        if (thisRef.id._value == null && !thisRef.isNewEntity()) return emptySized()
        val sourceRefColumn = sourceRefColumn(thisRef)
        val transaction = TransactionManager.currentOrNull() ?: return thisRef.getReferenceFromCache(targetColumn)


        val alreadyInJoin = (target.dependsOnTables as? Join)?.alreadyInJoin(linkedTable) ?: false
        val entityTables = if (alreadyInJoin) target.dependsOnTables else target.dependsOnTables.join(
            linkedTable, JoinType.INNER, target.table.id, targetColumn
        )

        val supportAlreadyInJoin = (entityTables as? Join)?.alreadyInJoin(supportTable) ?: false
        val supportedEntityTables = if (supportAlreadyInJoin) entityTables else entityTables.join(
            supportTable, JoinType.INNER, linkedTable.id, linkedColumn
        )

        val columns = target.dependsOnColumns - sourceRefColumn

        val query = supportedEntityTables.slice(columns)
            .select { sourceRefColumn eq thisRef.id }
            .groupBy(target.table.id)

        val refs = { target.wrapRows(query) }
        return transaction.entityCache.getOrPutReferrers(thisRef.id, targetColumn, refs).also {
            thisRef.storeReferenceInCache(targetColumn, it)
        }
    }
}