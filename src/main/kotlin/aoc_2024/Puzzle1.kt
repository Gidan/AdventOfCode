package aoc_2024

import Solution
import kotlin.math.abs

fun main() {
    Puzzle1().solve()
}

class Puzzle1 : Puzzle2024(1) {

    private val leftList = ArrayList<Int>()
    private val rightList = ArrayList<Int>()

    private fun prepareLists() {
        val lines = getInput()
        for (line in lines) {
            val split = line.split("   ").map { it.toInt() }
            leftList.add(split[0])
            rightList.add(split[1])
        }

        leftList.sort()
        rightList.sort()
    }

    override fun solution() : Solution {
        prepareLists()

        var totalDistance = 0
        val size = leftList.size
        for (i in 0..<size) {
            val distance = abs(rightList[i] - leftList[i])
            totalDistance += distance
        }

        var totalSimilarity = 0
        for (left in leftList) {
            val count = rightList.count { it == left }
            val similarity = left * count
            totalSimilarity += similarity
        }

        return Solution(totalDistance, totalSimilarity)
    }
}