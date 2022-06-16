package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.clampedSubList
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.entities.qna.post.Post
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class PostDaoRes : PostDao, JsonDao, AllDaoTest<Post> {
    private val posts = this.javaClass.getResource("/posts_list.json")!!

    override suspend fun create(item: Post): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Post? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<Post> {
        return all().clampedSubList(offset, limit)
    }

    override suspend fun all(): List<Post> {
        return posts.open {
            json.decodeFromStream(this)
        }
    }
}