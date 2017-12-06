package com.github.albertosh.krule

fun <T> createFactBook(init: FactBookBuilder<T>.() -> Unit): FactBook<T> {
    val builder = FactBookBuilder<T>()
    builder.init()
    return builder.build()
}

class FactBookBuilder<T> {

    private val facts = mutableMapOf<T, SpecifiedFact<T, *>>()

    fun <R> withKey(key: T): (R) -> Unit {
        return { value: R ->
            withKey(key, value)
        }
    }

    fun <R> withKey(key: T, value: R?) {
        withKey(key, { value })
    }

    fun <R> withKey(key: T, value: (() -> R?)) {
        facts.put(key, SpecifiedFact(key, value))
    }

    fun build(): FactBook<T> = FactBook(facts)


}