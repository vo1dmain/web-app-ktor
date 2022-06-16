package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.entities.news.articles.Article
import ru.penzgtu.web.app.data.entities.news.articles.ArticleFilters
import ru.penzgtu.web.app.data.entities.news.articles.ArticleView
import ru.penzgtu.web.app.data.entities.news.categories.Category
import ru.penzgtu.web.app.data.entities.news.categories.CategoryFilters
import ru.penzgtu.web.app.data.entities.qna.answers.Answer
import ru.penzgtu.web.app.data.entities.qna.posts.Post
import ru.penzgtu.web.app.data.entities.qna.posts.PostView
import ru.penzgtu.web.app.data.entities.qna.questions.Question
import ru.penzgtu.web.app.data.entities.timetables.list.Timetable
import ru.penzgtu.web.app.data.entities.timetables.list.TimetableFilters
import ru.penzgtu.web.app.data.entities.timetables.list.TimetableView
import ru.penzgtu.web.app.data.entities.timetables.meta.parts.*

//Qna
interface QuestionDao : CrudDao<Question, Int>,
    ListDao<Question>

interface AnswerDao : CrudDao<Answer, Int>,
    ListDao<Answer>

interface PostDao : CrudDao<Post, Int>, ListDao<PostView>


//Timetables
interface TimetableDao : CrudDao<Timetable, Int>,
    ListDao<TimetableView>,
    FilterDao<TimetableView, TimetableFilters>


//Meta
interface GraduationLevelDao : CrudDao<GraduationLevel, Int>, AllDao<GraduationLevel>
interface EducationFormDao : CrudDao<EducationForm, Int>, AllDao<EducationForm>
interface GroupDao : CrudDao<Group, Int>, AllDao<Group>
interface SessionTypeDao : CrudDao<SessionType, Int>, AllDao<SessionType>
interface TableTypeDao : CrudDao<TableType, Int>, AllDao<TableType>
interface TimePeriodDao : CrudDao<TimePeriod, Int>, AllDao<TimePeriod>
interface WeekOptionDao : CrudDao<WeekOption, Int>, AllDao<WeekOption>


//News
interface ArticleDao : CrudDao<Article, Int>,
    ListDao<ArticleView>,
    FilterDao<ArticleView, ArticleFilters>

interface CategoryDao : CrudDao<Category, Int>,
    ListDao<Category>,
    FilterDao<Category, CategoryFilters>
