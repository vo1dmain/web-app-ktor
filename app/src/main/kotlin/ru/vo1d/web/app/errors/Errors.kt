package ru.vo1d.web.app.errors

import io.ktor.util.internal.*
import kotlinx.coroutines.CopyableThrowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class UnprocessableEntityException(message: String? = null) : Exception(message),
    CopyableThrowable<UnprocessableEntityException> {

    constructor(fieldName: String, expected: String, actual: String) :
            this("Wrong field \'$fieldName\' value. Expected: \'$expected\'; actual: \'$actual\'.")

    override fun createCopy() = UnprocessableEntityException(message).also {
        it.initCauseBridge(this)
    }
}