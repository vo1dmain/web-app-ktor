package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.entities.news.Article
import ru.penzgtu.web.app.data.entities.news.ArticleView

interface ArticleDao : CrudDao<Article, Int>, ListDao<ArticleView>, FilterDao<ArticleView>