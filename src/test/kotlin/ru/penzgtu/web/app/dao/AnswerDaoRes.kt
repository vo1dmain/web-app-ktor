package ru.penzgtu.web.app.dao

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromStream
import ru.penzgtu.web.app.clampedSubList
import ru.penzgtu.web.app.data.dao.AnswerDao
import ru.penzgtu.web.app.data.entities.qna.answers.Answer
import ru.penzgtu.web.app.extensions.open

@OptIn(ExperimentalSerializationApi::class)
class AnswerDaoRes : AnswerDao, JsonDao, AllDaoTest<Answer> {
    private val answers = this.javaClass.getResource("/answers.json")!!

    override suspend fun create(item: Answer): Int {
        TODO("Not yet implemented")
    }

    override suspend fun read(id: Int): Answer? {
        return all().firstOrNull { it.id == id }
    }

    override suspend fun update(item: Answer) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun list(offset: Int, limit: Int): List<Answer> {
        return all().clampedSubList(offset, limit)
    }

    override suspend fun all(): List<Answer> {
        return answers.open {
            json.decodeFromStream(this)
        }
    }
}