package ru.penzgtu.web.app.exposed.repos

import ru.penzgtu.web.app.data.dao.delegates.dao
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.exposed.dao.qna.AnswerDaoXp
import ru.penzgtu.web.app.exposed.dao.qna.PostDaoXp
import ru.penzgtu.web.app.exposed.dao.qna.QuestionDaoXp
import ru.penzgtu.web.app.exposed.db.DbManager
import ru.penzgtu.web.entities.qna.question.QuestionModel

class QnaRepoXp(private val dbManager: DbManager) : QnaRepo() {
    override val postDao by dao<PostDaoXp>()
    override val questionDao by dao<QuestionDaoXp>()
    override val answerDao by dao<AnswerDaoXp>()

    override suspend fun posts(page: Int?) = with(dbManager) {
        query(qnaDb) { super.posts(page) }
    }

    override suspend fun post(id: Int) = with(dbManager) {
        query(qnaDb) { super.post(id) }
    }

    override suspend fun newQuestion(question: QuestionModel) = with(dbManager) {
        query(qnaDb) { super.newQuestion(question) }
    }

    override suspend fun questions(page: Int?) = with(dbManager) {
        query(qnaDb) { super.questions(page) }
    }
}