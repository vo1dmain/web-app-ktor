package ru.vo1d.web.exposed.utils.linkage

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.IdTable
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
}