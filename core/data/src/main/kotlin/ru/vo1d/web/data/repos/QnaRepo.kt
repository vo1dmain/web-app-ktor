package ru.vo1d.web.data.repos

import org.kodein.di.DI
import org.kodein.di.instance
import ru.vo1d.web.data.dao.AnswerDao
import ru.vo1d.web.data.dao.PostDao
import ru.vo1d.web.data.dao.PostViewDao
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.entities.qna.post.Post
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.Question

class QnaRepo(di: DI) : ListRepo {
    private val questionDao: QuestionDao by di.instance()
    private val answerDao: AnswerDao by di.instance()
    private val postDao: PostDao by di.instance()
    private val postViewDao: PostViewDao by di.instance()

    suspend fun posts(page: Int?): List<PostView> {
        return postViewDao.page(offset(page), limit)
    }

    suspend fun post(id: Int): Post? {
        return postDao.read(id)
    }

    suspend fun questions(page: Int?): List<Question> {
        return questionDao.page(offset(page), limit)
    }

    suspend fun question(id: Int): Question? {
        return questionDao.read(id)
    }

    suspend fun addQuestion(question: Question): Int? {
        return questionDao.create(question)
    }
}