package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.dao.PostDaoResourceImpl
import ru.penzgtu.web.app.dao.QuestionDaoResourceImpl
import ru.penzgtu.web.app.data.delegates.dao
import ru.penzgtu.web.app.data.repos.QnaRepo

class QnaRepoTest : QnaRepo() {
    override val postDao by dao<PostDaoResourceImpl>()
    override val questionDao by dao<QuestionDaoResourceImpl>()
}