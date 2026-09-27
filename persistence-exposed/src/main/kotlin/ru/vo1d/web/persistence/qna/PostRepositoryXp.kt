package ru.vo1d.web.persistence.qna

import org.jetbrains.exposed.v1.dao.with
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.PostRepository
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext

class PostRepositoryXp(ctx: DbContext) : XpRepository(ctx.qna), PostRepository {
    override suspend fun find(page: PageRequest): List<PostView> = query {
        PostEntity.all()
            .limit(page.size)
            .offset(page.offset)
            .with(PostEntity::question, PostEntity::answer)
            .map(PostEntity::toView)
    }

    override suspend fun get(id: Int): Post? = query {
        PostEntity.findById(id)?.toDomain()
    }
}
