package ru.vo1d.web.data.dao.filters

import kotlin.reflect.KClass
import kotlin.reflect.cast
import kotlin.reflect.full.declaredMemberProperties

abstract class Filters<T : Filters<T>>(private val klass: KClass<T>) {
    fun areEmpty() = klass::declaredMemberProperties.get().all { it.get(klass.cast(this)) == null }
}