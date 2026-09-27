package ru.vo1d.web.domain.dao

import ru.vo1d.web.domain.filters.news.ArticleFilters
import ru.vo1d.web.domain.filters.news.CategoryFilters
import ru.vo1d.web.domain.news.article.Article
import ru.vo1d.web.domain.news.article.ArticleView
import ru.vo1d.web.domain.news.category.Category

interface ArticleDao : Dao<Int, Article>

interface CategoryDao : Dao<Int, Category>, Pageable<Category>, Filterable<CategoryFilters, Category>

interface ArticleViewDao : Pageable<ArticleView>, Filterable<ArticleFilters, ArticleView>