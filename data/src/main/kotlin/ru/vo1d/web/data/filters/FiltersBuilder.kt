package ru.vo1d.web.data.filters

import kotlin.reflect.full.createInstance

interface FiltersBuilder<T : Filters<T>> {
    fun build(): T
}

inline fun <T : Filters<T>, reified B : FiltersBuilder<T>> filters(action: B.() -> Unit) =
    B::class.createInstance().apply(action).build()
