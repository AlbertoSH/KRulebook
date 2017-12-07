package com.github.albertosh.krule

abstract class KRuleBookDefinition<T, R>{

    abstract fun defineRules() : KRuleBookBuilder<T, R>.() -> Unit

    internal fun build(builder: KRuleBookBuilder<T, R>) {
        val init = defineRules()
        builder.init()
    }

}