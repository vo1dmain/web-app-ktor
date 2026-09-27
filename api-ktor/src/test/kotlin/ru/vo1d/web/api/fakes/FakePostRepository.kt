package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.PostRepository
import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.domain.qna.question.Question
import kotlin.time.Clock

class FakePostRepository : PostRepository {
    override suspend fun find(page: PageRequest): List<PostView> {
        val questions = Fixtures.list("/data/questions.json", Question.serializer())
        val answers = Fixtures.list("/data/answers.json", Answer.serializer())

        return all().page(page).map { post ->
            val question = questions.first { it.id == post.questionId }
            val answer = answers.first { it.id == post.answerId }
            PostView(
                post.id!!,
                question.id!!,
                question.dateTime ?: Clock.System.now(),
                question.theme,
                answer.dateTime ?: Clock.System.now(),
            )
        }
    }

    override suspend fun get(id: Int) = all().firstOrNull { it.id == id }

    private suspend fun all() = Fixtures.list("/data/posts.json", Post.serializer())
}
