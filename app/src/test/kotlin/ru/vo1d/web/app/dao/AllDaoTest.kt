package ru.vo1d.web.app.dao

sealed interface AllDaoTest<I> {
    suspend fun all(): List<I>
}