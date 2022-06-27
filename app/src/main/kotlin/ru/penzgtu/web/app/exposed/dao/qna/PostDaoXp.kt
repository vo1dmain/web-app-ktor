package ru.penzgtu.web.app.exposed.dao.qna

import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.update
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.exposed.orm.qna.Post
import ru.penzgtu.web.app.exposed.orm.qna.Posts
import ru.penzgtu.web.entities.qna.post.PostModel
import ru.penzgtu.web.entities.qna.post.PostView

class PostDaoXp : PostDao {
    override suspend fun create(item: PostModel): Int {
        return Posts.insertAndGetId {
            it[questionId] = item.question.id!!
            it[answerId] = item.answer.id!!
        }.value
    }

    override suspend fun read(id: Int): PostModel? {
        return Post.findById(id)?.model()
    }

    override suspend fun update(item: PostModel): Int {
        return Posts.update({ Posts.id eq item.id }) {
            it[questionId] = item.question.id!!
            it[answerId] = item.answer.id!!
        }
    }

    override suspend fun delete(id: Int): Int {
        return Posts.deleteWhere {
            Posts.id eq id
        }
    }

    override suspend fun list(offset: Long, limit: Int): List<PostView> {
        return Post.all().limit(limit, offset).map(Post::toView)
    }
}