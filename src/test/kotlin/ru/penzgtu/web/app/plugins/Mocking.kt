package ru.penzgtu.web.app.plugins

import io.ktor.server.application.*
import ru.penzgtu.web.app.repos.NewsRepoImplTest
import ru.penzgtu.web.app.routing.NewsRouting

fun Application.configureMocking() {
    NewsRouting.setRepo(NewsRepoImplTest())
}