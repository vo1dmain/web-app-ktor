package ru.penzgtu.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.data.entities.timetables.list.timetableFilters
import ru.penzgtu.web.app.data.repos.TimetablesRepo
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.failIfNegative
import ru.penzgtu.web.app.extensions.getOrNull
import ru.penzgtu.web.app.extensions.orFail

fun Route.timetablesRouting() {
    route("/timetables") {
        val repo by closestDI().instance<TimetablesRepo>()

        metaRouting()

        get {
            val queryParams = call.request.queryParameters

            val groupCode = queryParams.getOrNull<String>("group_code")
            val typeId = queryParams.getOrNull<Int>("type_id")
            val page = queryParams.getOrNull<Int>("page")?.failIfNegative()

            val list = repo.list(
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
            call.respond(repo.item(id).orFail())
        }

        get("/week") {
            call.respond(repo.week().orFail())
        }
    }
}

private fun Route.metaRouting() {
    route("/meta") {
        val repo by closestDI().instance<TimetablesRepo>()

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
