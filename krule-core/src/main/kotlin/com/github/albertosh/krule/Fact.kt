package com.github.albertosh.krule

import java.util.*

sealed class Fact<out T, V>(
        internal val key: T
) {
    abstract infix fun `is`(value: Any): Boolean
}


internal class SpecifiedFact<out T, V>(
        key: T,
        private val value: () -> V
) : Fact<T, V>(key) {

    private val calculatedValue: V by lazy {
        value()
    }

    override fun `is`(value: Any): Boolean = Objects.equals(calculatedValue, value)

}


internal class UnspecifiedFact<out T>(
        key: T
) : Fact<T, Nothing>(key) {

    override fun `is`(value: Any) = false

}
