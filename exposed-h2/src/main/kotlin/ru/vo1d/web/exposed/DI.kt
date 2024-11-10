package ru.vo1d.web.exposed

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import ru.vo1d.web.data.dao.*
import ru.vo1d.web.exposed.dao.daybook.group.*
import ru.vo1d.web.exposed.dao.daybook.timetable.SessionTypeDaoXp
import ru.vo1d.web.exposed.dao.daybook.timetable.StartTimeDaoXp
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

val exposedDaoModule =  DI.Module("exposed-dao") {
    bind<ArticleDao>() with singleton { ArticleDaoXp() }
    bind<ArticleViewDao>() with singleton { ArticleViewDaoXp() }
    bind<CategoryDao>() with singleton { CategoryDaoXp() }

    bind<AnswerDao>() with singleton { AnswerDaoXp() }
    bind<PostDao>() with singleton { PostDaoXp() }
    bind<PostViewDao>() with singleton { PostViewDaoXp() }
    bind<QuestionDao>() with singleton { QuestionDaoXp() }

    bind<EduFormDao>() with singleton { EduFormDaoXp() }
    bind<GradDegreeDao>() with singleton { GradDegreeDaoXp() }
    bind<GradLevelDao>() with singleton { GradLevelDaoXp() }
    bind<GroupDao>() with singleton { GroupDaoXp() }

    bind<DatedSessionDao>() with singleton { DatedSessionDaoXp() }
    bind<RegularSessionDao>() with singleton { RegularSessionDaoXp() }
    bind<SessionTypeDao>() with singleton { SessionTypeDaoXp() }
    bind<StartTimeDao>() with singleton { StartTimeDaoXp() }

    bind<TableTypeDao>() with singleton { TableTypeDaoXp() }
    bind<TimetableDao>() with singleton { TimetableDaoXp() }
    bind<TimetableDatedSessionDao>() with singleton { TimetableDatedSessionDaoXp() }
    bind<TimetableRegularSessionDao>() with singleton { TimetableRegularSessionDaoXp() }
}