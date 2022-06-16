package ru.penzgtu.web.app.dao

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.clampedSubList
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.entities.qna.answers.Answer
import ru.penzgtu.web.app.data.entities.qna.posts.Post
import ru.penzgtu.web.app.data.entities.qna.posts.PostView
import ru.penzgtu.web.app.data.entities.qna.questions.Question
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class PostDaoRes : PostDao, JsonDao, AllDaoTest<Post> {
    private val posts = this.javaClass.getResource("/posts_list.json")!!
    private val questions = this.javaClass.getResource("/questions.json")!!
    private val answers = this.javaClass.getResource("/answers.json")!!

    private val allQuestions by lazy { runBlocking { allQuestions() } }
    private val allAnswers by lazy { runBlocking { allAnswers() } }

    private suspend fun allQuestions(): List<Question> {
        return questions.open {
            json.decodeFromStream(this)
        }
    }

    private suspend fun allAnswers(): List<Answer> {
        return answers.open {
            json.decodeFromStream(this)
        }
    }

    override suspend fun create(item: Post): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Post? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Post) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<PostView> {
        return all().clampedSubList(offset, limit).map { post ->
            val question = allQuestions.first { it.id == post.questionId }
            val answer = allAnswers.first { it.id == post.answerId }
            PostView(post.id!!, question.id!!, question.dateTime, question.theme, answer.dateTime)
        }
    }

    override suspend fun all(): List<Post> {
        return posts.open {
            json.decodeFromStream(this)
        }
    }
}