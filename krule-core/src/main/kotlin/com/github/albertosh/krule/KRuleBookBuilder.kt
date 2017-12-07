package com.github.albertosh.krule

@JvmName("createGenericKRuleBook")
fun createKRuleBook(init: KRuleBookBuilder<Any, Any>.() -> Unit): KRuleBook<Any, Any> {
    return createKRuleBook<Any, Any>(init)
}

fun <T, R> createKRuleBook(definition: KRuleBookDefinition<T, R>): KRuleBook<T, R> {
    val builder = KRuleBookBuilder<T, R>()
    definition.build(builder)
    return builder.build()
}

fun <T, R> createKRuleBook(init: KRuleBookBuilder<T, R>.() -> Unit): KRuleBook<T, R> {
    val builder = KRuleBookBuilder<T, R>()
    builder.init()
    return builder.build()
}

class KRuleBookBuilder<T, R> {

    private val rules = mutableListOf<KRule<T, R, *>>()
    private val result : Result<R> = Result(null)

    fun build(): KRuleBook<T, R> {
        return KRuleBook(rules, result)
    }

    fun withDefaultResult(result: () -> R): KRuleBookBuilder<T, R> {
        this.result.value = result()
        return this
    }

    @JvmName("withRuleWithGenericId")
    fun withRule(id: Any? = null, init: KRuleBuilder<T, R, *>.() -> Unit): KRuleBookBuilder<T, R> {
        return withRule<Any>(id, init)
    }

    fun <Id> withRule(id: Id? = null, init: KRuleBuilder<T, R, Id>.() -> Unit): KRuleBookBuilder<T, R> {
        val builder = KRuleBuilder<T, R, Id>()
        builder.result = result
        builder.id = id
        builder.init()
        val rule = builder.build()
        rules.add(rule)
        return this
    }

}