package ru.vo1d.web.exposed.entities.qna

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.v1.core.ReferenceOption.CASCADE
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime

internal object Answers : IntIdTable() {
    val questionId = reference("questionId", Questions, CASCADE, CASCADE)
    val body = varchar("body", 1024)
    val dateTime = datetime("dateTime").defaultExpression(CurrentDateTime)
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

internal class AnswerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AnswerEntity>(Answers)

    val questionId by Answers.questionId
    val body by Answers.body
    val dateTime by Answers.dateTime
    val timeZone by Answers.timeZone
}