package ru.vo1d.web.data.dao.filters

import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

abstract class FiltersCreator<F : Filters, out B : FiltersBuilder<F>>(private val klass: KClass<B>) {
    fun new(block: B.() -> Unit) = klass.createInstance().apply(block).build()
}