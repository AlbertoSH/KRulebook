package com.github.albertosh.krule

import com.nhaarman.mockito_kotlin.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.api.dsl.xon


internal object KRuleSpec : Spek({
    describe("A KRule") {
        on("default setup") {
            it("executes the action") {
                val action = mock<FactBook<Any>.() -> Unit>()

                val rule = KRuleBuilder<Any, Any, Any>()
                        .action(action)
                        .build()
                rule.execute()
                verify(action).invoke(any())
            }
        }

        on("disabled execution") {
            it("doesn't execute the action") {
                val action = mock<FactBook<Any>.() -> Unit>()

                val rule = KRuleBuilder<Any, Any, Any>()
                        .action(action)
                        .`when`(false)
                        .build()
                rule.execute()
                verify(action, never()).invoke(any())
            }
        }
        on("several actions") {
            it("executes all actions") {
                val action = mock<FactBook<Any>.() -> Unit>()

                val builder = KRuleBuilder<Any, Any, Any>()

                (1..10).forEach { builder.action(action) }
                val rule = builder.build()
                rule.execute()
                verify(action, times(10)).invoke(any())
            }
        }
    }
})