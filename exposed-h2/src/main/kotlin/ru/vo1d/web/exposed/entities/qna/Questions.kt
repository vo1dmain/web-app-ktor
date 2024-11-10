package ru.vo1d.web.exposed.entities.qna

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

internal object Questions : IntIdTable() {
    val theme = varchar("theme", 64)
    val body = varchar("body", 1024)
    val acceptorId = integer("acceptorId")
    val email = varchar("email", 128).nullable()
    val dateTime = datetime("dateTime").defaultExpression(CurrentDateTime)
    val timeZone = varchar("timeZone", 32).default(TimeZone.currentSystemDefault().id)
}

internal class QuestionEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<QuestionEntity>(Questions)

    val theme by Questions.theme
    val body by Questions.body
    val acceptorId by Questions.acceptorId
    val email by Questions.email
    val dateTime by Questions.dateTime
    val timeZone by Questions.timeZone
}