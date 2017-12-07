package com.github.albertosh.krule

class KRuleBook<T, R>(
        private val rules: List<KRule<T, R>>,
        private val result: Result<R> = Result(null)
) {

    fun execute() = execute(FactBook())

    fun execute(facts: FactBook<T>) : Result<R> {
        rules.forEach { it.execute(facts) }

        return result
    }

}