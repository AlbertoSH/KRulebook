package com.github.albertosh.krule.example

import com.github.albertosh.krule.createFactBook
import com.github.albertosh.krule.createKRuleBook


fun main(args: Array<String>) {

    runHelloWorldExample()
    runHelloWorldWithFactsExample()
    runExampleWithResult()
    runExampleStopping()
    runComplexScenario()
}

fun runHelloWorldExample() {
    println("\n\nBasic HelloWorld example")
    val rulebook = createKRuleBook {
        withRule {
            action { print("Hello") }
        }
        withRule {
            action { println(" World") }
        }
    }

    rulebook.execute()
}

fun runHelloWorldWithFactsExample() {
    println("\n\nBasic HelloWorld with facts example")
    val factBook = createFactBook {
        withKey("hello") { "Hello" }
        withKey("world") { " World" }
    }

    val rulebook = createKRuleBook {
        withRule {
            `when` { hasFact("hello") }
            using("hello") { print(it) }
        }
        withRule {
            `when` { hasFact("world") }
            using("world") { println(it) }
        }
    }

    rulebook.execute(factBook)
}

fun runExampleWithResult() {
    println("\n\nExample with result")

    val factBook = createFactBook { withSingleValue(2) }
    val rulebook = createKRuleBook {
        withRule {
            result { factValue() }
        }
    }

    val result = rulebook.execute(factBook)

    println(result)
}

fun runExampleStopping() {
    println("\n\nExample Stopping")

    val rulebook = createKRuleBook {
        withRule(1) {
            action { println("Do it") }
        }
        withRule(2) {
            action { println("And stop!") }
            stop()
        }
        withRule("Don't") {
            action { println("Don't do it") }
        }
    }

    rulebook.execute()
}

