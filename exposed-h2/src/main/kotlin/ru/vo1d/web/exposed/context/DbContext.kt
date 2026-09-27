package ru.vo1d.web.exposed.context

import org.jetbrains.exposed.v1.jdbc.Database

abstract class DbContext {
    abstract val news: Database
    abstract val qna: Database
    abstract val daybook: Database

    abstract fun init()
}
