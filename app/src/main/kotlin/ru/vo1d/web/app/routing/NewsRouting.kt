package ru.vo1d.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.extensions.getOrNull
import ru.vo1d.web.app.extensions.orFail
import ru.vo1d.web.data.repos.NewsRepo

fun Route.newsRouting() = route("/news") {
    val repo by closestDI().instance<NewsRepo>()

    articlesRouting(repo)
    categoriesRouting(repo)
}

private fun Route.articlesRouting(repo: NewsRepo) = route("/articles") {
    get {
        val queryParams = call.request.queryParameters
        val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

        val list = repo.articles(page) {
            categoryId = queryParams.getOrNull<Int>("category")?.failIfNegative()
        }
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

        val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

        val list = repo.categories(page) {
            parentId = queryParams.getOrNull<Int>("parent")?.failIfNegative()
        }
        call.respond(list.failIfEmpty())
    }

    get("/{id}") {
        val pathParams = call.parameters
        val id = pathParams.getOrFail<Int>("id")

        call.respond(repo.category(id).orFail())
    }
}
