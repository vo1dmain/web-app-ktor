package ru.vo1d.web.exposed.entities

interface HasModel<out T> {
    fun toModel(): T
}