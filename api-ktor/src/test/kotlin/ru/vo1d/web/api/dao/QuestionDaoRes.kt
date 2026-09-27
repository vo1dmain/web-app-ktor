package ru.vo1d.web.api.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.domain.dao.AllDao
import ru.vo1d.web.domain.dao.QuestionDao
import ru.vo1d.web.domain.extensions.open
import ru.vo1d.web.domain.extensions.resource
import ru.vo1d.web.domain.qna.question.Question
import ru.vo1d.web.api.clampedSubList

@OptIn(ExperimentalSerializationApi::class)
class QuestionDaoRes : QuestionDao, JsonDao, AllDao<Question> {
    private val file = resource("/data/questions.json")

    override suspend fun create(item: Question): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun create(vararg items: Question): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Question? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Question): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(vararg items: Question): Int {
        TODO("Not yet implemented")
    }

    override suspend fun page(offset: Long, limit: Int): List<Question> {
        return all().clampedSubList(offset.toInt(), limit)
    }

    override suspend fun all() = file.open {
        json.decodeFromStream<List<Question>>(this)
    }
}