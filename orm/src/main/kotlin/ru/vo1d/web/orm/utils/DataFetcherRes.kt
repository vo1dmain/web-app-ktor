package ru.vo1d.web.orm.utils

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.session.SessionTypeModel
import ru.vo1d.web.entities.daybook.timetable.time.StartTimeModel
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.orm.dao.daybook.group.EduFormDaoXp
import ru.vo1d.web.orm.dao.daybook.group.GradDegreeDaoXp
import ru.vo1d.web.orm.dao.daybook.group.GradLevelDaoXp
import ru.vo1d.web.orm.dao.daybook.group.TableTypeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.SessionTypeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.StartTimeDaoXp
import ru.vo1d.web.orm.dao.news.CategoryDaoXp
import ru.vo1d.web.orm.extensions.resource

@OptIn(ExperimentalSerializationApi::class)
object DataFetcherRes {
    private val json = Json { ignoreUnknownKeys = true }

    fun fetchNews() {
        val categoriesFile = resource("/data/news/categories.json")

        runBlocking {
            val categories = categoriesFile.open {
                json.decodeFromStream<Array<CategoryModel>>(this@open)
            }
            CategoryDaoXp().create(*categories)
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


            val times = startTimesFile.open {
                json.decodeFromStream<Array<StartTimeModel>>(this)
            }
            StartTimeDaoXp().create(*times)

            val sessionTypes = sessionTypesFile.open {
                json.decodeFromStream<Array<SessionTypeModel>>(this)
            }
            SessionTypeDaoXp().create(*sessionTypes)
        }
    }
}