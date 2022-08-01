package ru.vo1d.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.app.clampedSubList
import ru.vo1d.web.data.dao.AnswerDao
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.orm.extensions.resource

@OptIn(ExperimentalSerializationApi::class)
class AnswerDaoRes : AnswerDao, JsonDao, AllDaoTest<Answer> {
    private val file = resource("/data/answers.json")

    override suspend fun create(item: Answer): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: Answer): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int) = all().firstOrNull { it.id == id }

    override suspend fun update(item: Answer): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Long, limit: Int) = all().clampedSubList(offset.toInt(), limit)

    override suspend fun all() = file.open {
        json.decodeFromStream<List<Answer>>(this)
    }
}