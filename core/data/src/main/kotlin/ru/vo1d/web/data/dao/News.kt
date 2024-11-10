package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.Category

interface ArticleDao : Dao<Int, Article>

interface CategoryDao : Dao<Int, Category>, Pageable<Category>, Filterable<CategoryFilters, Category>

interface ArticleViewDao : Pageable<ArticleView>, Filterable<ArticleFilters, ArticleView>