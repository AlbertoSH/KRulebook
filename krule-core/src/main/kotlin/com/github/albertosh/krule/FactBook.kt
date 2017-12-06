package com.github.albertosh.krule


class FactBook<T> internal constructor(
        private val facts: Map<T, SpecifiedFact<T, *>> = mapOf()
) {

    fun fact(key: T): Fact<T, *> {
        return facts[key] ?: UnspecifiedFact(key)
    }

    fun hasFact(key: T): Boolean = hasFact { key }

    fun hasFact(key: () -> T): Boolean = facts[key()] != null

    fun using(key: T, action: (Any?) -> Unit) {
        val value = facts[key]
        value?.let {
            action(it.value())
        }
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