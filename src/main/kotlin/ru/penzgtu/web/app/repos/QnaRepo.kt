package ru.penzgtu.web.app.repos

import ru.penzgtu.web.app.data.qna.Post
import ru.penzgtu.web.app.data.qna.Question

interface QnaRepo {
    suspend fun list(page: Int = defaultPage): List<Post>

    suspend fun add(question: Question)

    companion object {
        const val defaultPage = 0
    }
}