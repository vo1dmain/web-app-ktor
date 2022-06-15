package ru.penzgtu.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.penzgtu.web.app.repos.TimetablesRepo

fun Route.timetablesRouting() {
    route("/timetables") {
        val repo by closestDI().instance<TimetablesRepo>()

        get("/meta") {
            call.respond(repo.meta())
        }
    }
}