package com.vermeulen.nyethack

import kotlin.system.exitProcess

fun main() {
    Game.play()
}

object Game {
    private val player = Player("Madrigal")
    private var currentRoom: Room = TownSquare()
    private var worldMap = listOf(
            listOf(currentRoom, Room("Tavern"), Room("Back room")),
            listOf(Room("Long corridor"), Room("Generic Room")))

    init {
        println("Welcome, adventurer")
        player.castFireball()
    }

    fun play() {
        while (true) {
            println(currentRoom.description())
            println(currentRoom.load())

            printPlayerStatus()

            print("> Enter your command: ")
            val command = GameInput(readLine()).processCommand()
            println("Last command: \n$command")
            if (command == "quit") {
                println("by adventure, see you next time")
                return
            }
        }
    }

    private fun move(directionInput: String) =
            try {
                val direction = Direction.valueOf(directionInput.toUpperCase())
                val newPosition = direction.updateCoordinate(player.currentPosition)
                if (!newPosition.isInBounds) {
                    throw IllegalStateException("$direction out of bounds")
                }
                val newRoom = worldMap[newPosition.y][newPosition.x]
                player.currentPosition = newPosition
                currentRoom = newRoom
                "OK you move $direction to the ${newRoom.name}.\n${newRoom.load()}"
            } catch (e: Exception) {
                "Invalid direction: $directionInput"
            }

    private fun fight() = currentRoom.monster?.let {
        while (player.healthPoints > 0 && it.healthPoints >0 ) {
            slay(it)
            Thread.sleep(1000)
        }
        "Combat complete."
    } ?: "There is nothing here to fight"

    private fun slay(monster: Monster) {
        println("${monster.name} did ${monster.attack(player)} damage")
        println("${player.name} did ${player.attack(monster)} damage")

        if (player.healthPoints <= 0) {
            println(">>>>> You have been defeated! Thanks for playing. <<<<<<")
            exitProcess(0)
        }

        if (monster.healthPoints <= 0) {
            println(">>>>> ${monster.name} has been defeated! <<<<<")
            currentRoom.monster = null
        }
    }

    private fun printPlayerStatus() {
        println(
                "(Aura: ${player.auraColor()}) " +
                        "(Blessed: ${if (player.isBlessed) "YES" else "NO"})"
        )
        println("${player.name}${player.formatHealthStatus()}")
    }

    private fun ringBell(): String {
        val defaultMsg = "There are no bells to ring!"
        if (currentRoom is TownSquare) {
            return (currentRoom as? TownSquare)?.ringBell() ?: defaultMsg
        }
        return defaultMsg
    }

    private fun showMap(): String {
        var map = ""
        worldMap.forEachIndexed { y, row ->
            row.forEachIndexed { x, _ ->
                map += if (Coordinate(x, y) == player.currentPosition) " X " else " O "
            }
            map += "\n"
        }
        return map
    }

    private class GameInput(arg: String?) {
        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        private fun commandNotFound() = "I'm not quite sure what you're trying to do!"

        fun processCommand() = when (command.toLowerCase()) {
            "move" -> move(argument)
            "quit" -> "quit"
            "exit" -> "quit"
            "map" -> showMap()
            "ring" -> ringBell()
            "fight" -> fight()
            else -> commandNotFound()
        }
    }
}