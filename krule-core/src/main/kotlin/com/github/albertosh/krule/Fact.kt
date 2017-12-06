package com.github.albertosh.krule

import java.util.*

sealed class Fact<out T, out V>(
        internal val key: T
) {
    abstract infix fun `is`(value: Any): Boolean
    abstract val value: V?

    operator fun invoke() = (value)
    operator fun invoke(action: (V?) -> Unit) = action(value)
}

internal class SpecifiedFact<out T, out V>(
        key: T,
        private val specifiedValue: () -> V?
) : Fact<T, V>(key) {

    override val value: V?
        get() = calculatedValue

    private val calculatedValue: V? by lazy {
        specifiedValue()
    }

    override fun `is`(value: Any): Boolean = Objects.equals(calculatedValue, value)

}


internal class UnspecifiedFact<out T>(
        key: T
) : Fact<T, Nothing>(key) {

    override val value: Nothing?
        get() = null

    override fun `is`(value: Any) = false

}
