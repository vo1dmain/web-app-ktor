package ru.vo1d.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.orFail
import ru.vo1d.web.app.resources.daybook.Meta
import ru.vo1d.web.app.resources.daybook.Sessions
import ru.vo1d.web.app.resources.daybook.Timetables
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionModel
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel
import io.ktor.server.resources.post as postRes

fun Route.daybookRouting() = route("/daybook") {
    val repo by closestDI().instance<DaybookRepo>()

    metaRouting(repo)
    timetablesRouting(repo)
    sessionsRouting(repo)
}

private fun Route.metaRouting(repo: DaybookRepo) {
    get<Meta> {
        call.respond(repo.meta().orFail())
    }

    get<Meta.Levels> {
        call.respond(repo.levels().failIfEmpty())
    }

    get<Meta.Degrees> {
        call.respond(repo.degrees().failIfEmpty())
    }

    get<Meta.Forms> {
        call.respond(repo.forms().failIfEmpty())
    }

    get<Meta.TableTypes> {
        call.respond(repo.tableTypes().failIfEmpty())
    }

    get<Meta.Groups> {
        call.respond(repo.groups().failIfEmpty())
    }

    get<Meta.Times> {
        call.respond(repo.startTimes().failIfEmpty())
    }

    get<Meta.SessionTypes> {
        call.respond(repo.sessionTypes().failIfEmpty())
    }

    get<Meta.WeekOptions> {
        call.respond(repo.weekOptions().failIfEmpty())
    }
}

private fun Route.timetablesRouting(repo: DaybookRepo) {
    get<Timetables> {
        val list = repo.timetables(it.page) {
            groupCode = it.group
            typeId = it.type
        }
        call.respond(list.failIfEmpty())
    }

    postRes<Timetables> {
        val timetable = call.receive<TimetableModel>()
        val id = repo.addTimetable(timetable)
        call.respond(HttpStatusCode.Created, id)
    }

    get<Timetables.Id> {
        call.respond(repo.timetable(it.id).orFail())
    }

    get<Timetables.Id.Sessions> {
        val query = Parameters.build {
            appendAll(call.request.queryParameters)
            append("timetable", it.parent.id.toString())
        }.toMap()
            .map { "${it.key}=${it.value.joinToString(",")}" }
            .joinToString("&")

        call.respondRedirect("/api/v1/daybook/sessions?$query")
    }

    postRes<Timetables.Id.Sessions> {
        val input = call.receive<TimetableSessionModel>()
        val junction = if (input.timetableId == null) input.copy(timetableId = it.parent.id) else input

        repo.addJunction(junction)
        call.respond(HttpStatusCode.Created, junction)
    }
}

private fun Route.sessionsRouting(repo: DaybookRepo) {
    get<Sessions> {
        val list = repo.sessions(it.page) {
            timetableId = it.timetable
            typeId = it.type
            dayId = it.day
            timeId = it.time
            weekOptionId = it.weekOption
        }.failIfEmpty()
        call.respond(list)
    }

    postRes<Sessions> {
        val session = call.receive<SessionModel>()
        val id = repo.addSession(session)
        call.respond(HttpStatusCode.Created, id)
    }
}

