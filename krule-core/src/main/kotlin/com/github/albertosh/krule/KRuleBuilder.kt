package com.github.albertosh.krule

class KRuleBuilder<T> {

    private var `when`: (FactBook<T>) -> Boolean = { true }
    private val actions = mutableListOf<FactBook<T>.() -> Unit>()

    fun `when`(`when`: Boolean): KRuleBuilder<T> {
        this.`when` = { `when` }
        return this
    }

    fun `when`(`when`: FactBook<T>.() -> Boolean): KRuleBuilder<T> {
        this.`when` = `when`
        return this
    }

    fun using(key: T, action: (Any?) -> Unit): KRuleBuilder<T> {
        val generated = object : (FactBook<T>) -> Unit {
            override fun invoke(p1: FactBook<T>) {
                p1.using(key) { action(it as Any?) }
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