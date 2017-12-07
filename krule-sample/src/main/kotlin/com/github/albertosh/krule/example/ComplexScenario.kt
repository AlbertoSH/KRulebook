package com.github.albertosh.krule.example

import com.github.albertosh.krule.KRuleBookBuilder
import com.github.albertosh.krule.KRuleBookDefinition
import com.github.albertosh.krule.createFactBook
import com.github.albertosh.krule.createKRuleBook

data class Applicant(
        var creditScore: Int,
        var cashOnHand: Double,
        var firstTimeHomeBuyer: Boolean
)

class HomeLoanRateRuleBook : KRuleBookDefinition<Any, Double>() {

    override fun defineRules(): KRuleBookBuilder<Any, Double>.() -> Unit = {
        withDefaultResult { 4.5 }
        withRule {
            whenSingleFact<Applicant> { creditScore < 600 }
            result { 4.toDouble() }
            combineResult { old, multiplier -> old!! * multiplier }
            stop()
        }
        withRule {
            whenSingleFact<Applicant> { creditScore < 700 }
            result { 1.toDouble() }
            combineResult { old, increase -> old!! + increase }
        }
        withRule {
            whenSingleFact<Applicant> {
                (creditScore >= 700) and (cashOnHand > 25_000)
            }
            result { 0.25 }
            combineResult { old, decrease -> old!! - decrease }
        }
        withRule {
            whenSingleFact<Applicant> { firstTimeHomeBuyer }
            result { 0.8 }
            combineResult { old, multiplier -> old!! * multiplier }
        }
    }
}

fun runComplexScenario() {
    println("\n\nComplex Scenario Example")

    val factBook = createFactBook {
        withSingleValue(Applicant(550, 20_000.0, true))
    }

    val rulebook = createKRuleBook(HomeLoanRateRuleBook())

    val result = rulebook.execute(factBook)

    println(result)

}