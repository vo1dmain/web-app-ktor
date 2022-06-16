package ru.penzgtu.web.app.data.dao

interface FiltersBuilder<T: Filters> {
    fun build(): T
}