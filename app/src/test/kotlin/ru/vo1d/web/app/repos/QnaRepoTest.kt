package ru.vo1d.web.app.repos

import ru.vo1d.web.app.dao.AnswerDaoRes
import ru.vo1d.web.app.dao.PostDaoRes
import ru.vo1d.web.app.dao.QuestionDaoRes
import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.repos.impl.QnaRepoImpl

class QnaRepoTest : QnaRepoImpl() {
    override val postDao by crudDao<PostDaoRes>()
    override val questionDao by crudDao<QuestionDaoRes>()
    override val answerDao by crudDao<AnswerDaoRes>()
}