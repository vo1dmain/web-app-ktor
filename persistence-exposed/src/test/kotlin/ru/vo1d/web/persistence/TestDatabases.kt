package ru.vo1d.web.persistence

import ru.vo1d.web.persistence.context.DatabaseConfig
import ru.vo1d.web.persistence.context.DbContext
import java.nio.file.Path
import java.util.UUID

/**
 * Fresh in-memory databases, set up like the application's: daybook in MySQL mode, reference data loaded,
 * demo news and groups if [demoData], timetables from [importFile].
 */
internal fun testDatabases(demoData: Boolean = true, importFile: Path? = null): DbContext {
    val name = UUID.randomUUID()
    val config = DatabaseConfig(
        driver = "org.h2.Driver",
        newsUrl = "jdbc:h2:mem:news-$name;DB_CLOSE_DELAY=-1",
        qnaUrl = "jdbc:h2:mem:qna-$name;DB_CLOSE_DELAY=-1",
        daybookUrl = "jdbc:h2:mem:daybook-$name;MODE=MYSQL;DB_CLOSE_DELAY=-1",
        demoData = demoData,
        importFile = importFile
    )
    return DbContext(config).apply { init() }
}
