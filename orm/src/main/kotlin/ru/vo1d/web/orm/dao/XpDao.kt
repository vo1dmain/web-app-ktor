package ru.vo1d.web.orm.dao

import org.jetbrains.exposed.sql.statements.UpdateBuilder

interface XpDao<I> {
    fun UpdateBuilder<*>.mapItem(item: I)
}