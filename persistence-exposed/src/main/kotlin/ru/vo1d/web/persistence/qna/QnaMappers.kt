package ru.vo1d.web.persistence.qna

import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.domain.qna.question.NewQuestion
import ru.vo1d.web.domain.qna.question.Question

internal fun UpdateBuilder<*>.mapItem(item: NewQuestion) {
    this[Questions.theme] = item.theme
    this[Questions.body] = item.body
    this[Questions.acceptorId] = item.acceptorId
    this[Questions.email] = item.email
}

internal fun AnswerEntity.toDomain(): Answer {
    return Answer(id.value, questionId.value, body, dateTime)
}

internal fun PostEntity.toDomain() = Post(id.value, question.toDomain(), answer.toDomain())

internal fun PostEntity.toView() = PostView(
    id.value,
    question.id.value,
    question.dateTime,
    question.theme,
    answer.dateTime
)

internal fun QuestionEntity.toDomain() =
    Question(id.value, theme, body, acceptorId, email, dateTime)