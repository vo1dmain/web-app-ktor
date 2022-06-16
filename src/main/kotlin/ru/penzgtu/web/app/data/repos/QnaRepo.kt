package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.data.entities.qna.Question
import ru.penzgtu.web.app.data.entities.qna.post.PostDto
import ru.penzgtu.web.app.data.entities.qna.post.PostView
import ru.penzgtu.web.app.data.util.filtersOf
import ru.penzgtu.web.app.extensions.failIfEmpty

abstract class QnaRepo : ListRepo {
    protected abstract val questionDao: QuestionDao
    protected abstract val answerDao: AnswerDao
    protected abstract val postDao: PostDao

    suspend fun posts(page: Int?): List<PostView> {
        val posts = postDao.list(offset(page), limit).failIfEmpty()

        val questions = questionDao.filter(
            filtersOf(
                "ids" to posts.map { it.questionId }.toIntArray()
            ),
            offset(page),
            limit
        ).failIfEmpty()

        val answers = answerDao.filter(
            filtersOf(
                "ids" to posts.map { it.answerId }.toIntArray()
            ),
            offset(page),
            limit
        ).failIfEmpty()

        return posts.map { post ->
            val question = questions.first { it.id == post.questionId }
            val answer = answers.first { it.id == post.answerId }
            PostView(post.id!!, post.questionId, question.dateTime, question.theme, answer.dateTime)
        }
    }

    suspend fun post(id: Int): PostDto? {
        val post = postDao.read(id) ?: return null
        val question = questionDao.read(post.questionId) ?: return null
        val answer = answerDao.read(post.answerId) ?: return null
        return PostDto(post.id!!, question, answer)
    }

    suspend fun newQuestion(question: Question) {
        questionDao.create(question)
    }

    suspend fun questions(page: Int?): List<Question> {
        return questionDao.list(offset(page), limit)
    }
}