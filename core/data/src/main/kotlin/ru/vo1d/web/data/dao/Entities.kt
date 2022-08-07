package ru.vo1d.web.data.dao

import ru.vo1d.web.data.filters.daybook.DatedSessionFilters
import ru.vo1d.web.data.filters.daybook.RegularSessionFilters
import ru.vo1d.web.data.filters.daybook.TimetableFilters
import ru.vo1d.web.data.filters.news.ArticleFilters
import ru.vo1d.web.data.filters.news.CategoryFilters
import ru.vo1d.web.entities.daybook.group.Group
import ru.vo1d.web.entities.daybook.group.GroupWithTypes
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.entities.daybook.timetable.Timetable
import ru.vo1d.web.entities.daybook.timetable.TimetableWithSessions
import ru.vo1d.web.entities.daybook.timetable.session.DatedSession
import ru.vo1d.web.entities.daybook.timetable.session.RegularSession
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSession
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.article.ArticleView
import ru.vo1d.web.entities.news.category.Category
import ru.vo1d.web.entities.qna.answer.Answer
import ru.vo1d.web.entities.qna.post.Post
import ru.vo1d.web.entities.qna.post.PostView
import ru.vo1d.web.entities.qna.post.PostWithData
import ru.vo1d.web.entities.qna.question.Question

//Qna
interface QuestionDao : CrudDao<Int, Question>, ListDao<Question>
interface AnswerDao : CrudDao<Int, Answer>, ListDao<Answer>
interface PostDao : CudDao<Int, Post>, ReadDao<Int, PostWithData>, ListDao<PostView>


//Timetable
interface TimetableDao : CrudDao<Int, Timetable>,
    LinkedReadDao<Int, TimetableWithSessions<*>>,
    ListDao<Timetable>,
    FilterDao<Timetable, TimetableFilters>

interface RegularSessionDao : CrudDao<Int, RegularSession>, ListDao<RegularSession>,
    FilterDao<RegularSession, RegularSessionFilters>

interface DatedSessionDao : CrudDao<Int, DatedSession>, ListDao<DatedSession>,
    FilterDao<DatedSession, DatedSessionFilters>

interface TimetableRegularSessionDao : CreateDao<Unit, TimetableSession>, DeleteDao<TimetableSession>
interface TimetableDatedSessionDao : CreateDao<Unit, TimetableSession>, DeleteDao<TimetableSession>


//Group
interface GradLevelDao : CrudDao<String, GraduationLevel>, AllDao<GraduationLevel>

interface GradDegreeDao : CrudDao<String, GraduationDegree>, AllDao<GraduationDegree>

interface EduFormDao : CrudDao<String, EducationForm>, AllDao<EducationForm>

interface GroupDao : CudDao<String, Group>, ReadDao<String, GroupWithTypes>, AllDao<GroupWithTypes>


//Meta
interface TableTypeDao : CrudDao<String, TableType>, AllDao<TableType>

interface SessionTypeDao : CrudDao<Int, SessionType>, AllDao<SessionType>

interface StartTimeDao : CrudDao<Int, StartTime>, AllDao<StartTime>


//News
interface ArticleDao : CrudDao<Int, Article>, ListDao<ArticleView>, FilterDao<ArticleView, ArticleFilters>

interface CategoryDao : CrudDao<Int, Category>, ListDao<Category>, FilterDao<Category, CategoryFilters>
