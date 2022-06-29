package ru.vo1d.web.app.routing

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.data.filters.daybook.SessionFilters
import ru.vo1d.web.app.data.filters.daybook.TimetableFilters
import ru.vo1d.web.app.data.repos.DaybookRepo
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.extensions.getOrNull
import ru.vo1d.web.app.extensions.orFail
import ru.vo1d.web.entities.daybook.timetable.TimetableModel

fun Route.daybookRouting() = route("/daybook") {
    val repo by closestDI().instance<DaybookRepo>()

    metaRouting(repo)
    timetablesRouting(repo)
}

private fun Route.metaRouting(repo: DaybookRepo) = route("/meta") {
    get {
        call.respond(repo.meta().orFail())
    }

    get("/levels") {
        call.respond(repo.meta().levels.failIfEmpty())
    }

    get("/degrees") {
        call.respond(repo.meta().degrees.failIfEmpty())
    }

    get("/forms") {
        call.respond(repo.meta().forms.failIfEmpty())
    }

    get("/table-types") {
        call.respond(repo.meta().tableTypes.failIfEmpty())
    }

    get("/groups") {
        call.respond(repo.meta().groups.failIfEmpty())
    }

    get("/periods") {
        call.respond(repo.meta().periods.failIfEmpty())
    }

    get("/session-types") {
        call.respond(repo.meta().sessionTypes.failIfEmpty())
    }

    get("/week-options") {
        call.respond(repo.meta().weekOptions.failIfEmpty())
    }
}

private fun Route.timetablesRouting(repo: DaybookRepo) = route("/timetables") {
    get {
        val queryParams = call.request.queryParameters

        val groupCode = queryParams.getOrNull<String>("group_code")
        val typeId = queryParams.getOrNull<String>("type_id")
        val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

        val list = repo.timetables(
            TimetableFilters.new {
                this.groupCode = groupCode
                this.typeId = typeId
            },
            page
        )
        call.respond(list.failIfEmpty())
    }

    post {
        val timetable = call.receive<TimetableModel>()
        val id = repo.addTimetable(timetable)
        call.respond(HttpStatusCode.Created, id)
    }

    route("/{id}") {
        get {
            val id = call.parameters.getOrFail<Int>("id")
            call.respond(repo.timetable(id).orFail())
        }

        route("/sessions") {
            get {
                val timetableId = call.parameters.getOrFail<Int>("id")

                val queryParams = call.request.queryParameters
                val typeId = queryParams.getOrNull<Int>("type")?.failIfNegative()
                val dayId = queryParams.getOrNull<Int>("day")?.failIfNegative()
                val periodId = queryParams.getOrNull<Int>("period")?.failIfNegative()
                val weekOptionId = queryParams.getOrNull<Int>("week_option")?.failIfNegative()
                val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

                val sessions = repo.sessions(
                    SessionFilters.new {
                        this.timetableId = timetableId
                        this.typeId = typeId
                        this.dayId = dayId
                        this.periodId = periodId
                        this.weekOptionId = weekOptionId
                    },
                    page
                )
                call.respond(sessions.failIfEmpty())
            }
        }
    }
}

