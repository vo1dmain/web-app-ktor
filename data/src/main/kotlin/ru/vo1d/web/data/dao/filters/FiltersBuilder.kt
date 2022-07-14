package ru.vo1d.web.data.dao.filters

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

abstract class FiltersBuilder<T: Filters<T>>(private val klass: KClass<T>) {
    operator fun invoke(init: T.() -> Unit): T = klass.primaryConstructor!!.let {
        it.isAccessible = true
        it.call().apply(init)
    }
}