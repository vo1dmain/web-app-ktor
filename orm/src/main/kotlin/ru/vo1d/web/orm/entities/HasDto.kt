package ru.vo1d.web.orm.entities

interface HasDto<T> {
    fun toDto(): T
}