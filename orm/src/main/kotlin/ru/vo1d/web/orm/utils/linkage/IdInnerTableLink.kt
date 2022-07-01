@file:Suppress("UNCHECKED_CAST", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package ru.vo1d.web.orm.utils.linkage

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.entityCache
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class IdInnerTableLink<LID : Comparable<LID>, SID : Comparable<SID>, Source : Entity<SID>, ID : Comparable<ID>, Target : Entity<ID>>(
    val table: IdTable<LID>,
    val target: EntityClass<ID, Target>,
    private val sourceColumn: Column<EntityID<SID>>? = null,
    targetColumn: Column<EntityID<ID>>? = null
) : ReadOnlyProperty<Source, SizedIterable<Target>> {
    init {
        targetColumn?.let {
            requireNotNull(sourceColumn) { "Both source and target columns should be specified" }
            require(targetColumn.referee?.table == target.table) {
                "Column $targetColumn point to wrong table, expected ${target.table.tableName}"
            }
            require(targetColumn.table == sourceColumn.table) {
                "Both source and target columns should be from the same table"
            }
        }
        sourceColumn?.let {
            requireNotNull(targetColumn) { "Both source and target columns should be specified" }
        }
    }

    internal val targetRefColumn = targetColumn
        ?: table.columns.singleOrNull { it.referee == target.table.id } as? Column<EntityID<ID>>
        ?: error("Table does not reference target")

    private fun sourceRefColumn(o: Source) = sourceColumn
        ?: table.columns.singleOrNull { it.referee == o.klass.table.id } as? Column<EntityID<SID>>
        ?: error("Table does not reference source")


    override operator fun getValue(thisRef: Source, property: KProperty<*>): SizedIterable<Target> {
        if (thisRef.id._value == null && !thisRef.isNewEntity()) return emptySized()
        val sourceRefColumn = sourceRefColumn(thisRef)
        val transaction = TransactionManager.currentOrNull()
            ?: return thisRef.getReferenceFromCache(sourceRefColumn)
        val alreadyInJoin = (target.dependsOnTables as? Join)?.alreadyInJoin(table) ?: false
        val entityTables =
            if (alreadyInJoin) target.dependsOnTables else target.dependsOnTables.join(
                table,
                JoinType.INNER,
                target.table.id,
                targetRefColumn
            )

        val columns = (
                target.dependsOnColumns + (if (!alreadyInJoin) table.columns else emptyList()) -
                        sourceRefColumn
                ).distinct() + sourceRefColumn

        val query = { target.wrapRows(entityTables.slice(columns).select { sourceRefColumn eq thisRef.id }) }
        return transaction.entityCache.getOrPutReferrers(thisRef.id, sourceRefColumn, query).also {
            thisRef.storeReferenceInCache(sourceRefColumn, it)
        }
    }
}

