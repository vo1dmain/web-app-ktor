package ru.vo1d.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.data.filters.daybook.timetableFilters
import ru.vo1d.web.app.data.repos.DaybookRepo
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.failIfNegative
import ru.vo1d.web.app.extensions.getOrNull
import ru.vo1d.web.app.extensions.orFail

fun Route.daybookRouting() {
    route("/daybook") {
        val repo by closestDI().instance<DaybookRepo>()

        metaRouting(repo)
        timetablesRouting(repo)
    }
}

private fun Route.metaRouting(repo: DaybookRepo) {
    route("/meta") {

        get {
            call.respond(repo.meta().orFail())
        }

        get("/levels") {
            call.respond(repo.meta().levels.failIfEmpty())
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
}

private fun Route.timetablesRouting(repo: DaybookRepo) {
    route("/timetables") {
        get {
            val queryParams = call.request.queryParameters

            val groupCode = queryParams.getOrNull<String>("group_code")
            val typeId = queryParams.getOrNull<String>("type_id")
            val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

            val list = repo.timetables(
                timetableFilters {
                    groupCode(groupCode)
                    typeId(typeId)
                },
                page
            )
            call.respond(list.failIfEmpty())
        }

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            call.respond(repo.timetable(id).orFail())
        }
    }
}
