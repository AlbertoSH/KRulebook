package com.github.albertosh.krule


class FactBook<T> internal constructor(
        private val facts: Map<T, SpecifiedFact<T, *>> = mapOf()
) {

    fun fact(key: T): Fact<T, *> {
        return facts[key] ?: UnspecifiedFact(key)
    }

    @Suppress("UNCHECKED_CAST")
    infix fun Any.`is`(value: Any): Boolean {
        val asT = this as? T

        return if (asT != null) {
            fact(asT).`is`(value)
        } else {
            false
        }
    }

}