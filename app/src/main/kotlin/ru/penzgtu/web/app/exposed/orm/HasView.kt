package ru.penzgtu.web.app.exposed.orm

interface HasView<T> {
    fun toView(): T
}