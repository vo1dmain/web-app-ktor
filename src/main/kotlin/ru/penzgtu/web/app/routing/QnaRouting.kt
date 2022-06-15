package ru.penzgtu.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.data.qna.Question
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.getOrDefault
import ru.penzgtu.web.app.repos.QnaRepo
import ru.penzgtu.web.app.repos.QnaRepo.Companion.defaultPage

fun Route.qnaRouting() {
    route("/qna") {
        val repo by closestDI().instance<QnaRepo>()

        get("/posts") {
            val queryParams = call.request.queryParameters
            val page = queryParams.getOrDefault("page", defaultPage)

            val list = repo.list(page).failIfEmpty()
            call.respond(list)
        }

        post("/questions") {
            val question = call.receive<Question>()
            repo.add(question)
            call.respond(HttpStatusCode.Created)
        }
    }
}