package ru.penzgtu.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.getOrNull
import ru.penzgtu.web.app.extensions.orFail
import ru.penzgtu.web.app.repos.TimetablesRepo

fun Route.timetablesRouting() {
    route("/timetables") {
        val repo by closestDI().instance<TimetablesRepo>()

        metaRouting()

        get {
            val queryParams = call.request.queryParameters

            val groupCode = queryParams.getOrNull<String>("group_code")
            val typeId = queryParams.getOrNull<Int>("type_id")

            val list = repo.list(groupCode, typeId).failIfEmpty()
            call.respond(list)
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
