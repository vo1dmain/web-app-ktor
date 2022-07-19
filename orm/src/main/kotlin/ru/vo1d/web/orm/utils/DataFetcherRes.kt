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
import ru.vo1d.web.entities.daybook.timetable.period.StartTimeModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.entities.qna.post.PostModel
import ru.vo1d.web.entities.qna.question.QuestionModel
import ru.vo1d.web.orm.dao.daybook.group.*
import ru.vo1d.web.orm.dao.daybook.timetable.StartTimeDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.TimetableDaoXp
import ru.vo1d.web.orm.dao.daybook.timetable.WeekOptionDaoXp
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

        val periodsFile = resource("/data/daybook/timetable/start-times.json")
        val weekOptionsFile = resource("/data/daybook/timetable/week-options.json")
        val timetablesFile = resource("/data/daybook/timetable/timetables.json")

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

            val periods = periodsFile.open {
                json.decodeFromStream<Array<StartTimeModel>>(this)
            }
            StartTimeDaoXp().create(*periods)

            val weekOptions = weekOptionsFile.open {
                json.decodeFromStream<Array<WeekOptionModel>>(this)
            }
            WeekOptionDaoXp().create(*weekOptions)

            val timetables = timetablesFile.open {
                json.decodeFromStream<Array<TimetableModel>>(this)
            }
            TimetableDaoXp().create(*timetables)
        }
    }
}