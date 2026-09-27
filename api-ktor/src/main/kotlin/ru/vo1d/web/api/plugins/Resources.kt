package ru.vo1d.web.api.plugins

import io.ktor.server.application.*
import io.ktor.server.resources.*

fun Application.resources() {
    install(Resources)
}