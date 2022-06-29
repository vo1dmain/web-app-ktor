package ru.vo1d.web.app.repos

import ru.vo1d.web.app.dao.ArticleDaoRes
import ru.vo1d.web.app.dao.CategoryDaoRes
import ru.vo1d.web.app.data.dao.delegates.dao
import ru.vo1d.web.app.data.repos.impl.NewsRepoImpl

class NewsRepoTest : NewsRepoImpl() {
    override val articleDao by dao<ArticleDaoRes>()
    override val categoryDao by dao<CategoryDaoRes>()
}