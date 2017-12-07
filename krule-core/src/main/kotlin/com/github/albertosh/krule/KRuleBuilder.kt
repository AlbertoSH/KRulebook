package com.github.albertosh.krule

class KRuleBuilder<T, R, Id> {

    internal var id: Id? = null
    private var `when`: (FactBook<T>) -> Boolean = { true }
    private val actions = mutableListOf<FactBook<T>.() -> Unit>()
    internal var result : Result<R>? = null
    private var setResult: (FactBook<T>.() -> R)? = null
    private var combineResult: ((R?, R) -> R)? = null

    fun withDefaultResult(result: () -> R): KRuleBuilder<T, R, Id> {
        this.result = this.result ?: Result()
        this.result!!.value = result()
        return this
    }

    fun `when`(`when`: Boolean): KRuleBuilder<T, R, Id> {
        return `when` { `when` }
    }

    fun `when`(`when`: FactBook<T>.() -> Boolean): KRuleBuilder<T, R, Id> {
        this.`when` = `when`
        return this
    }

    fun using(key: T, action: (Any?) -> Unit): KRuleBuilder<T, R, Id> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                p1.using(key) { action(it) }
            }
        }

        this.actions.add(generated)
        return this
    }


    fun result(result: FactBook<T>.() -> R): KRuleBuilder<T, R, Id> {
        this.result = this.result ?: Result()
        val generated = object : (FactBook<T>) -> R {
            override fun invoke(p1: FactBook<T>): R {
                return p1.result()
            }
        }
        this.setResult = generated
        return this
    }

    fun combineResult(combine: ((R?, R) -> R)): KRuleBuilder<T, R, Id> {
        this.combineResult = combine
        return this
    }

    fun <F> whenFact(key: T, whenFact: (F) -> Boolean): KRuleBuilder<T, R, Id> {
        val generated = object : (FactBook<T>) -> Boolean {
            override fun invoke(p1: FactBook<T>): Boolean {
                val value = p1.fact(key).value as F
                return whenFact(value)
            }
        }
        this.`when` = generated
        return this
    }

    fun <F> whenSingleFact(whenFact: F.() -> Boolean): KRuleBuilder<T, R, Id> {
        val generated = object : (FactBook<T>) -> Boolean {
            override fun invoke(p1: FactBook<T>): Boolean {
                val value = p1.factValue<F>()
                return whenFact(value)
            }
        }
        this.`when` = generated
        return this
    }

    fun <F> usingFact(action: (F) -> Unit): KRuleBuilder<T, R, Id> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                p1.usingFact<F> { action(it) }
            }
        }

        this.actions.add(generated)
        return this
    }

    fun action(action: FactBook<T>.() -> Unit): KRuleBuilder<T, R, Id> {
        this.actions.add(action)
        return this
    }

    fun stop(): KRuleBuilder<T, R, Id> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                throw StopExecutionException()
            }
        }
        this.actions.add(generated)
        return this
    }

    fun build(): KRule<T, R, Id> {
        return KRule(
                id,
                `when`,
                actions.toList(),
                result,
                setResult,
                combineResult)
    }



}