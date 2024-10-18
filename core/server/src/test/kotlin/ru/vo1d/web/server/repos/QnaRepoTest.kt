package ru.vo1d.web.server.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.repos.base.QnaRepoBase
import ru.vo1d.web.server.dao.AnswerDaoRes
import ru.vo1d.web.server.dao.PostDaoRes
import ru.vo1d.web.server.dao.QuestionDaoRes

class QnaRepoTest : QnaRepoBase() {
    override val postDao by crudDao<PostDaoRes>()
    override val questionDao by crudDao<QuestionDaoRes>()
    override val answerDao by crudDao<AnswerDaoRes>()
}