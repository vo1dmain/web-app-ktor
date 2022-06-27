package ru.vo1d.web.app.data.dao

sealed interface CrudDao<I, PK> {
    suspend fun create(item: I): PK
    suspend fun read(id: PK): I?
    suspend fun update(item: I): Int
    suspend fun delete(id: PK): Int
}

sealed interface ListDao<I> {
    suspend fun list(offset: Long, limit: Int): List<I>
}

sealed interface AllDao<I> {
    suspend fun all(): List<I>
}

sealed interface FilterDao<I, F : Filters> {
    suspend fun filter(filters: F, offset: Long, limit: Int): List<I>
}


