package ru.vo1d.web.data.repos

interface ListRepo {
    val startPage get() = DEFAULT_PAGE
    val limit get() = DEFAULT_LIMIT

    fun offset(page: Int?): Long {
        val p = page ?: startPage
        return ((p - 1) * limit).toLong()
    }

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_LIMIT = 10
    }
}