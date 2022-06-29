package ru.vo1d.web.app.data.repos.impl

import ru.vo1d.web.app.data.dao.AnswerDao
import ru.vo1d.web.app.data.dao.PostDao
import ru.vo1d.web.app.data.dao.QuestionDao
import ru.vo1d.web.app.data.repos.QnaRepo
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.entities.qna.question.QuestionModel

abstract class QnaRepoImpl : QnaRepo {
    protected abstract val questionDao: QuestionDao
    protected abstract val answerDao: AnswerDao
    protected abstract val postDao: PostDao

    override suspend fun posts(page: Int?) = postDao.list(offset(page), limit).failIfEmpty()

    override suspend fun post(id: Int) = postDao.read(id)

    override suspend fun addQuestion(question: QuestionModel) = questionDao.create(question)

    override suspend fun questions(page: Int?) = questionDao.list(offset(page), limit)
}