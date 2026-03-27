package ru.vo1d.web.exposed.dao.qna

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.data.dao.PostDao
import ru.vo1d.web.entities.qna.post.Post
import ru.vo1d.web.exposed.entities.qna.PostEntity
import ru.vo1d.web.exposed.entities.qna.Posts
import ru.vo1d.web.exposed.mappers.mapItem
import ru.vo1d.web.exposed.mappers.toDomain

class PostDaoXp : PostDao {
    override suspend fun create(item: Post): Int {
        return Posts.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Post): Int {
        return Posts.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Post? {
        return PostEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Post): Int {
        return Posts.update({ Posts.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Post): Int {
        return Posts.deleteWhere { Posts.id inList items.mapNotNull { it.id } }
    }
}