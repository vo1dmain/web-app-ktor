package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.QuestionRepository
import ru.vo1d.web.domain.qna.question.Question

class FakeQuestionRepository : QuestionRepository {
    override suspend fun find(page: PageRequest) = all().page(page)

    override suspend fun get(id: Int) = all().firstOrNull { it.id == id }

    override suspend fun add(question: Question): Int = throw UnsupportedOperationException("Fixtures are read-only")

    private suspend fun all() = Fixtures.list("/data/questions.json", Question.serializer())
}
