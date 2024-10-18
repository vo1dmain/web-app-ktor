package ru.vo1d.web.server.repos

import ru.vo1d.web.data.dao.delegates.crudDao
import ru.vo1d.web.data.repos.base.NewsRepoBase
import ru.vo1d.web.server.dao.ArticleDaoRes
import ru.vo1d.web.server.dao.CategoryDaoRes

class NewsRepoTest : NewsRepoBase() {
    override val articleDao by crudDao<ArticleDaoRes>()
    override val categoryDao by crudDao<CategoryDaoRes>()
}