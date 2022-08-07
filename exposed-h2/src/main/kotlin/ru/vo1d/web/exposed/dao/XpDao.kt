package ru.vo1d.web.exposed.dao

import org.jetbrains.exposed.sql.statements.UpdateBuilder

interface XpDao<I> {
    fun UpdateBuilder<*>.mapItem(item: I)
}