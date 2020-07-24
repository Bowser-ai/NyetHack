package com.vermeulen.nyethack

import java.lang.IllegalStateException

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

            printPlayerStatus(player)

            print("> Enter your command: ")
            val command = GameInput(readLine()).processCommand()
            println("Last command: $command")
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

    private fun printPlayerStatus(player: Player) {
        println(
                "(Aura: ${player.auraColor()}) " +
                        "(Blessed: ${if (player.isBlessed) "YES" else "NO"})"
        )
        println("${player.name}${player.formatHealthStatus()}")
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
            else -> commandNotFound()
        }
    }
}