package ru.vo1d.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.data.filters.news.ArticleFilters
import ru.vo1d.web.app.data.filters.news.categoryFilters
import ru.vo1d.web.app.data.repos.NewsRepo
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.extensions.getOrNull
import ru.vo1d.web.app.extensions.orFail

fun Route.newsRouting() = route("/news") {
    val repo by closestDI().instance<NewsRepo>()

    articlesRouting(repo)
    categoriesRouting(repo)
}

private fun Route.articlesRouting(repo: NewsRepo) = route("/articles") {
    get {
        val queryParams = call.request.queryParameters

        val categoryId = queryParams.getOrNull<Int>("category")
        val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

        val list = repo.articles(
            ArticleFilters.new { this.categoryId = categoryId },
            page
        )
        call.respond(list.failIfEmpty())
    }

    get("/{id}") {
        val pathParams = call.parameters
        val id = pathParams.getOrFail<Int>("id")

        call.respond(repo.article(id).orFail())
    }
}

private fun Route.categoriesRouting(repo: NewsRepo) = route("/categories") {
    get {
        val queryParams = call.request.queryParameters

        val parentId = queryParams.getOrNull<Int>("parent")
        val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

        val list = repo.categories(
            categoryFilters { parentId(parentId) },
            page
        )
        call.respond(list.failIfEmpty())
    }

    get("/{id}") {
        val pathParams = call.parameters
        val id = pathParams.getOrFail<Int>("id")

        call.respond(repo.category(id).orFail())
    }
}
