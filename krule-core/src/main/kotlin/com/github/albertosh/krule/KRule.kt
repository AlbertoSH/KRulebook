package com.github.albertosh.krule

class KRule<T>(
        private val `when`: (FactBook<T>) -> Boolean,
        private val actions: List<() -> Unit>
) {

    fun execute() {
        execute(FactBook())
    }

    fun execute(facts: FactBook<T>) {
        if (`when`(facts))
            actions.forEach { it.invoke() }
    }

}