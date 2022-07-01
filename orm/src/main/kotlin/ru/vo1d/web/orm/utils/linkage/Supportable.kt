package ru.vo1d.web.orm.utils.linkage

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

interface Supportable<ID : Comparable<ID>> {
    infix fun <LID : Comparable<LID>, TID : Comparable<TID>, T : Entity<TID>> EntityClass<TID, T>.link(table: IdTable<LID>) =
        IdInnerTableLink<LID, ID, Entity<ID>, TID, T>(table, this@link)

    infix fun <LID : Comparable<LID>, TID : Comparable<TID>, T : Entity<TID>> IdInnerTableLink<LID, ID, Entity<ID>, TID, T>.supportedBy(
        supportTable: Table
    ) = SupportedInnerTableLink<ID, Entity<ID>, TID, T, LID>(
        this.table,
        supportTable,
        this.target
    )

    fun <LID : Comparable<LID>, TID : Comparable<TID>, T : Entity<TID>> IdInnerTableLink<LID, ID, Entity<ID>, TID, T>.supportedBy(
        linkedColumn: Column<EntityID<LID>>,
        sourceColumn: Column<EntityID<ID>>
    ) = SupportedInnerTableLink(
        this.table,
        sourceColumn.table,
        this.target,
        sourceColumn,
        this.targetRefColumn,
        linkedColumn
    )
}