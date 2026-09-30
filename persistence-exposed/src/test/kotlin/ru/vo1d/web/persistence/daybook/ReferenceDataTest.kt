package ru.vo1d.web.persistence.daybook

import kotlinx.coroutines.runBlocking
import ru.vo1d.web.persistence.testDatabases
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReferenceDataTest {
    @Test
    fun referenceDataIsLoaded() = runBlocking<Unit> {
        val reference = ReferenceRepositoryXp(testDatabases(demoData = false))

        assertEquals(listOf("mid", "high"), reference.levels().map { it.id }.sortedDescending())
        assertEquals(3, reference.degrees().size)
        assertEquals(4, reference.forms().size)
        assertEquals(setOf("tt", "att"), reference.tableTypes().map { it.id }.toSet())
        assertEquals(12, reference.sessionTypes().size)
        assertTrue(reference.groups().isEmpty())
    }

    @Test
    fun loadingAgainAddsNothing() = runBlocking<Unit> {
        val databases = testDatabases()
        databases.init()
        val reference = ReferenceRepositoryXp(databases)

        assertEquals(12, reference.sessionTypes().size)
        assertEquals(4, reference.forms().size)
        assertEquals(3, reference.groups().size)
    }

    @Test
    fun sessionTypesKeepFileOrder() = runBlocking<Unit> {
        val types = ReferenceRepositoryXp(testDatabases()).sessionTypes()

        assertEquals("Лекция", types.first().title)
        assertEquals(types.sortedBy { it.id }, types)
    }
}
