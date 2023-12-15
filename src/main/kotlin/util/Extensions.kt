package util

fun String.toULongList(): List<ULong> = this.split(" ").map { it.toULong() }

fun List<ULongRange>.printInLineSorted() {
    if (LOG) {
        val sorted = this.sortedBy { range -> range.first }
        for ((index, uLongRange) in sorted.withIndex()) {
            if (index > 0) {
                val distance = sorted[index].first - sorted[index - 1].last
                if (distance > 1uL) {
                    print("--]$distance[--")
                } else {
                    print(";")
                }
            }
            print("${Colors.YELLOW}$uLongRange${Colors.RESET}")
        }
    }
}

fun List<ULongRange>.printSorted() {
    if (LOG) {
        val sorted = this.sortedBy { range -> range.first }
        for ((index, uLongRange) in sorted.withIndex()) {
            if (index > 0) {
                val distance = sorted[index].first - sorted[index - 1].last
                if (distance > 1uL) {
                    println("$distance")
                }
            }
            println("${Colors.YELLOW}$uLongRange${Colors.RESET}")
        }
    }
}

fun ULongRange.size(): ULong {
    return this.last - first + 1uL
}

fun List<ULong>.lcm(): ULong {
    var result = this[0]
    for (i in 1 until this.size) {
        result = findLCM(result, this[i])
    }
    return result
}

fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()

//fun String.permute(result: String = ""): List<String> =
//    if (isEmpty()) listOf(result) else flatMapIndexed { i, c -> removeRange(i, i + 1).permute(result + c) }

fun String.clone(times : Int, separator : String) : String {
    val list = mutableListOf<String>()
    for (i in 1..times) {
        list.add(this)
    }
    return list.joinToString(separator = separator)
}

fun Int.factorial(): Int {
    var fact = 1
    for (i in 2..this) {
        fact *= i
    }
    return fact

}