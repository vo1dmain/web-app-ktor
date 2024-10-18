package ru.vo1d.web.server.routing

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.data.repos.QnaRepo
import ru.vo1d.web.entities.qna.question.Question
import ru.vo1d.web.server.extensions.failIfEmpty
import ru.vo1d.web.server.extensions.orFail
import ru.vo1d.web.server.resources.qna.Posts
import ru.vo1d.web.server.resources.qna.Questions
import io.ktor.server.resources.post as postRes

fun Route.qnaRouting() = route("/qna") {
    val repo by closestDI().instance<QnaRepo>()

    postsRouting(repo)
    questionsRouting(repo)
}

private fun Route.postsRouting(repo: QnaRepo) {
    get<Posts> {
        call.respond(repo.posts(it.page).failIfEmpty())
    }

    get<Posts.Id> {
        call.respond(repo.post(it.id).orFail())
    }
}

private fun Route.questionsRouting(repo: QnaRepo) {
    get<Questions> {
        call.respond(repo.questions(it.page).failIfEmpty())
    }

    get<Questions.Id> {
        call.respond(repo.question(it.id).orFail())
    }

    postRes<Questions> {
        val question = call.receive<Question>()
        val id = repo.addQuestion(question) ?: throw Exception()
        call.respond(HttpStatusCode.Created, id)
    }
}