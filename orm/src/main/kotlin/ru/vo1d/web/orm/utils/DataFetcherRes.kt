package ru.vo1d.web.orm.utils

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import ru.vo1d.web.data.extensions.open
import ru.vo1d.web.entities.daybook.group.GroupModel
import ru.vo1d.web.entities.daybook.group.degree.GradDegreeModel
import ru.vo1d.web.entities.daybook.group.form.EduFormModel
import ru.vo1d.web.entities.daybook.group.level.GradLevelModel
import ru.vo1d.web.entities.daybook.group.type.TableTypeModel
import ru.vo1d.web.entities.daybook.timetable.TimetableModel
import ru.vo1d.web.entities.daybook.timetable.period.TimePeriodModel
import ru.vo1d.web.entities.daybook.timetable.week.WeekOptionModel
import ru.vo1d.web.entities.news.article.ArticleModel
import ru.vo1d.web.entities.news.category.CategoryModel
import ru.vo1d.web.entities.qna.answer.AnswerModel
import ru.vo1d.web.entities.qna.question.QuestionModel
import ru.vo1d.web.orm.entities.daybook.group.*
import ru.vo1d.web.orm.entities.daybook.timetable.TimePeriods
import ru.vo1d.web.orm.entities.daybook.timetable.Timetables
import ru.vo1d.web.orm.entities.daybook.timetable.WeekOptions
import ru.vo1d.web.orm.entities.news.ArticleCategories
import ru.vo1d.web.orm.entities.news.Articles
import ru.vo1d.web.orm.entities.news.Categories
import ru.vo1d.web.orm.entities.qna.Answers
import ru.vo1d.web.orm.entities.qna.Posts
import ru.vo1d.web.orm.entities.qna.Questions
import ru.vo1d.web.orm.extensions.resource

@OptIn(ExperimentalSerializationApi::class)
object DataFetcherRes {
    private val json = Json { ignoreUnknownKeys = true }

    fun fetchNews() {
        val categoriesFile = resource("/data/news/categories.json")
        val newsFile = resource("/data/news/articles.json")

        runBlocking {
            val categories = categoriesFile.open {
                json.decodeFromStream<List<CategoryModel>>(this@open)
            }
            Categories.batchInsert(categories, shouldReturnGeneratedValues = false) {
                this[Categories.title] = it.title
                this[Categories.parentId] = it.parentId
            }

            newsFile.open {
                json.decodeFromStream<List<ArticleModel>>(this@open)
            }.forEach { item ->
                val itemId = Articles.insertAndGetId {
                    it[title] = item.title
                    it[body] = item.body
                    it[preview] = item.previewImage
                    it[gallery] = item.gallery?.joinToString(",")
                    it[dateTime] = item.dateTime
                }.value

                ArticleCategories.batchInsert(item.categories, shouldReturnGeneratedValues = false) { id ->
                    this[ArticleCategories.articleId] = itemId
                    this[ArticleCategories.categoryId] = id
                }
            }
        }
    }

    fun fetchQna() {
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
                val qId = Questions.insertAndGetId {
                    it[theme] = item.theme
                    it[body] = item.body
                    it[acceptorId] = item.acceptorId
                    it[email] = item.email
                    it[dateTime] = item.dateTime
                }.value

                answers.firstOrNull { it.questionId == qId }?.let { answer ->
                    val aId = Answers.insertAndGetId {
                        it[body] = answer.body
                        it[questionId] = qId
                        it[dateTime] = answer.dateTime
                    }.value

                    Posts.insert {
                        it[answerId] = aId
                        it[questionId] = qId
                    }
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

        val periodsFile = resource("/data/daybook/timetable/time-periods.json")
        val weekOptionsFile = resource("/data/daybook/timetable/week-options.json")
        val timetablesFile = resource("/data/daybook/timetable/timetables.json")

        runBlocking {
            val levels = levelsFile.open {
                json.decodeFromStream<List<GradLevelModel>>(this)
            }
            GraduationLevels.batchInsert(levels, shouldReturnGeneratedValues = false) {
                this[GraduationLevels.id] = it.id
                this[GraduationLevels.title] = it.title
            }

            val degrees = degreesFile.open {
                json.decodeFromStream<List<GradDegreeModel>>(this)
            }
            GraduationDegrees.batchInsert(degrees, shouldReturnGeneratedValues = false) {
                this[GraduationDegrees.id] = it.id
                this[GraduationDegrees.title] = it.title
            }

            val forms = formsFile.open {
                json.decodeFromStream<List<EduFormModel>>(this)
            }
            EducationForms.batchInsert(forms, shouldReturnGeneratedValues = false) {
                this[EducationForms.id] = it.id
                this[EducationForms.title] = it.title
            }

            val types = typesFile.open {
                json.decodeFromStream<List<TableTypeModel>>(this)
            }
            TableTypes.batchInsert(types, shouldReturnGeneratedValues = false) {
                this[TableTypes.id] = it.id
                this[TableTypes.title] = it.title
            }

            val groups = groupsFile.open {
                json.decodeFromStream<List<GroupModel>>(this)
            }
            Groups.batchInsert(groups, shouldReturnGeneratedValues = false) {
                this[Groups.id] = it.code
                this[Groups.levelId] = it.levelId
                this[Groups.degreeId] = it.degreeId
                this[Groups.formId] = it.formId
            }

            val periods = periodsFile.open {
                json.decodeFromStream<List<TimePeriodModel>>(this)
            }
            TimePeriods.batchInsert(periods, shouldReturnGeneratedValues = false) {
                this[TimePeriods.start] = it.start
                this[TimePeriods.end] = it.end
            }

            val weekOptions = weekOptionsFile.open {
                json.decodeFromStream<List<WeekOptionModel>>(this)
            }
            WeekOptions.batchInsert(weekOptions, shouldReturnGeneratedValues = false) {
                this[WeekOptions.title] = it.title
            }

            val timetables = timetablesFile.open {
                json.decodeFromStream<List<TimetableModel>>(this)
            }
            Timetables.batchInsert(timetables, shouldReturnGeneratedValues = false) {
                this[Timetables.groupCode] = it.groupCode
                this[Timetables.typeId] = it.typeId
            }
        }
    }
}