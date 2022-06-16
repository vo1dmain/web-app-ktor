package ru.penzgtu.web.app.dao

import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.data.entities.qna.Answer

class AnswerDaoResourceImpl: AnswerDao {
    override suspend fun create(item: Answer): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Answer? {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Answer) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}