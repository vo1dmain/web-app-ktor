package ru.vo1d.web.app.exposed.orm

interface HasModel<T> {
    fun toModel(): T
}