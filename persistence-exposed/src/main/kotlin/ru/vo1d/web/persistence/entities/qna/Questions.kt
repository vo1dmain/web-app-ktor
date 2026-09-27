package ru.vo1d.web.persistence.entities.qna

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentTimestamp
import org.jetbrains.exposed.v1.datetime.timestamp

internal object Questions : IntIdTable() {
    val theme = varchar("theme", 64)
    val body = varchar("body", 1024)
    val acceptorId = integer("acceptorId")
    val email = varchar("email", 128).nullable()
    val dateTime = timestamp("dateTime").defaultExpression(CurrentTimestamp)
}

internal class QuestionEntity(id: EntityID<Int>) : IntEntity(id = id) {
    companion object : IntEntityClass<QuestionEntity>(Questions)

    val theme by Questions.theme
    val body by Questions.body
    val acceptorId by Questions.acceptorId
    val email by Questions.email
    val dateTime by Questions.dateTime
}