package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.Filters

sealed interface CreateDao<PK, I> {
    suspend fun create(item: I): PK
}

sealed interface ReadDao<PK, I> {
    suspend fun read(id: PK): I?
}

sealed interface UpdateDao<I> {
    suspend fun update(item: I): Int
}

sealed interface DeleteDao<PK> {
    suspend fun delete(id: PK): Int
}



sealed interface CudDao<PK, I> : CreateDao<PK, I>, UpdateDao<I>, DeleteDao<PK>
sealed interface CrudDao<PK, I> : CreateDao<PK, I>, ReadDao<PK, I>, UpdateDao<I>, DeleteDao<PK>



sealed interface ListDao<I> {
    suspend fun list(offset: Long, limit: Int): List<I>
}

sealed interface AllDao<I> {
    suspend fun all(): List<I>
}

sealed interface FilterDao<I, F : Filters<F>> {
    suspend fun filter(filters: F, offset: Long, limit: Int): List<I>
}


