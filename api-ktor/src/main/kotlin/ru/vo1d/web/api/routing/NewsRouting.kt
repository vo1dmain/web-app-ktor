package ru.vo1d.web.api.routing

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.vo1d.web.api.dto.news.toResponse
import ru.vo1d.web.api.extensions.orFail
import ru.vo1d.web.api.resources.news.Articles
import ru.vo1d.web.api.resources.news.Categories
import ru.vo1d.web.api.resources.pageRequest
import ru.vo1d.web.domain.news.ArticleFilters
import ru.vo1d.web.domain.news.ArticleRepository
import ru.vo1d.web.domain.news.CategoryFilters
import ru.vo1d.web.domain.news.CategoryRepository

fun Route.newsRouting(articles: ArticleRepository, categories: CategoryRepository) = route("/news") {
    articlesRouting(articles)
    categoriesRouting(categories)
}

private fun Route.articlesRouting(articles: ArticleRepository) {
    get<Articles> {
        val list = articles.find(ArticleFilters(categories = it.categories), it.pageRequest())
        call.respond(list.map { it.toResponse() })
    }

    get<Articles.Id> {
        call.respond(articles.get(it.id).orFail().toResponse())
    }
}

private fun Route.categoriesRouting(categories: CategoryRepository) {
    get<Categories> {
        val list = categories.find(CategoryFilters(parentId = it.parent), it.pageRequest())
        call.respond(list.map { it.toResponse() })
    }

    get<Categories.Id> {
        call.respond(categories.get(it.id).orFail().toResponse())
    }
}
