package ru.penzgtu.web.app.data.repos.base

import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.data.dao.PostDao
import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.data.delegates.dao
import ru.penzgtu.web.app.data.repos.QnaRepo

class QnaRepoBase : QnaRepo() {
    override val postDao by dao<PostDao>()
    override val questionDao by dao<QuestionDao>()
    override val answerDao by dao<AnswerDao>()
}