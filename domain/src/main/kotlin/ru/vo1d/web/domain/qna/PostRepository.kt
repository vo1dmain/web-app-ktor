package ru.vo1d.web.domain.qna

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView

interface PostRepository {
    suspend fun find(page: PageRequest): List<PostView>

    suspend fun get(id: Int): Post?
}
