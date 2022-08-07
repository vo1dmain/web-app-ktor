package ru.vo1d.web.server.plugins

import io.ktor.server.application.*
import io.ktor.server.resources.*

fun Application.resources() {
    install(Resources)
}