package ru.vo1d.web.data.dao

import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.entities.qna.post.Post
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.post.PostWithData
import ru.vo1d.web.entities.qna.question.Question

interface QuestionDao :
    CreateDao<Int, Question>,
    ReadDao<Int, Question>,
    UpdateDao<Question>,
    DeleteDao<Int>,
    ListDao<Question>

interface AnswerDao :
    CreateDao<Int, Answer>,
    ReadDao<Int, Answer>,
    UpdateDao<Answer>,
    DeleteDao<Int>,
    ListDao<Answer>

interface PostDao :
    CreateDao<Int, Post>,
    ReadDao<Int, PostWithData>,
    UpdateDao<Post>,
    DeleteDao<Int>,
    ListDao<PostView>