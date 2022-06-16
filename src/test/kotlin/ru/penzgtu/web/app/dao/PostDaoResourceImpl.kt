package ru.penzgtu.web.app.dao

import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.entities.qna.Post

class PostDaoResourceImpl: PostDao {
    override suspend fun list(offset: Int, limit: Int): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun create(item: Post): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Post? {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}