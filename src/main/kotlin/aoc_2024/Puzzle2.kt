package aoc_2024

import Solution
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    Puzzle2().solve()
}

class Puzzle2 : Puzzle2024(2) {
    override fun solution(): Solution {
        val input = getInput()
        val part1 = input.map { line ->
            val levels = line.split(" ").map { it.toInt() }
            isLevelSafe(levels)
        }.count{ it }

        val part2 = input.map { line ->
            val levels = line.split(" ").map { it.toInt() }
            val isSafe = if (!isLevelSafe(levels)) {
               levels.indices.map {
                   val subList = levels.toMutableList()
                   subList.removeAt(it)
                   isLevelSafe(subList)
               }.any { it }
            } else {
                true
            }

            isSafe
        }.count{ it }

        return Solution(part1, part2)
    }

    private fun isLevelSafe(levels: List<Int>) : Boolean {
        var isSafe = true
        var direction = 0
        var i = 1
        while (i < levels.size && isSafe){
            val distance = levels[i] - levels[i-1]
            val absDistance = abs(distance)
            if (absDistance !in 1..3) {
                isSafe = false
            } else {
                if (i == 1) {
                    direction = if (distance > 0) 1 else -1
                }
                else if (direction.sign != distance.sign){
                    isSafe = false
                }
            }
            i++
        }
        return isSafe
    }
}