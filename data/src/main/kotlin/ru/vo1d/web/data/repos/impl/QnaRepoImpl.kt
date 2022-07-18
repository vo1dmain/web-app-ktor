package ru.vo1d.web.data.repos.impl

import ru.vo1d.web.data.dao.AnswerDao
import ru.vo1d.web.data.dao.PostDao
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.data.repos.QnaRepo
import ru.vo1d.web.entities.qna.question.QuestionModel

abstract class QnaRepoImpl : QnaRepo {
    protected abstract val questionDao: QuestionDao
    protected abstract val answerDao: AnswerDao
    protected abstract val postDao: PostDao

    override suspend fun posts(page: Int?) = postDao.list(offset(page), limit)

    override suspend fun post(id: Int) = postDao.read(id)


    override suspend fun questions(page: Int?) = questionDao.list(offset(page), limit)

    override suspend fun addQuestion(question: QuestionModel) = questionDao.create(question)
}