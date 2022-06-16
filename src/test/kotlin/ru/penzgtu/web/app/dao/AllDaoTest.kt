package ru.penzgtu.web.app.dao

sealed interface AllDaoTest<I> {
    suspend fun all(): List<I>
}