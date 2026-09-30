package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.PostRepository
import ru.vo1d.web.domain.qna.post.PostView

class FakePostRepository : PostRepository {
    private val posts = TestData.posts

    override suspend fun find(page: PageRequest) = posts.page(page).map {
        PostView(it.id, it.question.id, it.question.dateTime, it.question.theme, it.answer.dateTime)
    }

    override suspend fun get(id: Int) = posts.firstOrNull { it.id == id }
}
