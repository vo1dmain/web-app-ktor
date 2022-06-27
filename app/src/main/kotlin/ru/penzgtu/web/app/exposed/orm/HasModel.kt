package ru.penzgtu.web.app.exposed.orm

interface HasModel<T> {
    fun model(): T
}