package ru.vo1d.web.api.dao

import kotlin.time.Clock
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.domain.dao.AllDao
import ru.vo1d.web.domain.dao.PostDao
import ru.vo1d.web.domain.extensions.open
import ru.vo1d.web.domain.extensions.resource
import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.domain.qna.question.Question
import ru.vo1d.web.api.clampedSubList

@OptIn(ExperimentalSerializationApi::class)
class PostDaoRes : PostDao, JsonDao, AllDao<Post> {
    private val posts = resource("/data/posts.json")
    private val questions = resource("/data/questions.json")
    private val answers = resource("/data/answers.json")

    private suspend fun allQuestions() = questions.open {
        json.decodeFromStream<List<Question>>(this)
    }

    private suspend fun allAnswers() = answers.open {
        json.decodeFromStream<List<Answer>>(this)
    }

    override suspend fun create(item: Post): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: Post): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Post? {
        return all().firstOrNull { it.id == id }
    }


    override suspend fun update(item: Post): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg items: Post): Int {
        TODO("Not yet implemented")
    }

    suspend fun page(offset: Long, limit: Int) = all()
        .clampedSubList(offset.toInt(), limit)
        .map { post ->
            val question = allQuestions().first { it.id == post.questionId }
            val answer = allAnswers().first { it.id == post.answerId }
            PostView(
                post.id!!,
                question.id!!,
                question.dateTime ?: Clock.System.now(),
                question.theme,
                answer.dateTime ?: Clock.System.now(),
            )
        }

    override suspend fun all() = posts.open {
        json.decodeFromStream<List<Post>>(this)
    }
}