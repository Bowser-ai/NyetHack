package com.vermeulen.nyethack

import kotlin.random.Random

interface Fightable {
    var healthPoints: Int
    val diceCount: Int
    val diceSides: Int
    val damageRoll: Int
    get() = (0 until diceCount).map { _ ->
        Random.nextInt(diceSides + 1)
    }.sum()

    fun attack(oppenent: Fightable): Int
}

abstract class Monster(val name: String,
                       val description: String,
                       override var healthPoints: Int): Fightable {
    override fun attack(oppenent: Fightable): Int {
        val damageDealt = damageRoll
        oppenent.healthPoints -= damageDealt
        return damageDealt
    }
}

class Goblin(name: String = "Goblin",
             description: String = "A nasty looking goblin",
             healthPoints: Int = 30): Monster(name, description, healthPoints) {

    override val diceCount = 2
    override val diceSides = 8
}