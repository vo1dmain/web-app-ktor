package ru.vo1d.web.orm.entities

interface HasView<T> {
    fun toView(): T
}