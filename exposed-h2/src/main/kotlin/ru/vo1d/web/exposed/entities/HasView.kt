package ru.vo1d.web.exposed.entities

interface HasView<out T> {
    fun toView(): T
}