package com.github.albertosh.krule

class KRuleBuilder<T> {

    private var `when`: (FactBook<T>) -> Boolean = { true }
    private val actions = mutableListOf<() -> Unit>()

    fun `when`(`when`: Boolean): KRuleBuilder<T> {
        this.`when` = { `when` }
        return this
    }

    fun `when`(`when`: FactBook<T>.() -> Boolean): KRuleBuilder<T> {
        this.`when` = `when`
        return this
    }

    fun action(action: () -> Unit): KRuleBuilder<T> {
        this.actions.add(action)
        return this
    }

    fun build(): KRule<T> {
        return KRule(`when`, actions.toList())
    }

}