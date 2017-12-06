package com.github.albertosh.krule

abstract class KRuleBookDefinition<T>{

    abstract fun defineRules() : KRuleBookBuilder<T>.() -> Unit

    internal fun build(builder: KRuleBookBuilder<T>) {
        val init = defineRules()
        builder.init()
    }

}