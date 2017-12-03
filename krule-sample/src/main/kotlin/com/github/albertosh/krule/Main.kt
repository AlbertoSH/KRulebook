package com.github.albertosh.krule

enum class Key {
    KEY_1, KEY_2, KEY_3
}

fun main(args: Array<String>) {

    val facts = createFactBook<Key> {
        withKey(Key.KEY_1) { 1 }
        withKey(Key.KEY_2, 2)
        withKey<Int>(Key.KEY_3)(3)
    }

    val rulebook = createKRuleBook<Key> {
        withRule {
            `when` {
                fact(Key.KEY_1) `is` 1
            }
            action {
                println("Do")
            }
        }
        withRule {
            `when` {
                Key.KEY_2 `is` 3
            }
            action {
                println("Don't")
            }
        }
    }

    rulebook.execute(facts)

}



