package ru.vo1d.web.exposed.utils.time

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.IDateColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.vendors.OracleDialect
import org.jetbrains.exposed.sql.vendors.SQLiteDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private const val MILLIS_IN_SECOND = 1000

private val DEFAULT_TIME_ZONE by lazy {
    TimeZone.currentSystemDefault()
}

private val DEFAULT_TIME_STRING_FORMATTER by lazy {
    DateTimeFormatter.ISO_LOCAL_TIME.withLocale(Locale.ROOT).withZone(ZoneId.systemDefault())
}

private val SQLITE_AND_ORACLE_TIME_STRING_FORMATTER by lazy {
    DateTimeFormatter.ofPattern(
        "HH:mm:ss.SSS",
        Locale.ROOT
    ).withZone(ZoneId.systemDefault())
}

class KotlinLocalTimeColumnType : ColumnType(), IDateColumnType {
    override val hasTimePart: Boolean = true

    override fun sqlType(): String = currentDialect.dataTypeProvider.timeType()

    override fun nonNullValueToString(value: Any): String {
        val instant = when (value) {
            is String -> return value
            is LocalTime -> timeToInstant(value)
            is java.sql.Time -> Instant.fromEpochMilliseconds(value.time)
            is java.sql.Timestamp -> Instant.fromEpochSeconds(value.time / MILLIS_IN_SECOND, value.nanos.toLong())
            else -> error("Unexpected value: $value of ${value::class.qualifiedName}")
        }.toJavaInstant()

        return when (currentDialect) {
            is SQLiteDialect, is OracleDialect -> "'${SQLITE_AND_ORACLE_TIME_STRING_FORMATTER.format(instant)}'"
            else -> "'${DEFAULT_TIME_STRING_FORMATTER.format(instant)}'"
        }
    }

    override fun valueFromDB(value: Any): LocalTime = when (value) {
        is LocalTime -> value
        is java.sql.Time -> value.toLocalTime().toKotlinLocalTime()
        is java.sql.Timestamp -> value.toLocalDateTime().toKotlinLocalDateTime().time
        is Int -> longToLocalTime(value.toLong())
        is Long -> longToLocalTime(value)
        is String -> when (currentDialect) {
            is SQLiteDialect, is OracleDialect -> SQLITE_AND_ORACLE_TIME_STRING_FORMATTER.parse(
                value,
                java.time.LocalTime::from
            ).toKotlinLocalTime()
            else -> LocalTime.parse(value)
        }
        else -> LocalTime.parse(value.toString())
    }

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is LocalTime -> java.sql.Time.from(timeToInstant(value).toJavaInstant())
        else -> value
    }

    private fun longToLocalTime(millis: Long) =
        Instant.fromEpochMilliseconds(millis).toLocalDateTime(DEFAULT_TIME_ZONE).time

    private fun timeToInstant(time: LocalTime) =
        time.atDate(Clock.System.now().toLocalDateTime(DEFAULT_TIME_ZONE).date).toInstant(DEFAULT_TIME_ZONE)

    companion object {
        internal val INSTANCE = KotlinLocalTimeColumnType()
    }
}

fun Table.time(name: String): Column<LocalTime> = registerColumn(name, KotlinLocalTimeColumnType())