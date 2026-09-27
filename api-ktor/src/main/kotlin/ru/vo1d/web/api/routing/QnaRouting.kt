package ru.vo1d.web.api.routing

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.vo1d.web.api.dto.qna.QuestionRequest
import ru.vo1d.web.api.dto.qna.toDomain
import ru.vo1d.web.api.dto.qna.toResponse
import ru.vo1d.web.api.extensions.failIfEmpty
import ru.vo1d.web.api.extensions.orFail
import ru.vo1d.web.api.resources.pageRequest
import ru.vo1d.web.api.resources.qna.Posts
import ru.vo1d.web.api.resources.qna.Questions
import ru.vo1d.web.domain.qna.PostRepository
import ru.vo1d.web.domain.qna.QuestionRepository
import io.ktor.server.resources.post as postRes

fun Route.qnaRouting(posts: PostRepository, questions: QuestionRepository) = route("/qna") {
    postsRouting(posts)
    questionsRouting(questions)
}

private fun Route.postsRouting(posts: PostRepository) {
    get<Posts> {
        call.respond(posts.find(it.pageRequest()).failIfEmpty().map { it.toResponse() })
    }

    get<Posts.Id> {
        call.respond(posts.get(it.id).orFail().toResponse())
    }
}

private fun Route.questionsRouting(questions: QuestionRepository) {
    get<Questions> {
        call.respond(questions.find(it.pageRequest()).failIfEmpty().map { it.toResponse() })
    }

    get<Questions.Id> {
        call.respond(questions.get(it.id).orFail().toResponse())
    }

    postRes<Questions> {
        val question = call.receive<QuestionRequest>().toDomain()
        val id = questions.add(question)
        call.respond(HttpStatusCode.Created, id)
    }
}
