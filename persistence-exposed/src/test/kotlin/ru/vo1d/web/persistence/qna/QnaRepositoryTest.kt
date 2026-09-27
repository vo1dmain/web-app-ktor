package ru.vo1d.web.persistence.qna

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.qna.question.NewQuestion
import ru.vo1d.web.persistence.testDatabases
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class QnaRepositoryTest {
    private val databases = testDatabases()
    private val questions = QuestionRepositoryXp(databases)
    private val posts = PostRepositoryXp(databases)

    @Test
    fun addedQuestionGetsIdAndTime() = runBlocking<Unit> {
        val id = questions.add(NewQuestion("Сессия", "Когда экзамены?", 7, null))

        val stored = questions.get(id)
        assertEquals("Когда экзамены?", stored?.body)
        assertEquals(listOf(id), questions.find(PageRequest()).map { it.id })
        assertNull(questions.get(id + 1))
    }

    @Test
    fun postComesWithQuestionAndAnswer() = runBlocking<Unit> {
        val questionId = questions.add(NewQuestion("Сессия", "Когда экзамены?", 7, null))

        // answers and posts have no API yet
        val postId = transaction(databases.qna) {
            val answerId = Answers.insertAndGetId {
                it[Answers.questionId] = questionId
                it[body] = "В январе"
            }
            Posts.insertAndGetId {
                it[Posts.questionId] = questionId
                it[Posts.answerId] = answerId
            }.value
        }

        val post = posts.get(postId)
        assertEquals("Когда экзамены?", post?.question?.body)
        assertEquals("В январе", post?.answer?.body)

        val view = posts.find(PageRequest()).single()
        assertEquals(questionId, view.questionId)
        assertEquals("Сессия", view.questionTheme)
    }
}
