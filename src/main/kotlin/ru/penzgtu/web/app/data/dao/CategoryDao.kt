package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.entities.news.categories.Category

interface CategoryDao : CrudDao<Category, Int>, ListDao<Category>, FilterDao<Category>