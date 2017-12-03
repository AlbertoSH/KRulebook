package com.github.albertosh.krule

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test

internal class KRuleBuilderTest {

    @Test
    internal fun `default rule is always executed`() {
        val action = mock<() -> Unit>()
        val rule = KRuleBuilder<Any>()
                .action(action)
                .build()
        rule.execute()
        verify(action).invoke()
    }

    @Test
    internal fun `execution can be disabled`() {
        val action = mock<() -> Unit>()
        val rule = KRuleBuilder<Any>()
                .`when`(false)
                .action(action)
                .build()
        rule.execute()
        verify(action, times(0)).invoke()
    }

    @Test
    internal fun `all actions are executed`() {
        val action = mock<() -> Unit>()

        val ruleBuilder = KRuleBuilder<Any>()

        (1..10)
                .forEach { ruleBuilder.action(action) }


        val rule = ruleBuilder.build()
        rule.execute()
        verify(action, times(10)).invoke()
    }

}