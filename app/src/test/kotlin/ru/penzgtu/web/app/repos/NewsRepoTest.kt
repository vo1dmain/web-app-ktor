package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.dao.ArticleDaoRes
import ru.penzgtu.web.app.dao.CategoryDaoRes
import ru.penzgtu.web.app.data.dao.delegates.dao
import ru.penzgtu.web.app.data.repos.NewsRepo

class NewsRepoTest : NewsRepo() {
    override val articleDao by dao<ArticleDaoRes>()
    override val categoryDao by dao<CategoryDaoRes>()
}