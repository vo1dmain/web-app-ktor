package ru.penzgtu.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.extensions.failIfEmpty
import ru.penzgtu.web.app.extensions.orFail
import ru.penzgtu.web.app.repos.TimetablesRepo

fun Route.timetablesRouting() {
    route("/timetables") {
        val repo by closestDI().instance<TimetablesRepo>()

        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id")

            val queryParams = call.request.queryParameters
            val tableTypeId = queryParams.getOrFail<Int>("table_type")

            val item = repo.item(id, tableTypeId).orFail()
            call.respond(item)
        }

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

            get("/types") {
                call.respond(repo.meta().types.failIfEmpty())
            }

            get("/groups") {
                call.respond(repo.meta().groups.failIfEmpty())
            }
        }
    }
}