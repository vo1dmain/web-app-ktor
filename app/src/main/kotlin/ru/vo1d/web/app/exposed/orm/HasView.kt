package ru.vo1d.web.app.exposed.orm

interface HasView<T> {
    fun toView(): T
}