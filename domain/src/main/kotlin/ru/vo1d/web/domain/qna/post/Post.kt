package ru.vo1d.web.domain.qna.post

import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.question.Question

/**
 * A published question together with its answer.
 */
data class Post(
    val id: Int,
    val question: Question,
    val answer: Answer
)
