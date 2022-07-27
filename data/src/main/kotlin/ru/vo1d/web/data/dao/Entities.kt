package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.daybook.group.GroupDto
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.TimetableDto
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.DatedSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel
import ru.vo1d.web.entities.daybook.timetable.time.StartTimeModel
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


//Timetable
interface TimetableDao : CrudDao<Int, TimetableModel>,
    LinkedReadDao<Int, TimetableDto<*>>,
    ListDao<TimetableModel>,
    FilterDao<TimetableModel, TimetableFilters>

interface RegularSessionDao : CrudDao<Int, RegularSessionModel>, ListDao<RegularSessionModel>,
    FilterDao<RegularSessionModel, RegularSessionFilters>

interface DatedSessionDao : CrudDao<Int, DatedSessionModel>, ListDao<DatedSessionModel>,
    FilterDao<DatedSessionModel, DatedSessionFilters>

interface TimetableRegularSessionDao : CreateDao<Unit, TimetableSessionModel>, DeleteDao<TimetableSessionModel>
interface TimetableDatedSessionDao : CreateDao<Unit, TimetableSessionModel>, DeleteDao<TimetableSessionModel>


//Group
interface GradLevelDao : CrudDao<String, GradLevelModel>, AllDao<GradLevelModel>

interface GradDegreeDao : CrudDao<String, GradDegreeModel>, AllDao<GradDegreeModel>

interface EduFormDao : CrudDao<String, EduFormModel>, AllDao<EduFormModel>

interface GroupDao : CudDao<String, GroupModel>, ReadDao<String, GroupDto>, AllDao<GroupDto>


//Meta
interface TableTypeDao : CrudDao<String, TableTypeModel>, AllDao<TableTypeModel>

interface SessionTypeDao : CrudDao<Int, SessionTypeModel>, AllDao<SessionTypeModel>

interface StartTimeDao : CrudDao<Int, StartTimeModel>, AllDao<StartTimeModel>


//News
interface ArticleDao : CrudDao<Int, ArticleModel>, ListDao<ArticleView>, FilterDao<ArticleView, ArticleFilters>

interface CategoryDao : CrudDao<Int, CategoryModel>, ListDao<CategoryModel>, FilterDao<CategoryModel, CategoryFilters>
