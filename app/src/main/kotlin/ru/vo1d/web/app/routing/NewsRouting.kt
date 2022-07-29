package ru.vo1d.web.app.routing

import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.app.extensions.failIfEmpty
import ru.vo1d.web.app.extensions.orFail
import ru.vo1d.web.app.resources.news.Articles
import ru.vo1d.web.app.resources.news.Categories
import ru.vo1d.web.data.repos.NewsRepo

fun Route.newsRouting() = route("/news") {
    val repo by closestDI().instance<NewsRepo>()

    articlesRouting(repo)
    categoriesRouting(repo)
}

private fun Route.articlesRouting(repo: NewsRepo) {
    get<Articles> {
        val list = repo.articles(it.page) {
            categories = it.categories
        }
        call.respond(list.failIfEmpty())
    }

    get<Articles.Id> {
        call.respond(repo.article(it.id).orFail())
    }
}

private fun Route.categoriesRouting(repo: NewsRepo) {
    get<Categories> {
        val list = repo.categories(it.page) {
            parentId = it.parent
        }
        call.respond(list.failIfEmpty())
    }

    get<Categories.Id> {
        call.respond(repo.category(it.id).orFail())
    }
}
