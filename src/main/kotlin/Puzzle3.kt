import kotlin.math.max
import kotlin.math.min

fun main() {
    Puzzle3().solve()
}

typealias Coordinates = Pair<Int,Int>

class Puzzle3 {

    fun solve() {
        println("AdventOfCode 2023 puzzle #3")

        val lines = Util.getInput(3)
        val width = lines[0].length
        val height = lines.size

        val checkRight: (String, Int) -> Pair<Boolean, Boolean> = { line, i -> Pair(i < width && line[i] != '.', if (i < width) line[i] == '*' else false) }
        val checkLeft: (String, Int) -> Pair<Boolean, Boolean> = { line, i -> Pair(i - 1 > 0 && line[i] != '.', if (i - 1 > 0) line[i] == '*' else false) }
        val checkRowInRange: (Int, Int, Int) -> Pair<Boolean, Int> = { lineIndex, start, end ->
            if (lineIndex in 0..<height) {
                val l = lines[lineIndex]
                val substring = l.substring(start, end)
                var gearIndex = substring.indexOf('*')
                if (gearIndex >= 0) {
                    gearIndex += start
                } else {
                    gearIndex = -1
                }
                Pair(substring.any { it != '.' && !it.isDigit() }, gearIndex)
            } else {
                Pair(false, -1)
            }
        }

        var sum = 0
        var gearRatio = 0

        val gearMap = mutableMapOf<Coordinates, MutableList<Int>>()

        for (lineIdx in lines.indices) {
            val line = lines[lineIdx]
            var i = 0
            var numStr = ""
            while (i < width) {
                if (line[i].isDigit()) {
                    numStr += line[i]
                    var digitIdx = i + 1
                    while (digitIdx < width && line[digitIdx].isDigit()) {
                        numStr += line[digitIdx]
                        digitIdx++
                    }
                    val num = numStr.toInt()

                    val symbolRight = checkRight(line, digitIdx)
                    val symbolLeft = checkLeft(line, i - 1)
                    val symbolAbove = checkRowInRange(
                        lineIdx - 1,
                        max(i - 1, 0),
                        min(digitIdx + 1, width - 1)
                    )
                    val symbolBelow = checkRowInRange(
                        lineIdx + 1,
                        max(i - 1, 0),
                        min(digitIdx + 1, width - 1)
                    )

                    if (symbolRight.first or symbolLeft.first or symbolAbove.first or symbolBelow.first) {
                        //symbol found
                        sum += num
                        //println("adding $num")
                    }

                    if (symbolRight.second) {
                        val gearCoord = Pair(lineIdx, digitIdx)
                        val gearList = gearMap.getOrDefault(gearCoord, mutableListOf())
                        gearList.add(num)
                        gearMap[gearCoord] = gearList
                    }
                    if (symbolLeft.second) {
                        val gearCoord = Pair(lineIdx, i - 1)
                        val gearList = gearMap.getOrDefault(gearCoord, mutableListOf())
                        gearList.add(num)
                        gearMap[gearCoord] = gearList
                    }
                    if (symbolAbove.second >= 0) {
                        val gearCoord = Pair(lineIdx - 1, symbolAbove.second)
                        val gearList = gearMap.getOrDefault(gearCoord, mutableListOf())
                        gearList.add(num)
                        gearMap[gearCoord] = gearList
                    }
                    if (symbolBelow.second >= 0) {
                        val gearCoord = Pair(lineIdx + 1, symbolBelow.second)
                        val gearList = gearMap.getOrDefault(gearCoord, mutableListOf())
                        gearList.add(num)
                        gearMap[gearCoord] = gearList
                    }

                    numStr = ""
                    i = digitIdx
                } else {
                    i++
                }
            }

        }

        println(sum)

        val filter = gearMap.filter { (symbolPosition, numberList) -> numberList.size == 2 }
        println(gearMap)
        filter.forEach { (t, u) -> gearRatio += u.reduce { acc, i -> acc * i } }
        println(gearRatio)

    }


}
