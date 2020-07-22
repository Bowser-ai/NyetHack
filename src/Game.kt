const val MAX_INEBRIATION_LEVEL = 50

fun main() {
    val name = "Madrigal"
    val healthPoints = 89
    val isBlessed = true
    val isImmortal = false
    val auraColor = auraColor(isBlessed, healthPoints, isImmortal)
    var inebriationLevel = 0

    val healtStatus = formatHealthStatus(healthPoints, isBlessed)
    printPlayerStatus(auraColor, isBlessed, name, healtStatus)
    val addedInebriationLevel = castFireball(10)
    if (inebriationLevel + addedInebriationLevel <= MAX_INEBRIATION_LEVEL) {
        inebriationLevel += addedInebriationLevel
    } else {
        inebriationLevel = MAX_INEBRIATION_LEVEL
    }
    println(when (inebriationLevel) {
        in 1..10 -> "tipsy"
        in 11..20 -> "sloshed"
        in 21..30 -> "soushed"
        in 31..40 -> "stewed"
        in 41..50 -> "..t0aSt3d"
        else -> ""
    })
}

private fun printPlayerStatus(
    auraColor: String,
    isBlessed: Boolean,
    name: String,
    healtStatus: String
) {
    println(
        "(Aura: $auraColor) " +
                "(Blessed: ${if (isBlessed) "YES" else "NO"})"
    )
    println("$name$healtStatus")
}

private fun auraColor(isBlessed: Boolean, healthPoints: Int, isImmortal: Boolean) =
     if (isBlessed && healthPoints > 50 || isImmortal) "GREEN" else "NONE"

private fun formatHealthStatus(healthPoints: Int, isBlessed: Boolean) =
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

private fun castFireball(numFireballs: Int = 2): Int {
    println("A glass of fireball springs into existence. (x$numFireballs)")
    return 10 * numFireballs
}