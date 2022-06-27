package ru.penzgtu.web.app.exposed.repos

import ru.penzgtu.web.app.data.dao.delegates.dao
import ru.penzgtu.web.app.data.repos.NewsRepo
import ru.penzgtu.web.app.exposed.dao.news.ArticleDaoXp
import ru.penzgtu.web.app.exposed.dao.news.CategoryDaoXp

class NewsRepoXp : NewsRepo() {
    override val articleDao by dao<ArticleDaoXp>()
    override val categoryDao by dao<CategoryDaoXp>()
}