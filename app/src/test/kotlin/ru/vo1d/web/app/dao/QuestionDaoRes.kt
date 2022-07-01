package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.data.dao.QuestionDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.entities.qna.question.QuestionModel

@OptIn(ExperimentalSerializationApi::class)
class QuestionDaoRes : QuestionDao, JsonDao, AllDaoTest<QuestionModel> {
    private val questions = this.javaClass.getResource("/questions.json")!!

    override suspend fun create(item: QuestionModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): QuestionModel? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: QuestionModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int): List<QuestionModel> {
        return all().clampedSubList(offset.toInt(), limit)
    }

    override suspend fun all(): List<QuestionModel> {
        return questions.open {
            json.decodeFromStream(this)
        }
    }
}