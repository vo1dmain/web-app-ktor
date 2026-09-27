package ru.vo1d.web.api.fakes

import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.category.Category
import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.question.Question
import kotlin.time.Instant

/**
 * Data served by the fake repositories; ids start at 0.
 */
internal object TestData {
    private val published = Instant.parse("2026-09-01T09:00:00Z")

    val categories = listOf(
        Category(0, "Все новости", null),
        Category(1, "Анонсы", null)
    )

    val articles = listOf(
        Article(0, "Срочные новости!", "Случилось непоправимое.", dateTime = published, categories = listOf(0)),
        Article(1, "Несрочные новости!", "Случилось поправимое.", dateTime = published, categories = listOf(1))
    )

    val questions = listOf(
        Question(0, "Что это такое?", "Я открыл и не понимаю что это", 7, null, published),
        Question(1, "А это что?", "Снова что-то не то", 7, null, published)
    )

    val answers = listOf(
        Answer(0, 0, "Это вопрос", published),
        Answer(1, 1, "Это тоже вопрос", published)
    )

    val posts = listOf(
        Post(0, questions[0], answers[0]),
        Post(1, questions[1], answers[1])
    )
}

internal fun <T> List<T>.page(page: PageRequest) = drop(page.offset.toInt()).take(page.size)
