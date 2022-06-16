package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.entities.qna.Post

interface PostDao : CrudDao<Post, Int>, ListDao<Post>