package ru.penzgtu.web.app.exposed.orm.qna

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.penzgtu.web.app.exposed.orm.HasModel
import ru.penzgtu.web.entities.qna.answers.AnswerModel

object Answers : IntIdTable() {
    val questionId = reference("questionId", Questions, CASCADE, CASCADE)
    val body = varchar("body", 1024)
    val dateTime = long("dateTime")
}

class Answer(id: EntityID<Int>) : IntEntity(id), HasModel<AnswerModel> {
    companion object : IntEntityClass<Answer>(Answers)

    val questionId by Answers.questionId
    val body by Answers.body
    val dateTime by Answers.dateTime

    override fun model() = AnswerModel(id.value, questionId.value, body, dateTime)
}