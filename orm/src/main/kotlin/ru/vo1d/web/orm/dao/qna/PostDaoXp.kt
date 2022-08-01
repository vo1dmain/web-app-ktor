package ru.vo1d.web.orm.dao.qna

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.PostDao
import ru.vo1d.web.entities.qna.post.Post
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.qna.PostWithDataEntity
import ru.vo1d.web.orm.entities.qna.Posts

class PostDaoXp : PostDao, XpDao<Post> {
    override suspend fun create(item: Post) =
        Posts.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: Post) =
        Posts.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) =
        PostWithDataEntity.findById(id)?.toModel()

    override suspend fun update(item: Post) =
        Posts.update({ Posts.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) =
        Posts.deleteWhere { Posts.id eq id }

    override suspend fun list(offset: Long, limit: Int) =
        PostWithDataEntity.all().limit(limit, offset).map(PostWithDataEntity::toView)

    override fun UpdateBuilder<*>.mapItem(item: Post) {
        this[Posts.questionId] = item.questionId
        this[Posts.answerId] = item.answerId
    }
}