package ru.vo1d.web.server.errors

import io.ktor.util.internal.*
import kotlinx.coroutines.CopyableThrowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class UnprocessableEntityException(
    private val fieldName: String,
    private val expected: String,
    private val actual: String
) : Exception("Wrong field \'$fieldName\' value. Expected: \'$expected\'. Actual: \'$actual\'."),
    CopyableThrowable<UnprocessableEntityException> {

    override fun createCopy() = UnprocessableEntityException(fieldName, expected, actual).also {
        it.initCauseBridge(this)
    }
}