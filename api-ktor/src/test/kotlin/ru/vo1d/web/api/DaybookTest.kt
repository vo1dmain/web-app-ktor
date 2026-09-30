package ru.vo1d.web.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import ru.vo1d.web.api.dto.daybook.*
import ru.vo1d.web.api.fakes.*
import ru.vo1d.web.api.routing.daybookRouting
import ru.vo1d.web.domain.daybook.DaybookService
import ru.vo1d.web.domain.daybook.TimetableService
import ru.vo1d.web.domain.daybook.timetable.TimetableFormat
import ru.vo1d.web.domain.daybook.timetable.week.WeekOption
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.Instant

class DaybookTest {
    private val regularTimetable = TimetableRequest("ИВТ-21", "tt", TimetableFormat.Regular)

    private val mondayLecture = RegularSessionRequest(
        subject = "Матан",
        instructor = "Иванов",
        place = "101",
        typeId = 1,
        dayOfWeek = DayOfWeek.MONDAY,
        time = LocalTime(8, 0),
        weekOption = WeekOption.Both
    )

    @Test
    fun metaUsesTheDaybookZoneAndClock() = daybookTest { client ->
        val meta = client.get("/daybook/meta").body<MetaResponse>()

        assertEquals("Europe/Moscow", meta.timeZone)
        // Monday 2026-09-07 00:30 in Moscow is the second week
        assertEquals(2, meta.week.number)
        assertEquals(listOf("ИВТ-21"), meta.groups.map { it.code })
        assertEquals(2, client.get("/daybook/meta/session-types").body<List<SessionTypeResponse>>().size)
    }

    @Test
    fun timetableIsCreatedOnce() = daybookTest { client ->
        val created = client.post("/daybook/timetables") { json(regularTimetable) }
        assertEquals(HttpStatusCode.Created, created.status)
        val id = created.body<Int>()

        assertEquals(TimetableFormat.Regular, client.get("/daybook/timetables/$id").body<TimetableResponse>().format)
        assertEquals(HttpStatusCode.Conflict, client.post("/daybook/timetables") { json(regularTimetable) }.status)
        assertEquals(
            HttpStatusCode.UnprocessableEntity,
            client.post("/daybook/timetables") { json(regularTimetable.copy(groupCode = "НЕТ-00")) }.status
        )
        assertEquals(1, client.get("/daybook/timetables?group=ИВТ-21").body<List<TimetableResponse>>().size)
    }

    @Test
    fun sessionIsAttachedToItsTimetable() = daybookTest { client ->
        val timetable = client.post("/daybook/timetables") { json(regularTimetable) }.body<Int>()
        val session = client.post("/daybook/sessions/regular") { json(mondayLecture) }.body<Int>()

        val attached = client.post("/daybook/timetables/$timetable/sessions") {
            json(TimetableSessionDto(timetable, session))
        }
        assertEquals(HttpStatusCode.Created, attached.status)

        val listed = client.get("/daybook/sessions/regular?timetable=$timetable").body<List<RegularSessionResponse>>()
        assertEquals(listOf(session), listed.map { it.id })
        assertEquals(45, listed.single().duration.inWholeMinutes.toInt())
    }

    @Test
    fun attachingChecksTimetableAndSession() = daybookTest { client ->
        val timetable = client.post("/daybook/timetables") { json(regularTimetable) }.body<Int>()

        // the body must name the timetable from the path
        val mismatch = client.post("/daybook/timetables/$timetable/sessions") {
            json(TimetableSessionDto(timetable + 1, 1))
        }
        assertEquals(HttpStatusCode.UnprocessableEntity, mismatch.status)

        val noTimetable = client.post("/daybook/timetables/99/sessions") { json(TimetableSessionDto(99, 1)) }
        assertEquals(HttpStatusCode.NotFound, noTimetable.status)

        val noSession = client.post("/daybook/timetables/$timetable/sessions") {
            json(TimetableSessionDto(timetable, 99))
        }
        assertEquals(HttpStatusCode.UnprocessableEntity, noSession.status)
    }

    @Test
    fun sessionsAreValidated() = daybookTest { client ->
        val unknownType = client.post("/daybook/sessions/regular") { json(mondayLecture.copy(typeId = 99)) }
        assertEquals(HttpStatusCode.UnprocessableEntity, unknownType.status)

        val noFields = client.post("/daybook/sessions/regular") { json(mapOf("subject" to "Матан")) }
        assertEquals(HttpStatusCode.BadRequest, noFields.status)

        assertEquals(HttpStatusCode.BadRequest, client.get("/daybook/sessions/regular?page=0").status)
    }

    @Test
    fun datedSessionsFilterByTime() = daybookTest { client ->
        val exam = DatedSessionRequest("Экзамен", "Иванов", "101", 1, dateTime = Instant.parse("2026-01-20T07:00:00Z"))
        client.post("/daybook/sessions/dated") { json(exam) }
        client.post("/daybook/sessions/dated") { json(exam.copy(dateTime = Instant.parse("2026-01-12T07:00:00Z"))) }

        val all = client.get("/daybook/sessions/dated").body<List<DatedSessionResponse>>()
        assertEquals(listOf(2, 1), all.map { it.id })

        val one = client.get("/daybook/sessions/dated?dateTime=2026-01-20T07:00:00Z").body<List<DatedSessionResponse>>()
        assertEquals(listOf(1), one.map { it.id })
    }

    private fun HttpRequestBuilder.json(body: Any) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }

    private fun daybookTest(block: suspend ApplicationTestBuilder.(HttpClient) -> Unit) = testApplication {
        application {
            daybookModule()
        }
        block(jsonClient())
    }

    private fun Application.daybookModule() {
        testPlugins()

        val zone = TimeZone.of("Europe/Moscow")
        val clock = object : Clock {
            override fun now() = Instant.parse("2026-09-06T21:30:00Z")
        }

        val reference = FakeReferenceRepository()
        val timetables = FakeTimetableRepository(reference)
        val regular = FakeRegularSessionRepository(reference)
        val dated = FakeDatedSessionRepository(reference)

        routing {
            daybookRouting(
                DaybookService(reference, zone, clock),
                TimetableService(timetables, regular, dated),
                reference,
                timetables,
                regular,
                dated
            )
        }
    }
}
