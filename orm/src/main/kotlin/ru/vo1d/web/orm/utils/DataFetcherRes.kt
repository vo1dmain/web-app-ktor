package ru.vo1d.web.orm.utils

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.session.RegularSessionModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.session.TimetableSessionModel
import ru.vo1d.web.entities.daybook.timetable.time.StartTimeModel
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.entities.qna.post.PostModel
import ru.vo1d.web.entities.qna.question.QuestionModel
import ru.vo1d.web.orm.dao.daybook.group.*
import ru.vo1d.web.orm.dao.daybook.timetable.SessionTypeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.StartTimeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.TimetableDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.regular.RegularSessionDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.regular.TimetableRegularSessionDaoXp
import ru.vo1d.web.orm.dao.news.ArticleDaoXp
import ru.vo1d.web.orm.dao.news.CategoryDaoXp
import ru.vo1d.web.orm.dao.qna.AnswerDaoXp
import ru.vo1d.web.orm.dao.qna.PostDaoXp
import ru.vo1d.web.orm.dao.qna.QuestionDaoXp
import ru.vo1d.web.orm.extensions.resource

@OptIn(ExperimentalSerializationApi::class)
object DataFetcherRes {
    private val json = Json { ignoreUnknownKeys = true }

    fun fetchNews() {
        val categoriesFile = resource("/data/news/categories.json")
        val articlesFile = resource("/data/news/articles.json")

        runBlocking {
            val categories = categoriesFile.open {
                json.decodeFromStream<Array<CategoryModel>>(this@open)
            }
            CategoryDaoXp().create(*categories)

            val articles = articlesFile.open {
                json.decodeFromStream<Array<ArticleModel>>(this@open)
            }
            ArticleDaoXp().create(*articles)
        }
    }

    fun fetchQna() {
        val questionDao = QuestionDaoXp()
        val answerDao = AnswerDaoXp()
        val postDao = PostDaoXp()

        val questionsFile = resource("/data/qna/questions.json")
        val answersFile = resource("/data/qna/answers.json")

        runBlocking {
            val questions = questionsFile.open {
                json.decodeFromStream<List<QuestionModel>>(this@open)
            }
            val answers = answersFile.open {
                json.decodeFromStream<List<AnswerModel>>(this@open)
            }

            questions.forEach { item ->
                val qId = questionDao.create(item)

                answers.firstOrNull { it.questionId == qId }
                    ?.let {
                        val aId = answerDao.create(it)
                        postDao.create(PostModel(null, qId, aId))
                    }
            }
        }
    }

    fun fetchDaybook() {
        val levelsFile = resource("/data/daybook/group/levels.json")
        val degreesFile = resource("/data/daybook/group/degrees.json")
        val formsFile = resource("/data/daybook/group/forms.json")
        val typesFile = resource("/data/daybook/group/types.json")
        val groupsFile = resource("/data/daybook/group/groups.json")

        val startTimesFile = resource("/data/daybook/timetable/start-times.json")
        val sessionTypesFile = resource("/data/daybook/timetable/session-types.json")
        val timetablesFile = resource("/data/daybook/timetable/timetables.json")
        val sessionsFile = resource("/data/daybook/timetable/regular-sessions.json")
        val timetableSessionsFile = resource("/data/daybook/timetable/timetable-regular-sessions.json")

        runBlocking<Unit> {
            val levels = levelsFile.open {
                json.decodeFromStream<Array<GradLevelModel>>(this)
            }
            GradLevelDaoXp().create(*levels)

            val degrees = degreesFile.open {
                json.decodeFromStream<Array<GradDegreeModel>>(this)
            }
            GradDegreeDaoXp().create(*degrees)

            val forms = formsFile.open {
                json.decodeFromStream<Array<EduFormModel>>(this)
            }
            EduFormDaoXp().create(*forms)

            val types = typesFile.open {
                json.decodeFromStream<Array<TableTypeModel>>(this)
            }
            TableTypeDaoXp().create(*types)

            val groups = groupsFile.open {
                json.decodeFromStream<Array<GroupModel>>(this)
            }
            GroupDaoXp().create(*groups)


            val times = startTimesFile.open {
                json.decodeFromStream<Array<StartTimeModel>>(this)
            }
            StartTimeDaoXp().create(*times)

            val sessionTypes = sessionTypesFile.open {
                json.decodeFromStream<Array<SessionTypeModel>>(this)
            }
            SessionTypeDaoXp().create(*sessionTypes)

            val timetables = timetablesFile.open {
                json.decodeFromStream<Array<TimetableModel>>(this)
            }
            TimetableDaoXp().create(*timetables)

            val sessions = sessionsFile.open {
                json.decodeFromStream<Array<RegularSessionModel>>(this)
            }
            RegularSessionDaoXp().create(*sessions)

            val timetableSessions = timetableSessionsFile.open {
                json.decodeFromStream<Array<TimetableSessionModel>>(this)
            }
            TimetableRegularSessionDaoXp().create(*timetableSessions)
        }
    }
}