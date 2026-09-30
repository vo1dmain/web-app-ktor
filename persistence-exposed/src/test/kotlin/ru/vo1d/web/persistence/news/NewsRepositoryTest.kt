package ru.vo1d.web.persistence.news

import kotlinx.coroutines.runBlocking
import ru.vo1d.web.domain.PageRequest
import ru.vo1d.web.domain.news.ArticleFilters
import ru.vo1d.web.domain.news.CategoryFilters
import ru.vo1d.web.persistence.testDatabases
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

// demo data: category 1 "Все новости", 2 "Анонсы"; article 1 in both, article 2 in the first only
class NewsRepositoryTest {
    private val databases = testDatabases()
    private val articles = ArticleRepositoryXp(databases)
    private val categories = CategoryRepositoryXp(databases)

    @Test
    fun articleComesWithItsCategories() = runBlocking<Unit> {
        val article = articles.get(1)

        assertEquals(listOf(1, 2), article?.categories?.sorted())
        assertNull(articles.get(3))
    }

    @Test
    fun categoryFilterMatchesAnyOfTheCategories() = runBlocking<Unit> {
        val page = PageRequest()

        assertEquals(listOf(1), articles.find(ArticleFilters(categories = listOf(2)), page).map { it.id })
        // an article in several requested categories is listed once
        assertEquals(listOf(1, 2), articles.find(ArticleFilters(categories = listOf(1, 2)), page).map { it.id })
        assertEquals(listOf(1, 2), articles.find(ArticleFilters(), page).map { it.id })
    }

    @Test
    fun pagesFollowEachOther() = runBlocking<Unit> {
        assertEquals(listOf(2), articles.find(ArticleFilters(), PageRequest(number = 2, size = 1)).map { it.id })
        assertTrue(articles.find(ArticleFilters(), PageRequest(number = 2)).isEmpty())
    }

    @Test
    fun demoNewsIsLoadedOnce() = runBlocking<Unit> {
        databases.init()

        assertEquals(2, articles.find(ArticleFilters(), PageRequest()).size)
        assertEquals(2, categories.find(CategoryFilters(), PageRequest()).size)
    }

    @Test
    fun categoriesFilterByParent() = runBlocking<Unit> {
        assertEquals("Анонсы", categories.get(2)?.title)
        assertTrue(categories.find(CategoryFilters(parentId = 1), PageRequest()).isEmpty())
    }
}
