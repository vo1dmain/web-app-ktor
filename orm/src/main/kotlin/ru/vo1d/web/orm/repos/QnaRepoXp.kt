package ru.vo1d.web.orm.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.repos.impl.QnaRepoImpl
import ru.vo1d.web.entities.qna.question.QuestionModel
import ru.vo1d.web.orm.dao.qna.AnswerDaoXp
import ru.vo1d.web.orm.dao.qna.PostDaoXp
import ru.vo1d.web.orm.dao.qna.QuestionDaoXp
import ru.vo1d.web.orm.db.DbManager

class QnaRepoXp(override val dbManager: DbManager) : QnaRepoImpl(), XpRepo {
    override val postDao by crudDao<PostDaoXp>()
    override val questionDao by crudDao<QuestionDaoXp>()
    override val answerDao by crudDao<AnswerDaoXp>()

    override suspend fun posts(page: Int?) = dbManager {
        query(qnaDb) { super.posts(page) }
    }

    override suspend fun post(id: Int) = dbManager {
        query(qnaDb) { super.post(id) }
    }


    override suspend fun questions(page: Int?) = dbManager {
        query(qnaDb) { super.questions(page) }
    }

    override suspend fun question(id: Int) = dbManager {
        query(qnaDb) { super.question(id) }
    }

    override suspend fun addQuestion(question: QuestionModel) = dbManager {
        query(qnaDb) { super.addQuestion(question) }
    }
}