package ru.vo1d.web.app.data.dao

interface FiltersBuilder<T: Filters> {
    fun build(): T
}