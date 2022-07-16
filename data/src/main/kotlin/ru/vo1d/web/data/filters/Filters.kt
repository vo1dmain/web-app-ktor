package ru.vo1d.web.data.filters

import kotlin.reflect.KClass
import kotlin.reflect.cast
import kotlin.reflect.full.declaredMemberProperties

abstract class Filters<T : Filters<T>>(private val klass: KClass<T>) {
    fun areEmpty(): Boolean {
        val casted = klass.cast(this)
        return klass::declaredMemberProperties.get().all { it.get(casted) == null }
    }
}