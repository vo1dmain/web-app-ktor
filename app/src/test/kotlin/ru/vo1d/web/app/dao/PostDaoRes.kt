package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.data.dao.PostDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.entities.qna.post.PostDto
import ru.vo1d.web.entities.qna.post.PostModel
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.QuestionModel
import ru.vo1d.web.orm.extensions.resource

@OptIn(ExperimentalSerializationApi::class)
class PostDaoRes : PostDao, JsonDao, AllDaoTest<PostModel> {
    private val posts = resource("/posts.json")
    private val questions = resource("/questions.json")
    private val answers = resource("/answers.json")

    private suspend fun allQuestions() = questions.open {
        json.decodeFromStream<List<QuestionModel>>(this)
    }

    private suspend fun allAnswers() = answers.open {
        json.decodeFromStream<List<AnswerModel>>(this)
    }

    override suspend fun create(item: PostModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: PostModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int) = all()
        .map { post ->
            val question = allQuestions().first { it.id == post.questionId }
            val answer = allAnswers().first { it.id == post.answerId }
            PostDto(post.id!!, question, answer)
        }.firstOrNull { it.id == id }


    override suspend fun update(item: PostModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int) = all()
        .clampedSubList(offset.toInt(), limit)
        .map { post ->
            val question = allQuestions().first { it.id == post.questionId }
            val answer = allAnswers().first { it.id == post.answerId }
            PostView(
                post.id!!,
                question.id!!,
                question.dateTime!!,
                question.timeZone!!,
                question.theme,
                answer.dateTime!!,
                answer.timeZone!!
            )
        }

    override suspend fun all() = posts.open {
        json.decodeFromStream<List<PostModel>>(this)
    }
}