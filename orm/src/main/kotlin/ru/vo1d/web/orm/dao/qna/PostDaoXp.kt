package ru.vo1d.web.orm.dao.qna

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.vo1d.web.data.dao.PostDao
import ru.vo1d.web.entities.qna.post.PostModel
import ru.vo1d.web.orm.entities.qna.Post
import ru.vo1d.web.orm.entities.qna.Posts

class PostDaoXp : PostDao {
    override suspend fun create(item: PostModel) = Posts.insertAndGetId {
        it[questionId] = item.questionId
        it[answerId] = item.answerId
    }.value

    override suspend fun read(id: Int) = Post.findById(id)?.toModel()

    override suspend fun update(item: PostModel) = Posts.update({ Posts.id eq item.id }) {
        it[questionId] = item.questionId
        it[answerId] = item.answerId
    }

    override suspend fun delete(id: Int) = Posts.deleteWhere { Posts.id eq id }

    override suspend fun list(offset: Long, limit: Int) = Post.all().limit(limit, offset).map(Post::toView)
}