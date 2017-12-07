package com.github.albertosh.krule

internal class StopExecutionException : RuntimeException("Execution should stop here!")

class KRule<T, R, Id> internal constructor(
        internal val id: Id?,
        private val `when`: (FactBook<T>) -> Boolean,
        private val actions: List<FactBook<T>.() -> Unit>,
        private val result: Result<R>? = null,
        private val calculateResult: (FactBook<T>.() -> R)? = null,
        private val combineWithPreviousResult: ((R?, R) -> R)?
) {

    internal fun execute() {
        execute(FactBook())
    }

    internal fun execute(facts: FactBook<T>) {
        if (`when`(facts)) {
            try {
                actions.forEach { it.invoke(facts) }
            } finally {
                if (result != null && calculateResult != null) {
                    val currentResult = calculateResult.invoke(facts)
                    result.value = if (combineWithPreviousResult != null) {
                        combineWithPreviousResult.invoke(result.value, currentResult)
                    } else {
                        currentResult
                    }
                }
            }
        }
    }

}