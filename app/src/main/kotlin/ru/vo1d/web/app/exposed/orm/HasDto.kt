package ru.vo1d.web.app.exposed.orm

interface HasDto<T> {
    fun toDto(): T
}