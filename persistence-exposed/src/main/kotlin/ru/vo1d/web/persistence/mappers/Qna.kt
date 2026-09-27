package ru.vo1d.web.persistence.mappers

import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.domain.qna.question.Question
import ru.vo1d.web.persistence.entities.qna.*

internal fun UpdateBuilder<*>.mapItem(item: Answer) {
    this[Answers.questionId] = item.questionId
    this[Answers.body] = item.body
    item.dateTime?.let { this[Answers.dateTime] = it }
}

internal fun UpdateBuilder<*>.mapItem(item: Post) {
    this[Posts.questionId] = item.questionId
    this[Posts.answerId] = item.answerId
}

internal fun UpdateBuilder<*>.mapItem(item: Question) {
    this[Questions.theme] = item.theme
    this[Questions.body] = item.body
    this[Questions.acceptorId] = item.acceptorId
    this[Questions.email] = item.email
    item.dateTime?.let { this[Questions.dateTime] = it }
}


internal fun AnswerEntity.toDomain(): Answer {
    return Answer(id.value, questionId.value, body, dateTime)
}

internal fun PostEntity.toDomain() = Post(id.value, question.id.value, answer.id.value)

internal fun PostEntity.toView() = PostView(
    id.value,
    question.id.value,
    question.dateTime,
    question.theme,
    answer.dateTime
)

internal fun QuestionEntity.toDomain() =
    Question(id.value, theme, body, acceptorId, email, dateTime)