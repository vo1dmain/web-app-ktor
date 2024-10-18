package ru.vo1d.web.data.dao

sealed interface Dao

sealed interface CreateDao<PK, I> : Dao {
    suspend fun create(item: I): PK?

    suspend fun create(vararg items: I): Int
}

sealed interface ReadDao<PK, I> : Dao {
    suspend fun read(id: PK): I?
}

sealed interface UpdateDao<I> : Dao {
    suspend fun update(item: I): Int
}

sealed interface DeleteDao<PK> : Dao {
    suspend fun delete(id: PK): Int
}

sealed interface ListDao<I> : Dao {
    suspend fun list(offset: Long, limit: Int): List<I>
}

sealed interface AllDao<I> : Dao {
    suspend fun all(): List<I>
}

sealed interface FilterDao<I, F> : Dao {
    suspend fun filter(filters: F, offset: Long, limit: Int): List<I>
}

sealed interface LinkedReadDao<PK, I> : Dao {
    suspend fun readLinked(id: PK): I?
}


