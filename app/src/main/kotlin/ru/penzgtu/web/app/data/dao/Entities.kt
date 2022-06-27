package ru.penzgtu.web.app.data.dao

import ru.penzgtu.web.app.data.filters.news.ArticleFilters
import ru.penzgtu.web.app.data.filters.news.CategoryFilters
import ru.penzgtu.web.app.data.filters.daybook.TimetableFilters
import ru.penzgtu.web.entities.daybook.group.GroupModel
import ru.penzgtu.web.entities.daybook.group.form.EducationFormModel
import ru.penzgtu.web.entities.daybook.group.level.GraduationLevelModel
import ru.penzgtu.web.entities.daybook.group.type.TableTypeModel
import ru.penzgtu.web.entities.daybook.timetable.TimetableModel
import ru.penzgtu.web.entities.daybook.timetable.TimetableView
import ru.penzgtu.web.entities.daybook.timetable.period.TimePeriodModel
import ru.penzgtu.web.entities.daybook.timetable.session.SessionTypeModel
import ru.penzgtu.web.entities.daybook.timetable.week.WeekOptionModel
import ru.penzgtu.web.entities.news.article.ArticleModel
import ru.penzgtu.web.entities.news.article.ArticleView
import ru.penzgtu.web.entities.news.category.CategoryModel
import ru.penzgtu.web.entities.qna.answer.AnswerModel
import ru.penzgtu.web.entities.qna.post.PostModel
import ru.penzgtu.web.entities.qna.post.PostView
import ru.penzgtu.web.entities.qna.question.QuestionModel

//Qna
interface QuestionDao : CrudDao<QuestionModel, Int>, ListDao<QuestionModel>
interface AnswerDao : CrudDao<AnswerModel, Int>, ListDao<AnswerModel>
interface PostDao : CrudDao<PostModel, Int>, ListDao<PostView>


//Timetables
interface TimetableDao : CrudDao<TimetableModel, Int>,
    ListDao<TimetableView>,
    FilterDao<TimetableView, TimetableFilters>

interface SessionTypeDao : CrudDao<SessionTypeModel, Int>, AllDao<SessionTypeModel>

interface TimePeriodDao : CrudDao<TimePeriodModel, Int>, AllDao<TimePeriodModel>

interface WeekOptionDao : CrudDao<WeekOptionModel, Int>, AllDao<WeekOptionModel>

//Meta
interface GraduationLevelDao : CrudDao<GraduationLevelModel, Int>, AllDao<GraduationLevelModel>
interface EducationFormDao : CrudDao<EducationFormModel, Int>, AllDao<EducationFormModel>
interface GroupDao : CrudDao<GroupModel, Int>, AllDao<GroupModel>
interface TableTypeDao : CrudDao<TableTypeModel, Int>, AllDao<TableTypeModel>


//News
interface ArticleDao : CrudDao<ArticleModel, Int>,
    ListDao<ArticleView>,
    FilterDao<ArticleView, ArticleFilters>

interface CategoryDao : CrudDao<CategoryModel, Int>,
    ListDao<CategoryModel>,
    FilterDao<CategoryModel, CategoryFilters>
