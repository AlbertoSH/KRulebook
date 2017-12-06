package com.github.albertosh.krule


class FactBook<T> internal constructor(
        private val facts: Map<T, SpecifiedFact<T, *>> = mapOf()
) {

    @Suppress("UNCHECKED_CAST")
    fun fact(): Fact<T, *> =
            when {
                facts.size > 1 -> throw IllegalStateException("You must specify the key for the fact")
                facts.size == 1 -> facts.values.elementAt(0)
                else -> UnspecifiedFact(Any() as T)
            }

    fun <R> factValue(): R {
        val value = fact().value
        if (value == null)
            throw NullPointerException("Fact has not been specified")
        else
            return value as R
    }


    fun fact(key: T): Fact<T, *> = facts[key] ?: UnspecifiedFact(key)

    operator fun get(key: T) = fact(key)

    fun hasFact(key: T): Boolean = hasFact { key }

    fun hasFact(key: () -> T): Boolean = facts[key()] != null

    fun using(key: T, action: (Any?) -> Unit) = facts[key]?.let { action(it.value) }

    fun <R> usingFact(action: (R) -> Unit) = factValue<R>().let { action(it) }


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