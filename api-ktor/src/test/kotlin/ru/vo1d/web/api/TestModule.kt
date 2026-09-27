package ru.vo1d.web.api

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import ru.vo1d.web.api.fakes.FakeArticleRepository
import ru.vo1d.web.api.fakes.FakeCategoryRepository
import ru.vo1d.web.api.fakes.FakePostRepository
import ru.vo1d.web.api.fakes.FakeQuestionRepository
import ru.vo1d.web.domain.news.ArticleRepository
import ru.vo1d.web.domain.news.CategoryRepository
import ru.vo1d.web.domain.qna.PostRepository
import ru.vo1d.web.domain.qna.QuestionRepository

val testRepositoryModule = DI.Module("test-repositories") {
    bind<ArticleRepository>() with singleton { FakeArticleRepository() }
    bind<CategoryRepository>() with singleton { FakeCategoryRepository() }

    bind<PostRepository>() with singleton { FakePostRepository() }
    bind<QuestionRepository>() with singleton { FakeQuestionRepository() }
}
