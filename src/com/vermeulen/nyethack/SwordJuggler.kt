package com.vermeulen.nyethack

import java.lang.IllegalStateException

fun main() {
    var swordsJuggling: Int? = null
    val isJugglingProficient = (1..3).shuffled().last() == 3
    if (isJugglingProficient) {
        swordsJuggling = 2
    }
    try {
        proficiencyCheck(swordsJuggling)
        swordsJuggling = swordsJuggling!!.plus(1)
    } catch (e: IllegalStateException) {
        println(e.message)
    }
    println("You juggle $swordsJuggling swords!")
}

private fun proficiencyCheck(swordsJuggling: Int?) {
    checkNotNull(swordsJuggling) {"com.vermeulen.nyethack.Player cannot juggle"}
}

class UnskilledSwordJugglerException() : IllegalStateException("com.vermeulen.nyethack.Player cannot juggle")