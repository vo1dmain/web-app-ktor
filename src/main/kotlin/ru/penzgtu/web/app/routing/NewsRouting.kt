package ru.penzgtu.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.getOrDefault
import ru.penzgtu.web.app.extensions.getOrNull
import ru.penzgtu.web.app.extensions.orFail
import ru.penzgtu.web.app.repos.NewsRepo

fun Route.newsRouting() {
    route("/news") {
        val repo by closestDI().instance<NewsRepo>()

        get {
            val queryParams = call.request.queryParameters

            val categoryId = queryParams.getOrNull<Int>("category_id")
            val offset = queryParams.getOrDefault("offset", NewsRepo.defaultOffset)
            val limit = queryParams.getOrDefault("limit", NewsRepo.defaultLimit)

            val list = repo.list(categoryId, offset, limit).failIfEmpty()
            call.respond(list)
        }

        get("/{id}") {
            val pathParams = call.parameters
            val articleId = pathParams.getOrFail<Int>("id")

            val item = repo.item(articleId).orFail()

            call.respond(item)
        }

        route("/categories") {
            get {
                val queryParams = call.request.queryParameters

                val parentId = queryParams.getOrNull<Int>("parent_id")

                val list = repo.categories(parentId).failIfEmpty()

                call.respond(list)
            }
        }
    }
}

