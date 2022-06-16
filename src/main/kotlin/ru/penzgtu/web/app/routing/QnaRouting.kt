package ru.penzgtu.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.data.entities.qna.Question
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.getOrNull
import ru.penzgtu.web.app.extensions.orFail

fun Route.qnaRouting() {
    route("/qna") {
        val repo by closestDI().instance<QnaRepo>()

        route("/posts") {
            get {
                val queryParams = call.request.queryParameters
                val page = queryParams.getOrNull<Int>("page")

                val list = repo.posts(page)
                call.respond(list.failIfEmpty())
            }

            get("/{id}") {
                val params = call.parameters
                val id = params.getOrFail<Int>("id")

                val item = repo.post(id).orFail()
                call.respond(item)
            }
        }

        post("/questions") {
            val question = call.receive<Question>()
            repo.newQuestion(question)
            call.respond(HttpStatusCode.Created)
        }
    }
}