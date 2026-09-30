package ru.vo1d.web.persistence.news

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import ru.vo1d.web.persistence.readSeed

@Serializable
private class CategorySeed(val title: String, val parentId: Int? = null)

/**
 * @param categories 1-based positions of categories in `categories.json`
 */
@Serializable
private class ArticleSeed(
    val title: String,
    val body: String,
    val previewImage: String? = null,
    val gallery: List<String>? = null,
    val categories: List<Int>
)

/**
 * Fills empty news tables with the demo categories and articles from `data/news`; does nothing if there are
 * categories already.
 */
internal fun seedDemoNews() {
    if (!Categories.selectAll().empty()) return

    // the files refer to categories by position, the database assigns its own ids
    val categoryIds = mutableMapOf<Int, Int>()
    readSeed("news/categories.json", CategorySeed.serializer()).forEachIndexed { index, category ->
        categoryIds[index + 1] = Categories.insertAndGetId {
            it[title] = category.title
            it[parentId] = category.parentId?.let(categoryIds::getValue)
        }.value
    }

    readSeed("news/articles.json", ArticleSeed.serializer()).forEach { article ->
        val articleId = Articles.insertAndGetId {
            it[title] = article.title
            it[body] = article.body
            it[preview] = article.previewImage
            it[gallery] = article.gallery?.joinToString(",")
        }

        ArticleCategories.batchInsert(article.categories, shouldReturnGeneratedValues = false) {
            this[ArticleCategories.articleId] = articleId
            this[ArticleCategories.categoryId] = categoryIds.getValue(it)
        }
    }
}
