package ru.vo1d.web.exposed.entities.qna

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE

internal object Posts : IntIdTable() {
    val questionId = reference("questionId", Questions, CASCADE, CASCADE)
    val answerId = reference("answerId", Answers, CASCADE, CASCADE)
}

internal class PostEntity(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<PostEntity>(Posts)

    val question by QuestionEntity referencedOn Posts.questionId
    val answer by AnswerEntity referencedOn Posts.answerId
}