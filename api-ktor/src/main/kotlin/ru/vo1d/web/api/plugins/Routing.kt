package ru.vo1d.web.api.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.li
import kotlinx.html.ul

/**
 * Installs routing with the route index at `/` and [api] under `/api/v1`.
 */
internal fun Application.apiRouting(api: Route.() -> Unit) {
    install(IgnoreTrailingSlash)

    routing {
        root()
        route("/api/v1", api)
    }
}

private fun Routing.root() = get {
    val root = application.plugin(RoutingRoot)
    val allRoutes = allRoutes(root)
        .filter { it.selector is HttpMethodRouteSelector && it.parent != root }
        .map { it.toString().removeSuffix("(method:GET)") }

    call.respondHtml {
        body {
            ul {
                allRoutes.forEach {
                    val href = it.replace("/\\[.*]".toRegex(), "")

                    li {
                        if (href.contains("(method:POST)")) {
                            +href
                            return@li
                        }
                        a(href.replace("{id}", "1")) { +it }
                    }
                }
            }
        }
    }
}

private fun allRoutes(root: RoutingNode): List<RoutingNode> = listOf(root) + root.children.flatMap { allRoutes(it) }
