package ru.vo1d.web.app.exposed.repos

import ru.vo1d.web.app.data.dao.delegates.dao
import ru.vo1d.web.app.data.repos.impl.QnaRepoImpl
import ru.vo1d.web.app.exposed.dao.qna.AnswerDaoXp
import ru.vo1d.web.app.exposed.dao.qna.PostDaoXp
import ru.vo1d.web.app.exposed.dao.qna.QuestionDaoXp
import ru.vo1d.web.app.exposed.db.DbManager
import ru.vo1d.web.entities.qna.question.QuestionModel

class QnaRepoXp(private val dbManager: DbManager) : QnaRepoImpl() {
    override val postDao by dao<PostDaoXp>()
    override val questionDao by dao<QuestionDaoXp>()
    override val answerDao by dao<AnswerDaoXp>()

    override suspend fun posts(page: Int?) = with(dbManager) {
        query(qnaDb) { super.posts(page) }
    }

    override suspend fun post(id: Int) = with(dbManager) {
        query(qnaDb) { super.post(id) }
    }

    override suspend fun addQuestion(question: QuestionModel) = with(dbManager) {
        query(qnaDb) { super.addQuestion(question) }
    }

    override suspend fun questions(page: Int?) = with(dbManager) {
        query(qnaDb) { super.questions(page) }
    }
}