import java.lang.IllegalStateException
import kotlin.math.roundToInt

const val TAVERN_NAME = "Taernyl's Folly"

var playerDragonCoin = 5
var dragonBreathCask = 5.00
const val ONE_PINT = 0.125
val FULL_CASKET = dragonBreathCask / ONE_PINT

fun main() {
    try {
        (1..12).forEach { _ ->
            placeOrder("shandy,Dragon's Breath,5.91")
        }
    } catch (e : NotEnoughGold) {
        println("The bartender explains that you have not enough money, get out of here!!")
    }
}

fun drawFromCase(numberOfPints: Int) {
    val drawFromCasket = numberOfPints * ONE_PINT
    if (drawFromCasket > dragonBreathCask) throw IllegalStateException("casket is empty")
    dragonBreathCask -= drawFromCasket
    val pintsLeft = (dragonBreathCask / ONE_PINT).toInt()
    if (FULL_CASKET.toInt() - pintsLeft == 12) {
        println("Pint left in the casket $pintsLeft")
    }
}

fun performPurchase(price: Double) {
    displayBalance()
    val totalPurse = playerDragonCoin * 1.43
    if (price > totalPurse) throw NotEnoughGold()
    println("Total purse: %.2f".format(totalPurse))
    println("Purchasing item for $price")

    val remainingBalance = totalPurse - price
    println("Remaining balance: ${"%.4f".format(remainingBalance / 1.43)}")

    displayBalance()
    drawFromCase(1)
}

private fun displayBalance() {
    println("Player's purse balance: DragonCoins $playerDragonCoin")
}

private fun placeOrder(menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("Madrigal speaks with $tavernMaster about their order")

    val data = menuData.split(',')
    val (type, name, price) = data
    val message = "Madrigal buys a $name ($type) for $price."
    println(message)
    performPurchase(price.toDouble())
    val phrase = if (name == "Dragon's Breath") {
        "Madrigal exclaims ${toDragonSpeak("Ah. delicious $name!")}"
    } else {
        "Madrigal says: Thanks for the $name."
    }
    println(phrase)
}

private fun toDragonSpeak(phrase: String) =
        phrase.replace(Regex("[aeiou]")) {
            when (it.value) {
                "a" -> "4"
                "e" -> "3"
                "i" -> "1"
                "o" -> "0"
                "u" -> "|_|"
                else -> it.value
            }
        }

class NotEnoughGold() : Exception()