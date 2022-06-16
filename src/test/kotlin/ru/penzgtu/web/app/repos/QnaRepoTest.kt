package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.dao.AnswerDaoRes
import ru.penzgtu.web.app.dao.PostDaoRes
import ru.penzgtu.web.app.dao.QuestionDaoRes
import ru.penzgtu.web.app.data.delegates.dao
import ru.penzgtu.web.app.data.repos.QnaRepo

class QnaRepoTest : QnaRepo() {
    override val postDao by dao<PostDaoRes>()
    override val questionDao by dao<QuestionDaoRes>()
    override val answerDao by dao<AnswerDaoRes>()
}