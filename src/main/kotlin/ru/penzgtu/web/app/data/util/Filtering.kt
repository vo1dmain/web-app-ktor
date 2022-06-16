package ru.penzgtu.web.app.data.util

class FilterParams internal constructor() {
    private val map = mutableMapOf<String, Any?>()

    operator fun get(key: String): Any? {
        return map[key]
    }

    internal fun put(key: String, value: Any?) {
        map[key] = value
    }

    fun string(key: String): String? {
        return get(key) as? String
    }

    fun int(key: String): Int? {
        return get(key) as? Int
    }

    fun boolean(key: String): Boolean? {
        return get(key) as? Boolean
    }

    fun float(key: String): Float? {
        return get(key) as? Float
    }
}

fun filterParamsOf(vararg params: Pair<String, Any?>) = FilterParams().apply {
    for ((key, value) in params) {
        put(key, value)
    }
}