package ru.vo1d.web.app.data.dao

import ru.vo1d.web.app.data.filters.daybook.TimetableFilters
import ru.vo1d.web.app.data.filters.news.ArticleFilters
import ru.vo1d.web.app.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.TimetableView
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.entities.qna.post.PostDto
import ru.vo1d.web.entities.qna.post.PostModel
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.question.QuestionModel

//Qna
interface QuestionDao : CrudDao<Int, QuestionModel>, ListDao<QuestionModel>
interface AnswerDao : CrudDao<Int, AnswerModel>, ListDao<AnswerModel>
interface PostDao : CudDao<Int, PostModel>, ReadDao<Int, PostDto>, ListDao<PostView>


//Timetables
interface TimetableDao : CrudDao<Int, TimetableModel>, ListDao<TimetableView>,
    FilterDao<TimetableView, TimetableFilters>

interface SessionTypeDao : CrudDao<Int, SessionTypeModel>, AllDao<SessionTypeModel>

interface TimePeriodDao : CrudDao<Int, TimePeriodModel>, AllDao<TimePeriodModel>

interface WeekOptionDao : CrudDao<Int, WeekOptionModel>, AllDao<WeekOptionModel>


//Meta
interface GradLevelDao : CrudDao<String, GradLevelModel>, AllDao<GradLevelModel>

interface GradDegreeDao : CrudDao<String, GradDegreeModel>, AllDao<GradDegreeModel>

interface EduFormDao : CrudDao<String, EduFormModel>, AllDao<EduFormModel>

interface GroupDao : CrudDao<String, GroupModel>, AllDao<GroupModel>

interface TableTypeDao : CrudDao<String, TableTypeModel>, AllDao<TableTypeModel>


//News
interface ArticleDao : CrudDao<Int, ArticleModel>, ListDao<ArticleView>, FilterDao<ArticleView, ArticleFilters>

interface CategoryDao : CrudDao<Int, CategoryModel>, ListDao<CategoryModel>, FilterDao<CategoryModel, CategoryFilters>
