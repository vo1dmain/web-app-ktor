package ru.vo1d.web.server.routing

import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.data.repos.NewsRepo
import ru.vo1d.web.server.extensions.failIfEmpty
import ru.vo1d.web.server.extensions.orFail
import ru.vo1d.web.server.resources.news.Articles
import ru.vo1d.web.server.resources.news.Categories

fun Route.newsRouting() = route("/news") {
    val repo by closestDI().instance<NewsRepo>()

    articlesRouting(repo)
    categoriesRouting(repo)
}

private fun Route.articlesRouting(repo: NewsRepo) {
    get<Articles> {
        val list = repo.articles(
            it.page,
            ArticleFilters(categories = it.categories)
        )
        call.respond(list.failIfEmpty())
    }

    get<Articles.Id> {
        call.respond(repo.article(it.id).orFail())
    }
}

private fun Route.categoriesRouting(repo: NewsRepo) {
    get<Categories> {
        val list = repo.categories(
            it.page,
            CategoryFilters(parentId = it.parent)
        )
        call.respond(list.failIfEmpty())
    }

    get<Categories.Id> {
        call.respond(repo.category(it.id).orFail())
    }
}
