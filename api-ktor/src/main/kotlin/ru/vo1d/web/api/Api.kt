package ru.vo1d.web.api

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.vo1d.web.api.plugins.*

/**
 * Installs the HTTP layer: plugins first, since routes need them (Resources above all), then the route index
 * at `/` and [routes] under `/api/v1`.
 */
fun Application.installApi(routes: Route.() -> Unit) {
    logging()
    http()
    contentNegotiation()
    statusPages()
    resources()

    apiRouting(routes)
}
