package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.data.entities.qna.Post
import ru.penzgtu.web.app.data.entities.qna.Question

abstract class QnaRepo : ListRepo {
    protected abstract val postDao: PostDao
    protected abstract val questionDao: QuestionDao

    suspend fun list(page: Int?): List<Post> {
        return postDao.list(offset(page), limit)
    }

    suspend fun add(question: Question) {
        questionDao.create(question)
    }
}