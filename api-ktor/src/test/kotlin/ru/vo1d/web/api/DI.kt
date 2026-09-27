package ru.vo1d.web.api

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import ru.vo1d.web.domain.dao.*
import ru.vo1d.web.api.dao.*

val testDaoModule = DI.Module("test-dao") {
    bind<ArticleDao>() with singleton { ArticleDaoRes() }
    bind<ArticleViewDao>() with singleton { ArticleViewDaoRes() }
    bind<CategoryDao>() with singleton { CategoryDaoRes() }

    bind<AnswerDao>() with singleton { AnswerDaoRes() }
    bind<PostDao>() with singleton { PostDaoRes() }
    bind<PostViewDao>() with singleton { PostViewDaoRes() }
    bind<QuestionDao>() with singleton { QuestionDaoRes() }
}