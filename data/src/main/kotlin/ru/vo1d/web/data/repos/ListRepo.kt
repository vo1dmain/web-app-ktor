package ru.vo1d.web.data.repos

interface ListRepo {
    val startPage get() = defaultPage
    val limit get() = defaultLimit

    fun offset(page: Int?): Long {
        val p = page ?: startPage
        return ((p - 1) * limit).toLong()
    }

    companion object {
        const val defaultPage = 1
        const val defaultLimit = 10
    }
}