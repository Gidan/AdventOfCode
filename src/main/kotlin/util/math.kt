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

data class Vec2D(val x: Int, val y: Int) {
    operator fun plus(increment: Vec2D): Vec2D {
        return Vec2D(x + increment.x, y + increment.y)
    }
}

fun Int.pow(exp: Int): Int {
    val base = this
    var exponent = exp
    var result = 1

    while (exponent != 0) {
        result *= base
        --exponent
    }
    return result
}



