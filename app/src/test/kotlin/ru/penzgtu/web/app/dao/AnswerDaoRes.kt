package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.clampedSubList
import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.extensions.open
import ru.penzgtu.web.entities.qna.answers.AnswerModel

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