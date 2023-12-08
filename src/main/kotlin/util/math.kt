package util

fun findLCM(a: ULong, b: ULong): ULong {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0uL && lcm % b == 0uL) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

