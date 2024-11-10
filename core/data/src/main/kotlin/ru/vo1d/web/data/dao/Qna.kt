package ru.vo1d.web.data.dao

import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.entities.qna.post.Post
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.Question

interface QuestionDao : Dao<Int, Question>, Pageable<Question>

interface AnswerDao : Dao<Int, Answer>, Pageable<Answer>

interface PostDao : Dao<Int, Post>

interface PostViewDao: Pageable<PostView>