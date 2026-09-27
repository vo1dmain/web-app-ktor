package ru.vo1d.web.persistence.qna

import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.QuestionRepository
import ru.vo1d.web.domain.qna.question.Question
import ru.vo1d.web.persistence.XpRepository
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.entities.qna.QuestionEntity
import ru.vo1d.web.persistence.entities.qna.Questions
import ru.vo1d.web.persistence.mappers.mapItem
import ru.vo1d.web.persistence.mappers.toDomain

class QuestionRepositoryXp(ctx: DbContext) : XpRepository(ctx.qna), QuestionRepository {
    override suspend fun find(page: PageRequest): List<Question> = query {
        QuestionEntity.all()
            .limit(page.size)
            .offset(page.offset)
            .map(QuestionEntity::toDomain)
    }

    override suspend fun get(id: Int): Question? = query {
        QuestionEntity.findById(id)?.toDomain()
    }

    override suspend fun add(question: Question): Int = query {
        Questions.insertAndGetId { it.mapItem(question) }.value
    }
}
