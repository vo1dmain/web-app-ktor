package ru.vo1d.web.domain.qna

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.question.NewQuestion
import ru.vo1d.web.domain.qna.question.Question

interface QuestionRepository {
    suspend fun find(page: PageRequest): List<Question>

    suspend fun get(id: Int): Question?

    /** @return id of the stored question */
    suspend fun add(question: NewQuestion): Int
}
