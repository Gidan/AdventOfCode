package aoc_2023

import Puzzle
import Solution

fun main() {
    Puzzle6().solve()
}

class Puzzle6 : Puzzle() {

    override fun getPuzzleNumber() = 6

    override fun solution() : Solution {
        val input = getInput()
        val timesAsString = input[0].removePrefix("Time:")
            .trimStart()
            .split(" ")
            .filter { it.isNotEmpty() }
        val distancesAsString =
            input[1].removePrefix("Distance:")
                .trimStart()
                .split(" ")
                .filter { it.isNotEmpty() }

        val times = timesAsString.map{it.toULong()}
        val distances = distancesAsString.map{it.toULong()}

        val timesJoined = timesAsString.joinToString(" ").replace(" ", "").toULong()
        val distancesJoined = distancesAsString.joinToString(" ").replace(" ", "").toULong()

        val waysToWin = mutableListOf<ULong>()

        var raceIndex = 0
        while (raceIndex < times.size) {
            val raceTime = times[raceIndex]
            val recordDistance = distances[raceIndex]
            waysToWin.add(waysToWinRace(raceTime, recordDistance))
            raceIndex++
        }

        val part1 = waysToWin.reduce { acc, w -> acc * w }
        val waysToWinRace = waysToWinRace(timesJoined, distancesJoined)
        return Solution(part1.toLong(), waysToWinRace.toLong())
    }

    private fun waysToWinRace(raceTime: ULong, recordDistance: ULong): ULong {
        var waysToWinThisRace = 0uL

        for (pushTime in 0uL..raceTime) {
            val distance = pushTime * (raceTime - pushTime)
            if (distance > recordDistance) {
                waysToWinThisRace++
            }
        }

        return waysToWinThisRace
    }
}

