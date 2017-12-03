package com.github.albertosh.krule

class KRuleBook<T>(
        private val rules: List<KRule<T>>
) {

    fun execute() {
        execute(FactBook())
    }

    fun execute(facts: FactBook<T>) {
        rules.forEach { it.execute(facts) }
    }

}