package ru.penzgtu.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.data.entities.news.articles.articleFilters
import ru.penzgtu.web.app.data.entities.news.categories.categoryFilters
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.failIfNegative
import ru.penzgtu.web.app.extensions.getOrNull
import ru.penzgtu.web.app.extensions.orFail

fun Route.newsRouting() {
    route("/news") {
        val repo by closestDI().instance<NewsRepo>()

        articlesRouting(repo)
        categoriesRouting(repo)
    }
}

private fun Route.articlesRouting(repo: NewsRepo) {
    route("/articles") {
        get {
            val queryParams = call.request.queryParameters

            val categoryId = queryParams.getOrNull<Int>("category_id")
            val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

            val list = repo.articles(
                articleFilters {
                    categoryId(categoryId)
                },
                page
            )
            call.respond(list.failIfEmpty())
        }

        get("/{id}") {
            val pathParams = call.parameters
            val id = pathParams.getOrFail<Int>("id")

            val item = repo.article(id).orFail()
            call.respond(item)
        }
    }
}

private fun Route.categoriesRouting(repo: NewsRepo) {
    route("/categories") {
        get {
            val queryParams = call.request.queryParameters

            val parentId = queryParams.getOrNull<Int>("parent_id")
            val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

            val list = repo.categories(
                categoryFilters {
                    parentId(parentId)
                },
                page
            )
            call.respond(list.failIfEmpty())
        }

        get("/{id}") {
            val pathParams = call.parameters
            val id = pathParams.getOrFail<Int>("id")

            val item = repo.category(id).orFail()
            call.respond(item)
        }
    }
}
