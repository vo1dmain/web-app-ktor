package ru.vo1d.web.server.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.dao.AllDao
import ru.vo1d.web.data.dao.AnswerDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.data.extensions.resource
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.server.clampedSubList

@OptIn(ExperimentalSerializationApi::class)
class AnswerDaoRes : AnswerDao, JsonDao, AllDao<Answer> {
    private val file = resource("/data/answers.json")

    override suspend fun create(item: Answer): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: Answer): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Answer? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Answer): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg items: Answer): Int {
        TODO("Not yet implemented")
    }

    override suspend fun page(offset: Long, limit: Int): List<Answer> {
        return all().clampedSubList(offset.toInt(), limit)
    }

    override suspend fun all(): List<Answer> {
        return file.open {
            json.decodeFromStream<List<Answer>>(this)
        }
    }
}