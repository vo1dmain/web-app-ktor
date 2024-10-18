package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.Category

interface ArticleDao :
    CreateDao<Int, Article>,
    ReadDao<Int, Article>,
    UpdateDao<Article>,
    DeleteDao<Int>,
    ListDao<ArticleView>,
    FilterDao<ArticleView, ArticleFilters>

interface CategoryDao :
    CreateDao<Int, Category>,
    ReadDao<Int, Category>,
    UpdateDao<Category>,
    DeleteDao<Int>,
    ListDao<Category>,
    FilterDao<Category, CategoryFilters>