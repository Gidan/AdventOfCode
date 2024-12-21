package aoc_2023

import Puzzle
import Solution
import kotlin.math.max
import kotlin.math.min

fun main() {
    Puzzle3().solve()
}

typealias Coordinates = Pair<Int, Int>

data class CheckResult(val symbolFound: Boolean, val starFound: Boolean)
data class CheckLineResult(val symbolFound: Boolean, val gearPosition: Int)

class Puzzle3 : Puzzle() {

    override fun getPuzzleNumber(): Int = 3

    override fun solution() : Solution {
        val lines = getInput()
        val width = lines[0].length
        val height = lines.size

        val checkRight: (String, Int) -> CheckResult =
            { line, i -> CheckResult(i < width && line[i] != '.', if (i < width) line[i] == '*' else false) }
        val checkLeft: (String, Int) -> CheckResult =
            { line, i -> CheckResult(i - 1 > 0 && line[i] != '.', if (i - 1 > 0) line[i] == '*' else false) }
        val checkRowInRange: (Int, Int, Int) -> CheckLineResult = { lineIndex, start, end ->
            if (lineIndex in 0..<height) {
                val l = lines[lineIndex]
                val substring = l.substring(start, end)
                var gearIndex = substring.indexOf('*')
                if (gearIndex >= 0) {
                    gearIndex += start
                } else {
                    gearIndex = -1
                }
                CheckLineResult(substring.any { it != '.' && !it.isDigit() }, gearIndex)
            } else {
                CheckLineResult(false, -1)
            }
        }

        var sum = 0
        var gearRatio = 0

        val gearMap = mutableMapOf<Coordinates, MutableList<Int>>()

        for (lineIdx in lines.indices) {
            val line = lines[lineIdx]
            var i = 0
            var numLiteral = ""
            while (i < width) {
                if (line[i].isDigit()) {
                    numLiteral += line[i]
                    var digitIdx = i + 1
                    while (digitIdx < width && line[digitIdx].isDigit()) {
                        numLiteral += line[digitIdx]
                        digitIdx++
                    }
                    val num = numLiteral.toInt()

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

                    if (symbolRight.symbolFound or symbolLeft.symbolFound or symbolAbove.symbolFound or symbolBelow.symbolFound) {
                        //symbol found
                        sum += num
                    }

                    val addGear: (Coordinates) -> Unit = {
                        val gearList = gearMap.getOrDefault(it, mutableListOf())
                        gearList.add(num)
                        gearMap[it] = gearList
                    }

                    if (symbolRight.starFound) {
                        val gearPosition = Pair(lineIdx, digitIdx)
                        addGear(gearPosition)
                    }
                    if (symbolLeft.starFound) {
                        val gearPosition = Pair(lineIdx, i - 1)
                        addGear(gearPosition)
                    }
                    if (symbolAbove.gearPosition >= 0) {
                        val gearPosition = Pair(lineIdx - 1, symbolAbove.gearPosition)
                        addGear(gearPosition)
                    }
                    if (symbolBelow.gearPosition >= 0) {
                        val gearPosition = Pair(lineIdx + 1, symbolBelow.gearPosition)
                        addGear(gearPosition)
                    }

                    numLiteral = ""
                    i = digitIdx
                } else {
                    i++
                }
            }

        }

        val gearsWithTwoNumbers = gearMap.filter { (_, numberList) -> numberList.size == 2 }
        gearsWithTwoNumbers.forEach { (_, n) -> gearRatio += n.reduce { acc, i -> acc * i } }

        return Solution(sum, gearRatio)
    }
}
