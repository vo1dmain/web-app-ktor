package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.QuestionRepository
import ru.vo1d.web.domain.qna.question.NewQuestion

class FakeQuestionRepository : QuestionRepository {
    private val questions = TestData.questions

    override suspend fun find(page: PageRequest) = questions.page(page)

    override suspend fun get(id: Int) = questions.firstOrNull { it.id == id }

    override suspend fun add(question: NewQuestion): Int = throw UnsupportedOperationException("Test data is read-only")
}
