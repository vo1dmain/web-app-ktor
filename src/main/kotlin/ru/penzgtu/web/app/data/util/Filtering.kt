package ru.penzgtu.web.app.data.util

class Filters internal constructor() {
    private val map = mutableMapOf<String, Any?>()

    operator fun get(key: String): Any? {
        return map[key]
    }

    internal fun put(key: String, value: Any?) {
        if ((value != null) && supportedTypes.any { it.isInstance(value) }.not())
            throw IllegalArgumentException()
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

    fun intArray(key: String): IntArray? {
        return get(key) as? IntArray
    }

    fun floatArray(key: String): FloatArray? {
        return get(key) as? FloatArray
    }

    companion object {
        private val supportedTypes = listOf(
            String::class,
            Number::class,
            Boolean::class,
            IntArray::class,
            FloatArray::class
        )
    }
}

fun filtersOf(vararg params: Pair<String, Any?>) = Filters().apply {
    for ((key, value) in params) {
        put(key, value)
    }
}