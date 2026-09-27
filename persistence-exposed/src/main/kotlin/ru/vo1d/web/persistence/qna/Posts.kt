package ru.vo1d.web.persistence.qna

import org.jetbrains.exposed.v1.core.ReferenceOption.CASCADE
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

internal object Posts : IntIdTable() {
    val questionId = reference("questionId", Questions, CASCADE, CASCADE)
    val answerId = reference("answerId", Answers, CASCADE, CASCADE)
}

internal class PostEntity(id: EntityID<Int>) : IntEntity(id){
    companion object : IntEntityClass<PostEntity>(Posts)

    val question by QuestionEntity referencedOn Posts.questionId
    val answer by AnswerEntity referencedOn Posts.answerId
}