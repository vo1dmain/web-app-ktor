package ru.vo1d.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.errors.UnprocessableEntityException
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.orFail
import ru.vo1d.web.app.resources.daybook.DatedSessions
import ru.vo1d.web.app.resources.daybook.Meta
import ru.vo1d.web.app.resources.daybook.RegularSessions
import ru.vo1d.web.app.resources.daybook.Timetables
import ru.vo1d.web.data.repos.DaybookRepo
import ru.vo1d.web.entities.daybook.timetable.TimetableFormat
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.DatedSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
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
        val id = repo.addTimetable(timetable) ?: throw Exception()
        call.respond(HttpStatusCode.Created, id)
    }

    get<Timetables.Id> {
        call.respond(repo.timetable(it.id).orFail())
    }

    postRes<Timetables.Id.Sessions> {
        val input = call.receive<TimetableSessionModel>()

        val parentId = it.parent.id
        val finalId = input.timetableId ?: parentId

        if (finalId != parentId) throw UnprocessableEntityException(
            TimetableSessionModel::timetableId.name,
            parentId.toString(),
            finalId.toString()
        )

        val itemFormat = repo.timetableBase(finalId).orFail().format

        val junction = input.copy(timetableId = finalId)
        when (itemFormat) {
            TimetableFormat.Dated -> repo.addDatedJunction(junction)
            TimetableFormat.Regular -> repo.addRegularJunction(junction)
        }

        call.respond(HttpStatusCode.Created, junction)
    }
}

private fun Route.sessionsRouting(repo: DaybookRepo) {
    get<RegularSessions> {
        val list = repo.regularSessions(it.page) {
            timetableId = it.timetable
            subject = it.subject
            instructor = it.instructor
            place = it.place
            typeId = it.type
            dayOfWeek = it.day
            timeId = it.time
            weekOption = it.weekOption
        }
        call.respond(list.failIfEmpty())
    }

    postRes<RegularSessions> {
        val session = call.receive<RegularSessionModel>()
        val id = repo.addRegularSession(session) ?: throw Exception()
        call.respond(HttpStatusCode.Created, id)
    }

    get<DatedSessions> {
        val list = repo.datedSessions(it.page) {
            timetableId = it.timetable
            subject = it.subject
            instructor = it.instructor
            place = it.place
            typeId = it.type
            datetime = it.datetime
        }
        call.respond(list.failIfEmpty())
    }

    postRes<DatedSessions> {
        val session = call.receive<DatedSessionModel>()
        val id = repo.addDatedSession(session) ?: throw Exception()
        call.respond(HttpStatusCode.Created, id)
    }
}

