package ru.vo1d.web.exposed.mappers

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.entities.qna.post.Post
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.Question
import ru.vo1d.web.exposed.entities.qna.*

internal fun UpdateBuilder<*>.mapItem(item: Answer) {
    this[Answers.questionId] = item.questionId
    this[Answers.body] = item.body
    item.dateTime?.let { this[Answers.dateTime] = it }
    item.timeZone?.let { this[Answers.timeZone] = it.id }
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
    item.timeZone?.let { this[Questions.timeZone] = it.id }
}


internal fun AnswerEntity.toDomain(): Answer {
    return Answer(id.value, questionId.value, body, dateTime, TimeZone.of(timeZone))
}

internal fun PostEntity.toDomain() = Post(id.value, question.id.value, answer.id.value)

internal fun PostEntity.toView() = PostView(
    id.value,
    question.id.value,
    question.dateTime,
    TimeZone.of(question.timeZone),
    question.theme,
    answer.dateTime,
    TimeZone.of(answer.timeZone)
)

internal fun QuestionEntity.toDomain() =
    Question(id.value, theme, body, acceptorId, email, dateTime, TimeZone.of(timeZone))