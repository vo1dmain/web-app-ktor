package ru.vo1d.web.entities.qna.post

import kotlinx.serialization.Serializable
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.entities.qna.question.Question

@Serializable
data class PostWithData(val id: Int, val question: Question, val answer: Answer)