package ru.penzgtu.web.app.data.repos.base

import ru.penzgtu.web.app.data.dao.ArticleDao
import ru.penzgtu.web.app.data.dao.CategoryDao
import ru.penzgtu.web.app.data.delegates.dao
import ru.penzgtu.web.app.data.repos.NewsRepo

class NewsRepoBase : NewsRepo() {
    override val articleDao by dao<ArticleDao>()
    override val categoryDao by dao<CategoryDao>()
}