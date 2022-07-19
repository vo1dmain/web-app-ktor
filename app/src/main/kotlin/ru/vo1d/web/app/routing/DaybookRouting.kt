package ru.vo1d.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.extensions.getOrNull
import ru.vo1d.web.app.extensions.orFail
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel

fun Route.daybookRouting() = route("/daybook") {
    val repo by closestDI().instance<DaybookRepo>()

    metaRouting(repo)
    timetablesRouting(repo)
    sessionsRouting(repo)
}

private fun Route.metaRouting(repo: DaybookRepo) = route("/meta") {
    get {
        call.respond(repo.meta().orFail())
    }

    get("/levels") {
        call.respond(repo.levels().failIfEmpty())
    }

    get("/degrees") {
        call.respond(repo.degrees().failIfEmpty())
    }

    get("/forms") {
        call.respond(repo.forms().failIfEmpty())
    }

    get("/table-types") {
        call.respond(repo.tableTypes().failIfEmpty())
    }

    get("/groups") {
        call.respond(repo.groups().failIfEmpty())
    }

    get("/start-times") {
        call.respond(repo.startTimes().failIfEmpty())
    }

    get("/session-types") {
        call.respond(repo.sessionTypes().failIfEmpty())
    }

    get("/week-options") {
        call.respond(repo.weekOptions().failIfEmpty())
    }
}

private fun Route.timetablesRouting(repo: DaybookRepo) = route("/timetables") {
    get {
        val queryParams = call.request.queryParameters
        val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

        val list = repo.timetables(page) {
            groupCode = queryParams.getOrNull("group")
            typeId = queryParams.getOrNull("type")
        }
        call.respond(list.failIfEmpty())
    }

    post {
        val timetable = call.receive<TimetableModel>()
        val id = repo.addTimetable(timetable)
        call.respond(HttpStatusCode.Created, id)
    }

    route("/{id}") {
        get {
            val id = call.parameters.getOrFail<Int>("id").failIfNegative()
            call.respond(repo.timetable(id).orFail())
        }

        route("/sessions") {
            get {
                val ttId = call.parameters.getOrFail<Int>("id").failIfNegative()
                val query = Parameters.build {
                    appendAll(call.request.queryParameters)
                    append("timetable", ttId.toString())
                }.toMap()
                    .map { "${it.key}=${it.value.joinToString(",")}" }
                    .joinToString("&")

                call.respondRedirect("/api/v1/daybook/sessions?$query")
            }

            post {
                val input = call.receive<TimetableSessionModel>()

                val junction = if (input.timetableId == null) {
                    val ttId = call.parameters.getOrFail<Int>("id").failIfNegative()
                    input.copy(timetableId = ttId)
                } else input

                repo.addJunction(junction)
                call.respond(HttpStatusCode.Created, junction)
            }
        }
    }
}

private fun Route.sessionsRouting(repo: DaybookRepo) = route("/sessions") {
    get {
        val queryParams = call.request.queryParameters
        val page = queryParams.getOrNull<Int>("page")?.failIfNegative()
        val list = repo.sessions(page) {
            timetableId = queryParams.getOrNull<Int>("timetable")?.failIfNegative()
            typeId = queryParams.getOrNull<Int>("type")?.failIfNegative()
            dayId = queryParams.getOrNull<Int>("day")?.failIfNegative()
            timeId = queryParams.getOrNull<Int>("time")?.failIfNegative()
            weekOptionId = queryParams.getOrNull<Int>("week_option")?.failIfNegative()
        }
        call.respond(list.failIfEmpty())
    }

    post {
        val session = call.receive<SessionModel>()
        val id = repo.addSession(session)
        call.respond(HttpStatusCode.Created, id)
    }
}

