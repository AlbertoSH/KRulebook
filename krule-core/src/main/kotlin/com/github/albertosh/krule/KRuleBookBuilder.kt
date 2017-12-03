package com.github.albertosh.krule

fun <T> createKRuleBook(init: KRuleBookBuilder<T>.() -> Unit): KRuleBook<T> {
    val builder = KRuleBookBuilder<T>()
    builder.init()
    return builder.build()
}

class KRuleBookBuilder<T> {

    private val rules = mutableListOf<KRule<T>>()

    fun build(): KRuleBook<T> {
        return KRuleBook(rules)
    }

    fun withRule(init: KRuleBuilder<T>.() -> Unit): KRuleBookBuilder<T> {
        val builder = KRuleBuilder<T>()
        builder.init()
        val rule = builder.build()
        rules.add(rule)
        return this
    }

}