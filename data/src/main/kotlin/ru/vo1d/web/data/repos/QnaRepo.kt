package ru.vo1d.web.data.repos

import ru.vo1d.web.entities.qna.post.PostDto
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.QuestionModel

interface QnaRepo : ListRepo {
    suspend fun posts(page: Int?): List<PostView>

    suspend fun post(id: Int): PostDto?


    suspend fun questions(page: Int?): List<QuestionModel>

    suspend fun addQuestion(question: QuestionModel): Int
}