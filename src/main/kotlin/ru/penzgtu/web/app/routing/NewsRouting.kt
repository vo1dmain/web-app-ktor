package ru.penzgtu.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import ru.penzgtu.web.app.extensions.getOrDefault
import ru.penzgtu.web.app.extensions.getOrNull
import ru.penzgtu.web.app.repos.NewsRepo

object NewsRouting {
    private lateinit var repo: NewsRepo

    fun setRepo(newsRepo: NewsRepo) {
        repo = newsRepo
    }

    fun Route.newsRouting() {
        route("/news") {
            get {
                val queryParams = call.request.queryParameters

                val categoryId = queryParams.getOrNull<Int>("category_id")
                val offset = queryParams.getOrDefault("offset", NewsRepo.defaultOffset)
                val limit = queryParams.getOrDefault("limit", NewsRepo.defaultLimit)

                val list = repo.list(categoryId, offset, limit)
                call.respond(list)
            }

            get("/{id}") {
                val pathParams = call.parameters
                val articleId = pathParams.getOrFail<Int>("id")

                val article = repo.article(articleId)
                call.respond(article)
            }

            route("/categories") {
                get {
                    val queryParams = call.request.queryParameters

                    val parentId = queryParams.getOrNull<Int>("parent_id")

                    val list = repo.listCategories(parentId)
                    call.respond(list)
                }
            }
        }
    }
}

