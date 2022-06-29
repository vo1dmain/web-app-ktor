package ru.vo1d.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.data.repos.impl.QnaRepoImpl
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.extensions.getOrNull
import ru.vo1d.web.app.extensions.orFail
import ru.vo1d.web.entities.qna.question.QuestionModel

fun Route.qnaRouting() {
    route("/qna") {
        val repo by closestDI().instance<QnaRepoImpl>()

        postsRouting(repo)
        questionsRouting(repo)
    }
}

private fun Route.postsRouting(repo: QnaRepoImpl) {
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

private fun Route.questionsRouting(repo: QnaRepoImpl) {
    route("/questions") {
        get {
            val queryParams = call.request.queryParameters
            val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

            val list = repo.questions(page)
            call.respond(list.failIfEmpty())
        }

        post {
            val question = call.receive<QuestionModel>()
            val id = repo.addQuestion(question)
            call.respond(HttpStatusCode.Created, id)
        }
    }
}