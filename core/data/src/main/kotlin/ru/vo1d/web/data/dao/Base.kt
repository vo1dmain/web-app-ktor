package ru.vo1d.web.data.dao

interface Dao<PK, I> {
    suspend fun create(item: I): PK?

    suspend fun create(vararg items: I): Int

    suspend fun read(id: PK): I?

    suspend fun update(item: I): Int

    suspend fun delete(vararg items: I): Int
}

interface Pageable<out I> {
    suspend fun page(offset: Long, limit: Int): List<I>
}

interface Filterable<in F, out I> {
    suspend fun filter(filters: F, offset: Long, limit: Int): List<I>
}

interface AllDao<out I> {
    suspend fun all(): List<I>
}


