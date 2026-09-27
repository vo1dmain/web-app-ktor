package ru.vo1d.web.exposed

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import ru.vo1d.web.data.dao.*
import ru.vo1d.web.exposed.context.DbContext
import ru.vo1d.web.exposed.context.H2Context
import ru.vo1d.web.exposed.dao.daybook.group.*
import ru.vo1d.web.exposed.dao.daybook.timetable.SessionTypeDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.TimetableDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.dated.DatedSessionDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.dated.TimetableDatedSessionDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.regular.RegularSessionDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.regular.TimetableRegularSessionDaoXp
import ru.vo1d.web.exposed.dao.news.ArticleDaoXp
import ru.vo1d.web.exposed.dao.news.ArticleViewDaoXp
import ru.vo1d.web.exposed.dao.news.CategoryDaoXp
import ru.vo1d.web.exposed.dao.qna.AnswerDaoXp
import ru.vo1d.web.exposed.dao.qna.PostDaoXp
import ru.vo1d.web.exposed.dao.qna.PostViewDaoXp
import ru.vo1d.web.exposed.dao.qna.QuestionDaoXp

val exposedDaoModule = DI.Module("exposed-dao") {
    bind<DbContext>() with singleton { H2Context }

    bind<ArticleDao>() with singleton { ArticleDaoXp(instance()) }
    bind<ArticleViewDao>() with singleton { ArticleViewDaoXp(instance()) }
    bind<CategoryDao>() with singleton { CategoryDaoXp(instance()) }

    bind<AnswerDao>() with singleton { AnswerDaoXp(instance()) }
    bind<PostDao>() with singleton { PostDaoXp(instance()) }
    bind<PostViewDao>() with singleton { PostViewDaoXp(instance()) }
    bind<QuestionDao>() with singleton { QuestionDaoXp(instance()) }

    bind<EduFormDao>() with singleton { EduFormDaoXp(instance()) }
    bind<GradDegreeDao>() with singleton { GradDegreeDaoXp(instance()) }
    bind<GradLevelDao>() with singleton { GradLevelDaoXp(instance()) }
    bind<GroupDao>() with singleton { GroupDaoXp(instance()) }

    bind<DatedSessionDao>() with singleton { DatedSessionDaoXp(instance()) }
    bind<RegularSessionDao>() with singleton { RegularSessionDaoXp(instance()) }
    bind<SessionTypeDao>() with singleton { SessionTypeDaoXp(instance()) }

    bind<TableTypeDao>() with singleton { TableTypeDaoXp(instance()) }
    bind<TimetableDao>() with singleton { TimetableDaoXp(instance()) }
    bind<TimetableDatedSessionDao>() with singleton { TimetableDatedSessionDaoXp(instance()) }
    bind<TimetableRegularSessionDao>() with singleton { TimetableRegularSessionDaoXp(instance()) }
}