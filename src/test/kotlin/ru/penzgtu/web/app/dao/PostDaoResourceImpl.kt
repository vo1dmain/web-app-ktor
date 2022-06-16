package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.entities.qna.post.Post
import ru.penzgtu.web.app.data.entities.qna.post.PostView
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class PostDaoResourceImpl: PostDao {
    private val posts = this.javaClass.getResource("/posts.json")!!

    private val json = Json { ignoreUnknownKeys = true }

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

    override suspend fun list(offset: Int, limit: Int): List<PostView> {
        return posts.open {
            json.decodeFromStream(this)
        }
    }
}