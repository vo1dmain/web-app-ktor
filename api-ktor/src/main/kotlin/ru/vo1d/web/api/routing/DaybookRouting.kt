package ru.vo1d.web.api.routing

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.vo1d.web.api.dto.daybook.*
import ru.vo1d.web.api.errors.UnprocessableEntityException
import ru.vo1d.web.api.extensions.failIfEmpty
import ru.vo1d.web.api.extensions.orFail
import ru.vo1d.web.api.resources.daybook.DatedSessions
import ru.vo1d.web.api.resources.daybook.Meta
import ru.vo1d.web.api.resources.daybook.RegularSessions
import ru.vo1d.web.api.resources.daybook.Timetables
import ru.vo1d.web.api.resources.pageRequest
import ru.vo1d.web.domain.daybook.*
import io.ktor.server.resources.post as postRes

fun Route.daybookRouting(
    service: DaybookService,
    timetableService: TimetableService,
    reference: ReferenceRepository,
    timetables: TimetableRepository,
    regularSessions: RegularSessionRepository,
    datedSessions: DatedSessionRepository
) = route("/daybook") {
    metaRouting(service, reference)
    timetablesRouting(timetables, timetableService)
    sessionsRouting(regularSessions, datedSessions)
}

private fun Route.metaRouting(service: DaybookService, reference: ReferenceRepository) {
    get<Meta> {
        call.respond(service.meta().toResponse())
    }

    get<Meta.Week> {
        call.respond(service.week().toResponse())
    }

    get<Meta.Levels> {
        call.respond(reference.levels().failIfEmpty().map { it.toResponse() })
    }

    get<Meta.Degrees> {
        call.respond(reference.degrees().failIfEmpty().map { it.toResponse() })
    }

    get<Meta.Forms> {
        call.respond(reference.forms().failIfEmpty().map { it.toResponse() })
    }

    get<Meta.TableTypes> {
        call.respond(reference.tableTypes().failIfEmpty().map { it.toResponse() })
    }

    get<Meta.Groups> {
        call.respond(reference.groups().failIfEmpty().map { it.toResponse() })
    }

    get<Meta.SessionTypes> {
        call.respond(reference.sessionTypes().failIfEmpty().map { it.toResponse() })
    }
}

private fun Route.timetablesRouting(timetables: TimetableRepository, timetableService: TimetableService) {
    get<Timetables> {
        val list = timetables.find(
            TimetableFilters(
                groupCode = it.group,
                typeId = it.type,
                format = it.format
            ),
            it.pageRequest()
        )
        call.respond(list.failIfEmpty().map { it.toResponse() })
    }

    postRes<Timetables> {
        val timetable = call.receive<TimetableRequest>().toDomain()
        val id = timetables.add(timetable) ?: throw Exception()
        call.respond(HttpStatusCode.Created, id)
    }

    get<Timetables.Id> {
        call.respond(timetables.get(it.id).orFail().toResponse())
    }

    postRes<Timetables.Id.Sessions> {
        val link = call.receive<TimetableSessionDto>().toDomain()
        val parentId = it.parent.id

        if (link.timetableId != parentId) throw UnprocessableEntityException(
            TimetableSessionDto::timetableId.name,
            parentId.toString(),
            link.timetableId.toString()
        )

        timetableService.attachSession(link).orFail()
        call.respond(HttpStatusCode.Created, link.toDto())
    }
}

private fun Route.sessionsRouting(regularSessions: RegularSessionRepository, datedSessions: DatedSessionRepository) {
    get<RegularSessions> {
        val list = regularSessions.find(
            RegularSessionFilters(
                timetableId = it.timetable,
                subject = it.subject,
                instructor = it.instructor,
                place = it.place,
                typeId = it.type,
                dayOfWeek = it.day,
                time = it.time,
                weekOption = it.weekOption
            ),
            it.pageRequest()
        )
        call.respond(list.failIfEmpty().map { it.toResponse() })
    }

    postRes<RegularSessions> {
        val session = call.receive<RegularSessionRequest>().toDomain()
        val id = regularSessions.add(session) ?: throw Exception()
        call.respond(HttpStatusCode.Created, id)
    }

    get<DatedSessions> {
        val list = datedSessions.find(
            DatedSessionFilters(
                timetableId = it.timetable,
                subject = it.subject,
                instructor = it.instructor,
                place = it.place,
                typeId = it.type,
                dateTime = it.dateTime
            ),
            it.pageRequest()
        )
        call.respond(list.failIfEmpty().map { it.toResponse() })
    }

    postRes<DatedSessions> {
        val session = call.receive<DatedSessionRequest>().toDomain()
        val id = datedSessions.add(session) ?: throw Exception()
        call.respond(HttpStatusCode.Created, id)
    }
}
