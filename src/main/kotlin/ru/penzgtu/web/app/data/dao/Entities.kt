package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.entities.news.Article
import ru.penzgtu.web.app.data.entities.news.ArticleView
import ru.penzgtu.web.app.data.entities.news.categories.Category
import ru.penzgtu.web.app.data.entities.qna.Post
import ru.penzgtu.web.app.data.entities.qna.Question
import ru.penzgtu.web.app.data.entities.timetables.main.Timetable
import ru.penzgtu.web.app.data.entities.timetables.main.TimetableView
import ru.penzgtu.web.app.data.entities.timetables.meta.parts.*

//Qna
interface QuestionDao : CrudDao<Question, Int>
interface PostDao : CrudDao<Post, Int>, ListDao<Post>


//Timetables
interface TimetableDao : CrudDao<Timetable, Int>, ListDao<TimetableView>, FilterDao<TimetableView>


//Meta
interface GraduationLevelDao : CrudDao<GraduationLevel, Int>, AllDao<GraduationLevel>
interface EducationFormDao : CrudDao<EducationForm, Int>, AllDao<EducationForm>
interface GroupDao : CrudDao<Group, Int>, AllDao<Group>
interface SessionTypeDao : CrudDao<SessionType, Int>, AllDao<SessionType>
interface TableTypeDao : CrudDao<TableType, Int>, AllDao<TableType>
interface TimePeriodDao : CrudDao<TimePeriod, Int>, AllDao<TimePeriod>
interface WeekOptionDao : CrudDao<WeekOption, Int>, AllDao<WeekOption>


//News
interface ArticleDao : CrudDao<Article, Int>, ListDao<ArticleView>, FilterDao<ArticleView>
interface CategoryDao : CrudDao<Category, Int>, ListDao<Category>, FilterDao<Category>
