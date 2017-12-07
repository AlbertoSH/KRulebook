package com.github.albertosh.krule

class KRuleBuilder<T, R> {

    private var `when`: (FactBook<T>) -> Boolean = { true }
    private val actions = mutableListOf<FactBook<T>.() -> Unit>()
    internal var result : Result<R>? = null
    private var setResult: (FactBook<T>.() -> R)? = null

    fun `when`(`when`: Boolean): KRuleBuilder<T, R> {
        return `when` { `when` }
    }

    fun `when`(`when`: FactBook<T>.() -> Boolean): KRuleBuilder<T, R> {
        this.`when` = `when`
        return this
    }

    fun using(key: T, action: (Any?) -> Unit): KRuleBuilder<T, R> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                p1.using(key) { action(it) }
            }
        }

        this.actions.add(generated)
        return this
    }


    fun result(result: FactBook<T>.() -> R): KRuleBuilder<T, R> {
        this.result = this.result ?: Result()
        val generated = object : (FactBook<T>) -> R {
            override fun invoke(p1: FactBook<T>): R {
                return p1.result()
            }
        }
        this.setResult = generated
        return this
    }

    fun <F> whenFact(whenFact: (F) -> Boolean): KRuleBuilder<T, R> {
        val generated = object : (FactBook<T>) -> Boolean {
            override fun invoke(p1: FactBook<T>): Boolean {
                val value = p1.factValue<F>()
                return whenFact(value)
            }
        }
        this.`when` = generated
        return this
    }

    fun <F> usingFact(action: (F) -> Unit): KRuleBuilder<T, R> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                p1.usingFact<F> { action(it) }
            }
        }

        this.actions.add(generated)
        return this
    }

    fun action(action: FactBook<T>.() -> Unit): KRuleBuilder<T, R> {
        this.actions.add(action)
        return this
    }

    fun build(): KRule<T, R> {
        return KRule(`when`,
                actions.toList(),
                result,
                setResult)
    }

}