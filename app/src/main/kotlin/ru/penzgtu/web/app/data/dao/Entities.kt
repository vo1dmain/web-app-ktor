package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.filters.news.ArticleFilters
import ru.penzgtu.web.app.data.filters.news.CategoryFilters
import ru.penzgtu.web.app.data.filters.timetables.TimetableFilters
import ru.penzgtu.web.entities.news.articles.ArticleModel
import ru.penzgtu.web.entities.news.articles.ArticleView
import ru.penzgtu.web.entities.news.categories.CategoryModel
import ru.penzgtu.web.entities.qna.answers.AnswerModel
import ru.penzgtu.web.entities.qna.posts.PostModel
import ru.penzgtu.web.entities.qna.posts.PostView
import ru.penzgtu.web.entities.qna.questions.QuestionModel
import ru.penzgtu.web.entities.timetables.list.TimetableModel
import ru.penzgtu.web.entities.timetables.list.TimetableView
import ru.penzgtu.web.entities.timetables.meta.parts.*

//Qna
interface QuestionDao : CrudDao<QuestionModel, Int>, ListDao<QuestionModel>
interface AnswerDao : CrudDao<AnswerModel, Int>, ListDao<AnswerModel>
interface PostDao : CrudDao<PostModel, Int>, ListDao<PostView>


//Timetables
interface TimetableDao : CrudDao<TimetableModel, Int>,
    ListDao<TimetableView>,
    FilterDao<TimetableView, TimetableFilters>


//Meta
interface GraduationLevelDao : CrudDao<GraduationLevel, Int>, AllDao<GraduationLevel>
interface EducationFormDao : CrudDao<EducationForm, Int>, AllDao<EducationForm>
interface GroupDao : CrudDao<GroupModel, Int>, AllDao<GroupModel>
interface SessionTypeDao : CrudDao<SessionTypeModel, Int>, AllDao<SessionTypeModel>
interface TableTypeDao : CrudDao<TableTypeModel, Int>, AllDao<TableTypeModel>
interface TimePeriodDao : CrudDao<TimePeriodModel, Int>, AllDao<TimePeriodModel>
interface WeekOptionDao : CrudDao<WeekOptionModel, Int>, AllDao<WeekOptionModel>


//News
interface ArticleDao : CrudDao<ArticleModel, Int>,
    ListDao<ArticleView>,
    FilterDao<ArticleView, ArticleFilters>

interface CategoryDao : CrudDao<CategoryModel, Int>,
    ListDao<CategoryModel>,
    FilterDao<CategoryModel, CategoryFilters>
