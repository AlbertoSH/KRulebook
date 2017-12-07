package com.github.albertosh.krule

class KRule<T, R> internal constructor(
        private val `when`: (FactBook<T>) -> Boolean,
        private val actions: List<FactBook<T>.() -> Unit>,
        private val result: Result<R>? = null,
        private val setResult: (FactBook<T>.() -> R)? = null
) {

    fun execute() {
        execute(FactBook())
    }

    fun execute(facts: FactBook<T>) {
        if (`when`(facts)) {
            actions.forEach { it.invoke(facts) }
            if (result != null && setResult != null)
                result.value = setResult.invoke(facts)
        }
    }

}