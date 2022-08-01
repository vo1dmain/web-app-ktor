package ru.vo1d.web.app.utils

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.entities.daybook.group.degree.GraduationDegree
import ru.vo1d.web.entities.daybook.group.form.EducationForm
import ru.vo1d.web.entities.daybook.group.level.GraduationLevel
import ru.vo1d.web.entities.daybook.group.type.TableType
import ru.vo1d.web.entities.daybook.timetable.session.SessionType
import ru.vo1d.web.entities.daybook.timetable.time.StartTime
import ru.vo1d.web.entities.news.article.Article
import ru.vo1d.web.entities.news.category.Category
import ru.vo1d.web.orm.dao.daybook.group.EduFormDaoXp
import ru.vo1d.web.orm.dao.daybook.group.GradDegreeDaoXp
import ru.vo1d.web.orm.dao.daybook.group.GradLevelDaoXp
import ru.vo1d.web.orm.dao.daybook.group.TableTypeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.SessionTypeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.StartTimeDaoXp
import ru.vo1d.web.orm.dao.news.ArticleDaoXp
import ru.vo1d.web.orm.dao.news.CategoryDaoXp
import ru.vo1d.web.orm.extensions.resource

@OptIn(ExperimentalSerializationApi::class)
object DataFetcherRes {
    private val json = Json { ignoreUnknownKeys = true }

    fun fetchNews() {
        val categoriesFile = resource("/data/news/categories.json")
        val articlesFile = resource("/data/news/articles.json")

        runBlocking<Unit> {
            val categories = categoriesFile.open {
                json.decodeFromStream<Array<Category>>(this@open)
            }
            CategoryDaoXp().create(*categories)

            val articles = articlesFile.open {
                json.decodeFromStream<Array<Article>>(this@open)
            }
            ArticleDaoXp().create(*articles)
        }
    }

    fun fetchDaybook() {
        val levelsFile = resource("/data/daybook/group/levels.json")
        val degreesFile = resource("/data/daybook/group/degrees.json")
        val formsFile = resource("/data/daybook/group/forms.json")
        val typesFile = resource("/data/daybook/group/types.json")

        val startTimesFile = resource("/data/daybook/timetable/start-times.json")
        val sessionTypesFile = resource("/data/daybook/timetable/session-types.json")

        runBlocking<Unit> {
            val levels = levelsFile.open {
                json.decodeFromStream<Array<GraduationLevel>>(this)
            }
            GradLevelDaoXp().create(*levels)

            val degrees = degreesFile.open {
                json.decodeFromStream<Array<GraduationDegree>>(this)
            }
            GradDegreeDaoXp().create(*degrees)

            val forms = formsFile.open {
                json.decodeFromStream<Array<EducationForm>>(this)
            }
            EduFormDaoXp().create(*forms)

            val types = typesFile.open {
                json.decodeFromStream<Array<TableType>>(this)
            }
            TableTypeDaoXp().create(*types)

            val times = startTimesFile.open {
                json.decodeFromStream<Array<StartTime>>(this)
            }
            StartTimeDaoXp().create(*times)

            val sessionTypes = sessionTypesFile.open {
                json.decodeFromStream<Array<SessionType>>(this)
            }
            SessionTypeDaoXp().create(*sessionTypes)
        }
    }
}