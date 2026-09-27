package ru.vo1d.web.api.dao

import ru.vo1d.web.domain.dao.PostViewDao
import ru.vo1d.web.domain.qna.post.PostView

class PostViewDaoRes: PostViewDao, JsonDao {
    override suspend fun page(offset: Long, limit: Int): List<PostView> {
        TODO("Not yet implemented")
    }
}