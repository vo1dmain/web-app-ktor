package ru.vo1d.web.exposed.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.repos.impl.QnaRepoImpl
import ru.vo1d.web.entities.qna.question.Question
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.dao.qna.AnswerDaoXp
import ru.vo1d.web.exposed.dao.qna.PostDaoXp
import ru.vo1d.web.exposed.dao.qna.QuestionDaoXp

class QnaRepoXp(override val dbContext: DbContext) : QnaRepoImpl(), XpRepo {
    override val postDao by crudDao<PostDaoXp>()
    override val questionDao by crudDao<QuestionDaoXp>()
    override val answerDao by crudDao<AnswerDaoXp>()

    override suspend fun posts(page: Int?) = dbContext {
        query(qnaDb) { super.posts(page) }
    }

    override suspend fun post(id: Int) = dbContext {
        query(qnaDb) { super.post(id) }
    }


    override suspend fun questions(page: Int?) = dbContext {
        query(qnaDb) { super.questions(page) }
    }

    override suspend fun question(id: Int) = dbContext {
        query(qnaDb) { super.question(id) }
    }

    override suspend fun addQuestion(question: Question) = dbContext {
        query(qnaDb) { super.addQuestion(question) }
    }
}