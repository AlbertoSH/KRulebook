package com.github.albertosh.krule

@JvmName("createGenericKRuleBook")
fun createKRuleBook(init: KRuleBookBuilder<Any>.() -> Unit): KRuleBook<Any> {
    return createKRuleBook<Any>(init)
}

fun <T> createKRuleBook(definition: KRuleBookDefinition<T>): KRuleBook<T> {
    val builder = KRuleBookBuilder<T>()
    definition.build(builder)
    return builder.build()
}

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