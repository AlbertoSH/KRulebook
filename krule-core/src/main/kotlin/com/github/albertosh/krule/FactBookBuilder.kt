package com.github.albertosh.krule

@JvmName("createGenericFactBook")
fun createFactBook(init: FactBookBuilder<Any>.() -> Unit): FactBook<Any> {
    return createFactBook<Any>(init)
}

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

    fun <R> withKey(key: T, value: R?): FactBookBuilder<T> {
        return withKey(key, { value })
    }

    fun <R> withKey(key: T, value: (() -> R?)): FactBookBuilder<T> {
        facts.put(key, SpecifiedFact(key, value))
        return this
    }

    fun <R> withSingleValue(value: R?): FactBookBuilder<T> {
        return withKey(FactBook.singleKey as T, { value })
    }

    fun build(): FactBook<T> = FactBook(facts)


}