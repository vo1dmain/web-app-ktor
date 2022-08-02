package ru.vo1d.web.orm.entities

interface HasView<out T> {
    fun toView(): T
}