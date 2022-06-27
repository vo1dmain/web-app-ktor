package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.app.data.dao.AnswerDao
import ru.vo1d.web.app.extensions.open
import ru.vo1d.web.entities.qna.answer.AnswerModel

@OptIn(ExperimentalSerializationApi::class)
class AnswerDaoRes : AnswerDao, JsonDao, AllDaoTest<AnswerModel> {
    private val answers = this.javaClass.getResource("/answers.json")!!

    override suspend fun create(item: AnswerModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): AnswerModel? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: AnswerModel): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int): List<AnswerModel> {
        return all().clampedSubList(offset.toInt(), limit)
    }

    override suspend fun all(): List<AnswerModel> {
        return answers.open {
            json.decodeFromStream(this)
        }
    }
}