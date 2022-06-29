package ru.vo1d.web.app.data.dao.filters

interface FiltersBuilder<T: Filters> {
    fun build(): T
}