package ru.vo1d.web.domain.dao

import ru.vo1d.web.domain.qna.answer.Answer
import ru.vo1d.web.domain.qna.post.Post
import ru.vo1d.web.domain.qna.post.PostView
import ru.vo1d.web.domain.qna.question.Question

interface QuestionDao : Dao<Int, Question>, Pageable<Question>

interface AnswerDao : Dao<Int, Answer>, Pageable<Answer>

interface PostDao : Dao<Int, Post>

interface PostViewDao: Pageable<PostView>