package ru.vo1d.web.orm.entities.qna

import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption.CASCADE
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.post.PostWithData
import ru.vo1d.web.orm.entities.HasModel
import ru.vo1d.web.orm.entities.HasView

object Posts : IntIdTable() {
    val questionId = reference("questionId", Questions, CASCADE, CASCADE)
    val answerId = reference("answerId", Answers, CASCADE, CASCADE)
}

class PostWithDataEntity(id: EntityID<Int>) : IntEntity(id), HasModel<PostWithData>, HasView<PostView> {
    companion object : IntEntityClass<PostWithDataEntity>(Posts)

    val question by QuestionEntity referencedOn Posts.questionId
    val answer by AnswerEntity referencedOn Posts.answerId

    override fun toModel() = PostWithData(id.value, question.toModel(), answer.toModel())

    override fun toView() = PostView(
        id.value,
        question.id.value,
        question.dateTime,
        TimeZone.of(question.timeZone),
        question.theme,
        answer.dateTime,
        TimeZone.of(answer.timeZone)
    )
}