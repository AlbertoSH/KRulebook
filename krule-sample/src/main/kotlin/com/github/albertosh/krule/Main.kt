package com.github.albertosh.krule


fun main(args: Array<String>) {

    val facts = createFactBook<String> {
        withKey("hello", "Hello ")
        withKey("world", " World")
    }

    val rulebook = createKRuleBook<String> {
        withRule {
            `when` {
                hasFact("hello")
            }
            using("hello") {
                println(it)
            }
        }
        withRule {
            `when` {
                hasFact("world")
            }
            using("world") {
                println(it)
            }
        }
    }

    rulebook.execute(facts)

}



