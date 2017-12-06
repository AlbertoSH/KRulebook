package com.github.albertosh.krule


fun main(args: Array<String>) {

    runHelloWorldExample()
    runHelloWorldWithFactsExample()
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
            using("world") { print(it) }
        }
    }

    rulebook.execute(factBook)
}
