package ru.vo1d.web.data.filters

import kotlin.reflect.KClass
import kotlin.reflect.cast
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties

abstract class Filters<T : Filters<T>>(private val klass: KClass<T>) {
    fun areEmpty(): Boolean {
        val casted = klass.cast(this)
        return klass::declaredMemberProperties.get().all { it.get(casted) == null }
    }

    interface Builder<T : Filters<T>> {
        fun build(): T
    }
}

inline fun <T : Filters<T>, reified B : Filters.Builder<T>> filters(action: B.() -> Unit) =
    B::class.createInstance().apply(action).build()