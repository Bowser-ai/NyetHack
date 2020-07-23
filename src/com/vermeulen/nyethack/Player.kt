package com.vermeulen.nyethack

import java.io.File

class Player( _name: String,
              private var healthPoints: Int = 100,
              val isBlessed: Boolean,
              private val isImmortal: Boolean) {

    var name = _name
        get() = "${field.capitalize()} of $homeTown"
        private set(value) {
            field = value.trim()
        }

    private val homeTown by lazy { selectHomeTown() }

    init {
        require(healthPoints > 0) {"healthpoints must be greater than zero"}
        require(name.isNotBlank()) {"Player must have a name"}
    }

    constructor(name: String) : this(name,
    healthPoints = 100,
    isBlessed = true,
    isImmortal = false) {
        if (name.toLowerCase() == "kar") healthPoints = 40
    }

    fun castFireball(numFireballs: Int = 2) {
        println("A glass of fireball springs into existence. (x$numFireballs)")
    }

    fun auraColor() =
            if (isBlessed && healthPoints > 50 || isImmortal) "GREEN" else "NONE"

    fun formatHealthStatus() =
            when (healthPoints) {
                100 -> " is in excellent condition"
                in 90..99 -> " has a few scratches"
                in 75..89 -> if (isBlessed) {
                    " has some minor wounds but is healing quite quickly"
                } else {
                    " has some minor wounds"
                }
                in 15..74 -> " looks pretty hurt"
                else -> " is in awful condition"
            }

    private fun selectHomeTown() = File("data/towns.txt")
            .readText()
            .split("\n")
            .shuffled()
            .first()
}