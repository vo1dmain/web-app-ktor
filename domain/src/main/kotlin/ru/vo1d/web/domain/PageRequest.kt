package ru.vo1d.web.domain

/**
 * One page of a list query; [number] starts at 1.
 */
data class PageRequest(val number: Int = 1, val size: Int = DEFAULT_SIZE) {
    init {
        require(number >= 1) { "Page number must be positive: $number" }
        require(size >= 1) { "Page size must be positive: $size" }
    }

    val offset: Long get() = (number - 1).toLong() * size

    companion object {
        const val DEFAULT_SIZE = 10
    }
}
