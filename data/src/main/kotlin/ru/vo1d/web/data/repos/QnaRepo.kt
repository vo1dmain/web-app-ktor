package ru.vo1d.web.data.repos

import ru.vo1d.web.entities.qna.post.PostWithData
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.Question

interface QnaRepo : ListRepo {
    suspend fun posts(page: Int?): List<PostView>

    suspend fun post(id: Int): PostWithData?


    suspend fun questions(page: Int?): List<Question>

    suspend fun question(id: Int): Question?

    suspend fun addQuestion(question: Question): Int?
}