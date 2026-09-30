package ru.vo1d.web.app

import kotlinx.datetime.TimeZone
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.plugin.module.dsl.single
import ru.vo1d.web.domain.daybook.*
import ru.vo1d.web.domain.news.ArticleRepository
import ru.vo1d.web.domain.news.CategoryRepository
import ru.vo1d.web.domain.qna.PostRepository
import ru.vo1d.web.domain.qna.QuestionRepository
import ru.vo1d.web.persistence.context.DatabaseConfig
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.daybook.DatedSessionRepositoryXp
import ru.vo1d.web.persistence.daybook.ReferenceRepositoryXp
import ru.vo1d.web.persistence.daybook.RegularSessionRepositoryXp
import ru.vo1d.web.persistence.daybook.TimetableRepositoryXp
import ru.vo1d.web.persistence.news.ArticleRepositoryXp
import ru.vo1d.web.persistence.news.CategoryRepositoryXp
import ru.vo1d.web.persistence.qna.PostRepositoryXp
import ru.vo1d.web.persistence.qna.QuestionRepositoryXp
import kotlin.time.Clock

/**
 * @param config database settings from the `database` section of the config
 */
fun persistenceModule(config: DatabaseConfig) = module {
    single<DatabaseConfig> { config }
    single<DbContext>()

    single<ArticleRepositoryXp>() bind ArticleRepository::class
    single<CategoryRepositoryXp>() bind CategoryRepository::class

    single<QuestionRepositoryXp>() bind QuestionRepository::class
    single<PostRepositoryXp>() bind PostRepository::class

    single<ReferenceRepositoryXp>() bind ReferenceRepository::class
    single<TimetableRepositoryXp>() bind TimetableRepository::class
    single<RegularSessionRepositoryXp>() bind RegularSessionRepository::class
    single<DatedSessionRepositoryXp>() bind DatedSessionRepository::class
}

/**
 * @param timeZone the daybook zone from `daybook.timeZone` in the config
 */
fun domainModule(timeZone: TimeZone) = module {
    single<TimeZone> { timeZone }
    single<Clock> { Clock.System }

    single<DaybookService>()
    single<TimetableService>()
}
