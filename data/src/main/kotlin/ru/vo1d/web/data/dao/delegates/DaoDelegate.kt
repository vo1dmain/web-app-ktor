package ru.vo1d.web.data.dao.delegates

import ru.vo1d.web.data.dao.CreateDao
import ru.vo1d.web.data.dao.DeleteDao
import ru.vo1d.web.data.dao.ReadDao
import ru.vo1d.web.data.dao.UpdateDao
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.full.createInstance

inline fun <reified T> dao(): ReadOnlyProperty<Any, T>
        where T : CreateDao<*, *>, T : ReadDao<*, *>, T : UpdateDao<*>, T : DeleteDao<*> {
    return ReadOnlyProperty { _, _ -> T::class.createInstance() }
}