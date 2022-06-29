package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.app.data.dao.PostDao
import ru.vo1d.web.app.entities.PostRaw
import ru.vo1d.web.app.extensions.open
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.entities.qna.post.PostDto
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.QuestionModel

@OptIn(ExperimentalSerializationApi::class)
class PostDaoRes : PostDao, JsonDao, AllDaoTest<PostRaw> {
    private val posts = this.javaClass.getResource("/posts_list.json")!!
    private val questions = this.javaClass.getResource("/questions.json")!!
    private val answers = this.javaClass.getResource("/answers.json")!!

    private suspend fun allQuestions(): List<QuestionModel> {
        return questions.open {
            json.decodeFromStream(this)
        }
    }

    private suspend fun allAnswers(): List<AnswerModel> {
        return answers.open {
            json.decodeFromStream(this)
        }
    }

    override suspend fun create(item: PostDto): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): PostDto? {
        return all().map { post ->
            val question = allQuestions().first { it.id == post.questionId }
            val answer = allAnswers().first { it.id == post.answerId }
            PostDto(post.id, question, answer)
        }.firstOrNull { it.id == id }
    }

    override suspend fun update(item: PostDto): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int): List<PostView> {
        return all().clampedSubList(offset.toInt(), limit).map { post ->
            val question = allQuestions().first { it.id == post.questionId }
            val answer = allAnswers().first { it.id == post.answerId }
            PostView(
                post.id,
                question.id!!,
                question.dateTime,
                question.theme,
                answer.dateTime
            )
        }
    }

    override suspend fun all(): List<PostRaw> {
        return posts.open {
            json.decodeFromStream(this)
        }
    }
}