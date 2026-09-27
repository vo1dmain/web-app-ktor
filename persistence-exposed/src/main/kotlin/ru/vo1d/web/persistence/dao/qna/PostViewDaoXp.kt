package ru.vo1d.web.persistence.dao.qna

import ru.vo1d.web.domain.dao.PostViewDao
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.dao.XpDao
import ru.vo1d.web.persistence.entities.qna.PostEntity
import ru.vo1d.web.persistence.mappers.toView

class PostViewDaoXp(ctx: DbContext) : XpDao(ctx.qna), PostViewDao {
    override suspend fun page(offset: Long, limit: Int): List<PostView> = query {
        PostEntity.all()
            .limit(limit)
            .offset(offset)
            .map(PostEntity::toView)
    }
}
