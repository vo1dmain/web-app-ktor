package ru.vo1d.web.orm.entities

interface HasModel<T> {
    fun toModel(): T
}