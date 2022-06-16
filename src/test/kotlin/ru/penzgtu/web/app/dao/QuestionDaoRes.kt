package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.clampedSubList
import ru.penzgtu.web.app.data.dao.QuestionDao
import ru.penzgtu.web.app.data.entities.qna.Question
import ru.penzgtu.web.app.data.util.Filters
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class QuestionDaoRes : QuestionDao, JsonDao, AllDaoTest<Question> {
    private val questions = this.javaClass.getResource("/questions.json")!!

    override suspend fun create(item: Question): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Question? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Question) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<Question> {
        return all().clampedSubList(offset, limit)
    }

    override suspend fun filter(filters: Filters, offset: Int, limit: Int): List<Question> {
        val ids = filters.intArray("ids")
        return list(offset, limit).filter { question ->
            ids?.let { question.id!! in it } ?: true
        }
    }

    override suspend fun all(): List<Question> {
        return questions.open {
            json.decodeFromStream(this)
        }
    }
}