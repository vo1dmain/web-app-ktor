package ru.penzgtu.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.data.repos.QnaRepo
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.failIfNegative
import ru.penzgtu.web.app.extensions.getOrNull
import ru.penzgtu.web.app.extensions.orFail
import ru.penzgtu.web.entities.qna.question.QuestionModel

fun Route.qnaRouting() {
    route("/qna") {
        val repo by closestDI().instance<QnaRepo>()

        postsRouting(repo)
        questionsRouting(repo)
    }
}

private fun Route.postsRouting(repo: QnaRepo) {
    route("/posts") {
        get {
            val queryParams = call.request.queryParameters
            val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

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
}

private fun Route.questionsRouting(repo: QnaRepo) {
    route("/questions") {
        get {
            val queryParams = call.request.queryParameters
            val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

            val list = repo.questions(page)
            call.respond(list.failIfEmpty())
        }

        post {
            val question = call.receive<QuestionModel>()
            val id = repo.newQuestion(question)
            call.respond(HttpStatusCode.Created, id)
        }
    }
}