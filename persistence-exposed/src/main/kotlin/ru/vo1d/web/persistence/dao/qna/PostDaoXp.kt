package ru.vo1d.web.persistence.dao.qna

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.update
import ru.vo1d.web.domain.dao.PostDao
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.qna.PostEntity
import ru.vo1d.web.persistence.entities.qna.Posts
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class PostDaoXp(ctx: DbContext) : XpDao(ctx.qna), PostDao {
    override suspend fun create(item: Post): Int = query {
        Posts.insertAndGetId { it.mapItem(item) }.value
    }

    override suspend fun create(vararg items: Post): Int = query {
        Posts.batchInsert(items.asIterable()) { mapItem(it) }.count()
    }

    override suspend fun read(id: Int): Post? = query {
        PostEntity.findById(id)?.toDomain()
    }

    override suspend fun update(item: Post): Int = query {
        Posts.update({ Posts.id eq item.id }) { it.mapItem(item) }
    }

    override suspend fun delete(vararg items: Post): Int = query {
        Posts.deleteWhere { Posts.id inList items.mapNotNull { it.id } }
    }
}
