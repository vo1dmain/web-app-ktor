package ru.vo1d.web.orm.dao.qna

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.PostDao
import ru.vo1d.web.entities.qna.post.PostModel
import ru.vo1d.web.orm.dao.XpDao
import ru.vo1d.web.orm.entities.qna.Post
import ru.vo1d.web.orm.entities.qna.Posts

class PostDaoXp : PostDao, XpDao<PostModel> {
    override suspend fun create(item: PostModel) = Posts.insertAndGetId { it.mapItem(item) }.value

    override suspend fun create(vararg items: PostModel) =
        Posts.batchInsert(items.asIterable()) { mapItem(it) }.count()

    override suspend fun read(id: Int) = Post.findById(id)?.toDto()

    override suspend fun update(item: PostModel) = Posts.update({ Posts.id eq item.id }) { it.mapItem(item) }

    override suspend fun delete(id: Int) = Posts.deleteWhere { Posts.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Post.all().limit(limit, offset).map(Post::toView)

    override fun UpdateBuilder<*>.mapItem(item: PostModel) {
        this[Posts.questionId] = item.questionId
        this[Posts.answerId] = item.answerId
    }
}