package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.util.Filters

sealed interface CrudDao<I, PK> {
    suspend fun create(item: I): PK
    suspend fun read(id: PK): I?
    suspend fun update(item: I)
    suspend fun delete(id: PK)
}

sealed interface ListDao<I> {
    suspend fun list(offset: Int, limit: Int): List<I>
}

sealed interface AllDao<I> {
    suspend fun all(): List<I>
}

sealed interface FilterDao<I> {
    suspend fun filter(filters: Filters, offset: Int, limit: Int): List<I>
}


