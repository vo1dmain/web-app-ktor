package ru.vo1d.web.persistence.daybook

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.jdbc.batchInsert
import ru.vo1d.web.persistence.readSeed

@Serializable
private class GroupSeed(val code: String, val levelId: String, val degreeId: String?, val formId: String)

/**
 * Inserts the demo groups from `data/daybook/demo`, so timetables can be created until real groups are loaded;
 * groups that already exist are left as they are. Needs the reference data in place.
 */
internal fun seedDemoGroups() {
    val groups = readSeed("daybook/demo/groups.json", GroupSeed.serializer())
    Groups.batchInsert(groups, ignore = true, shouldReturnGeneratedValues = false) {
        this[Groups.id] = it.code
        this[Groups.levelId] = it.levelId
        this[Groups.degreeId] = it.degreeId
        this[Groups.formId] = it.formId
    }
}
