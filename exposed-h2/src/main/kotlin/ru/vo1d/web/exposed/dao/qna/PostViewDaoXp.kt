package ru.vo1d.web.exposed.dao.qna

import ru.vo1d.web.data.dao.PostViewDao
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.exposed.entities.qna.PostEntity
import ru.vo1d.web.exposed.mappers.toView

class PostViewDaoXp : PostViewDao {
    override suspend fun page(offset: Long, limit: Int): List<PostView> {
        return PostEntity.all()
            .limit(limit)
            .offset(offset)
            .map(PostEntity::toView)
    }
}