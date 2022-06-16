package ru.penzgtu.web.app.dao

import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.data.entities.qna.Question

class QuestionDaoResourceImpl: QuestionDao {
    override suspend fun create(item: Question): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Question? {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Question) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}