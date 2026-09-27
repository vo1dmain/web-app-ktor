package ru.vo1d.web.persistence

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.vo1d.web.domain.daybook.DatedSessionRepository
import ru.vo1d.web.domain.daybook.ReferenceRepository
import ru.vo1d.web.domain.daybook.RegularSessionRepository
import ru.vo1d.web.domain.daybook.TimetableRepository
import ru.vo1d.web.domain.news.ArticleRepository
import ru.vo1d.web.domain.news.CategoryRepository
import ru.vo1d.web.domain.qna.PostRepository
import ru.vo1d.web.domain.qna.QuestionRepository
import ru.vo1d.web.persistence.context.DbContext
import ru.vo1d.web.persistence.context.H2Context
import ru.vo1d.web.persistence.daybook.DatedSessionRepositoryXp
import ru.vo1d.web.persistence.daybook.ReferenceRepositoryXp
import ru.vo1d.web.persistence.daybook.RegularSessionRepositoryXp
import ru.vo1d.web.persistence.daybook.TimetableRepositoryXp
import ru.vo1d.web.persistence.news.ArticleRepositoryXp
import ru.vo1d.web.persistence.news.CategoryRepositoryXp
import ru.vo1d.web.persistence.qna.PostRepositoryXp
import ru.vo1d.web.persistence.qna.QuestionRepositoryXp

val persistenceModule = DI.Module("persistence") {
    bind<DbContext>() with singleton { H2Context }

    bind<ArticleRepository>() with singleton { ArticleRepositoryXp(instance()) }
    bind<CategoryRepository>() with singleton { CategoryRepositoryXp(instance()) }

    bind<QuestionRepository>() with singleton { QuestionRepositoryXp(instance()) }
    bind<PostRepository>() with singleton { PostRepositoryXp(instance()) }

    bind<ReferenceRepository>() with singleton { ReferenceRepositoryXp(instance()) }
    bind<TimetableRepository>() with singleton { TimetableRepositoryXp(instance()) }
    bind<RegularSessionRepository>() with singleton { RegularSessionRepositoryXp(instance()) }
    bind<DatedSessionRepository>() with singleton { DatedSessionRepositoryXp(instance()) }
}
