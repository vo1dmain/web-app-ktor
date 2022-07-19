package ru.vo1d.web.app.repos

import ru.vo1d.web.app.dao.ArticleDaoRes
import ru.vo1d.web.app.dao.CategoryDaoRes
import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.repos.impl.NewsRepoImpl

class NewsRepoTest : NewsRepoImpl() {
    override val articleDao by crudDao<ArticleDaoRes>()
    override val categoryDao by crudDao<CategoryDaoRes>()
}