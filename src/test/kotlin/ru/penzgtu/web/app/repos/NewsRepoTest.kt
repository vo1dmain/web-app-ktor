package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.dao.ArticleDaoResourceImpl
import ru.penzgtu.web.app.dao.CategoryDaoResourceImpl
import ru.penzgtu.web.app.data.delegates.dao
import ru.penzgtu.web.app.data.repos.NewsRepo

class NewsRepoTest : NewsRepo() {
    override val articleDao by dao<ArticleDaoResourceImpl>()
    override val categoryDao by dao<CategoryDaoResourceImpl>()
}