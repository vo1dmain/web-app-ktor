package ru.vo1d.web.data.dao.delegates

import ru.vo1d.web.data.dao.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.full.createInstance

inline fun <reified T> crudDao(): ReadOnlyProperty<Any, T>
        where T : CreateDao<*, *>, T : ReadDao<*, *>, T : UpdateDao<*>, T : DeleteDao<*> {
    return ReadOnlyProperty { _, _ -> T::class.createInstance() }
}

inline fun <reified T> dao(): ReadOnlyProperty<Any, T>
        where T : Dao {
    return ReadOnlyProperty { _, _ -> T::class.createInstance() }
}