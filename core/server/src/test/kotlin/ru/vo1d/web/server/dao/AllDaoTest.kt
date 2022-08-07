package ru.vo1d.web.server.dao

sealed interface AllDaoTest<I> {
    suspend fun all(): List<I>
}