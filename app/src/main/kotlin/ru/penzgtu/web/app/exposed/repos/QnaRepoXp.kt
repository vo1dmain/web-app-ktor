package ru.penzgtu.web.app.exposed.repos

import ru.penzgtu.web.app.data.dao.delegates.dao
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.exposed.dao.qna.AnswerDaoXp
import ru.penzgtu.web.app.exposed.dao.qna.PostDaoXp
import ru.penzgtu.web.app.exposed.dao.qna.QuestionDaoXp

class QnaRepoXp : QnaRepo() {
    override val postDao by dao<PostDaoXp>()
    override val questionDao by dao<QuestionDaoXp>()
    override val answerDao by dao<AnswerDaoXp>()
}