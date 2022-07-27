package ru.vo1d.web.orm.entities

interface HasModel<out T> {
    fun toModel(): T
}