package ru.penzgtu.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.penzgtu.web.app.data.qna.Question
import ru.penzgtu.web.app.extensions.getOrDefault
import ru.penzgtu.web.app.repos.QnaRepo
import ru.penzgtu.web.app.repos.QnaRepo.Companion.defaultPage

object QnaRouting {
    private lateinit var repo: QnaRepo

    fun setRepo(repo: QnaRepo) {
        this.repo = repo
    }

    fun Route.qnaRouting() {
        route("/qna") {
            get("/posts") {
                val queryParams = call.request.queryParameters
                val page = queryParams.getOrDefault("page", defaultPage)

                val list = repo.list(page)
                call.respond(list)
            }

            post {
                val question = call.receive<Question>()
                repo.add(question)
                call.respond(HttpStatusCode.Created)
            }
        }
    }
}