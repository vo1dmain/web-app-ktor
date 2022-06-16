package ru.penzgtu.web.app.data.repos

interface ListRepo {
    val startPage get() = defaultPage
    val limit get() = defaultLimit

    fun offset(page: Int?): Int {
        val p = page ?: startPage
        return (p - 1) * limit
    }

    companion object {
        const val defaultPage = 1
        const val defaultLimit = 10
    }
}