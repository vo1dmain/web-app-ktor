package ru.vo1d.web.app.repos

import ru.vo1d.web.app.dao.AnswerDaoRes
import ru.vo1d.web.app.dao.PostDaoRes
import ru.vo1d.web.app.dao.QuestionDaoRes
import ru.vo1d.web.app.data.dao.delegates.dao
import ru.vo1d.web.app.data.repos.impl.QnaRepoImpl

class QnaRepoTest : QnaRepoImpl() {
    override val postDao by dao<PostDaoRes>()
    override val questionDao by dao<QuestionDaoRes>()
    override val answerDao by dao<AnswerDaoRes>()
}