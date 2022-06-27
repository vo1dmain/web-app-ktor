package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.entities.qna.posts.PostModel
import ru.penzgtu.web.entities.qna.posts.PostView
import ru.penzgtu.web.entities.qna.questions.QuestionModel

abstract class QnaRepo : ListRepo {
    protected abstract val questionDao: QuestionDao
    protected abstract val answerDao: AnswerDao
    protected abstract val postDao: PostDao

    suspend fun posts(page: Int?): List<PostView> {
        return postDao.list(offset(page), limit).failIfEmpty()
    }

    suspend fun post(id: Int): PostModel? {
        return postDao.read(id)
    }

    suspend fun newQuestion(question: QuestionModel) {
        questionDao.create(question)
    }

    suspend fun questions(page: Int?): List<QuestionModel> {
        return questionDao.list(offset(page), limit)
    }
}