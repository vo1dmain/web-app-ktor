package ru.vo1d.web.server.plugins

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.li
import kotlinx.html.ul
import ru.vo1d.web.server.routing.daybookRouting
import ru.vo1d.web.server.routing.newsRouting
import ru.vo1d.web.server.routing.qnaRouting

fun Application.routing() {
    install(IgnoreTrailingSlash)

    this@routing.routing {
        root()
        route("/api/v1") {
            newsRouting()
            qnaRouting()
            daybookRouting()
        }
    }
}

private fun Routing.root() = get {
    val root = application.plugin(Routing)
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

private fun allRoutes(root: Route): List<Route> = listOf(root) + root.children.flatMap { allRoutes(it) }
