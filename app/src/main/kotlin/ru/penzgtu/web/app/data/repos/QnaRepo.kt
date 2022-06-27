package ru.penzgtu.web.app.data.repos

import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.entities.qna.post.PostModel
import ru.penzgtu.web.entities.qna.post.PostView
import ru.penzgtu.web.entities.qna.question.QuestionModel

abstract class QnaRepo : ListRepo {
    protected abstract val questionDao: QuestionDao
    protected abstract val answerDao: AnswerDao
    protected abstract val postDao: PostDao

    open suspend fun posts(page: Int?): List<PostView> {
        return postDao.list(offset(page), limit).failIfEmpty()
    }

    open suspend fun post(id: Int): PostModel? {
        return postDao.read(id)
    }

    open suspend fun newQuestion(question: QuestionModel): Int {
        return questionDao.create(question)
    }

    open suspend fun questions(page: Int?): List<QuestionModel> {
        return questionDao.list(offset(page), limit)
    }
}