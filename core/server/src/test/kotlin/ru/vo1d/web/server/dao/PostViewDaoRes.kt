package ru.vo1d.web.server.dao

import ru.vo1d.web.data.dao.PostViewDao
import ru.vo1d.web.entities.qna.post.PostView

class PostViewDaoRes: PostViewDao, JsonDao {
    override suspend fun page(offset: Long, limit: Int): List<PostView> {
        TODO("Not yet implemented")
    }
}