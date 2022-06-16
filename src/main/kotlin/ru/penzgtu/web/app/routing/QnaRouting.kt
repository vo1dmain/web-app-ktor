package ru.penzgtu.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.data.entities.qna.Question
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.getOrNull

fun Route.qnaRouting() {
    route("/qna") {
        val repo by closestDI().instance<QnaRepo>()

        get("/posts") {
            val queryParams = call.request.queryParameters
            val page = queryParams.getOrNull<Int>("page")

            val list = repo.list(page)
            call.respond(list.failIfEmpty())
        }

        post("/questions") {
            val question = call.receive<Question>()
            repo.add(question)
            call.respond(HttpStatusCode.Created)
        }
    }
}