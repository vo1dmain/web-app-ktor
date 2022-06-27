package ru.vo1d.web.app.data.dao.delegates

import ru.vo1d.web.app.data.dao.CrudDao
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.full.createInstance

inline fun <reified T : CrudDao<*, *>> dao(): ReadOnlyProperty<Any, T> {
    return ReadOnlyProperty { _, _ -> T::class.createInstance() }
}