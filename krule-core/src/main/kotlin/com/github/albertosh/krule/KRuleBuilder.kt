package com.github.albertosh.krule

class KRuleBuilder<T> {

    private var `when`: (FactBook<T>) -> Boolean = { true }
    private val actions = mutableListOf<FactBook<T>.() -> Unit>()

    fun `when`(`when`: Boolean): KRuleBuilder<T> {
        return `when` { `when` }
    }

    fun `when`(`when`: FactBook<T>.() -> Boolean): KRuleBuilder<T> {
        this.`when` = `when`
        return this
    }

    fun using(key: T, action: (Any?) -> Unit): KRuleBuilder<T> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                p1.using(key) { action(it) }
            }
        }

        this.actions.add(generated)
        return this
    }

    fun <R> whenFact(whenFact: (R) -> Boolean): KRuleBuilder<T> {
        val generated = object : (FactBook<T>) -> Boolean {
            override fun invoke(p1: FactBook<T>): Boolean {
                val value = p1.factValue<R>()
                return whenFact(value)
            }
        }
        this.`when` = generated
        return this
    }

    fun <R> usingFact(action: (R) -> Unit): KRuleBuilder<T> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                p1.usingFact<R> { action(it) }
            }
        }

        this.actions.add(generated)
        return this
    }

    fun action(action: FactBook<T>.() -> Unit): KRuleBuilder<T> {
        this.actions.add(action)
        return this
    }

    fun build(): KRule<T> {
        return KRule(`when`, actions.toList())
    }



}