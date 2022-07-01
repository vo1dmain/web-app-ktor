package ru.vo1d.web.data.dao.filters

interface FiltersBuilder<T: Filters> {
    fun build(): T
}