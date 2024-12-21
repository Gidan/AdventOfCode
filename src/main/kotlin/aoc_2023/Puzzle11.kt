package aoc_2023

import Puzzle
import Solution
import util.Vec2D
import util.logln
import kotlin.math.abs

fun main() {
    Puzzle11().solve()
}

class Puzzle11 : Puzzle() {
    override fun getPuzzleNumber() = 11

    override fun solution() : Solution {
        val input = getInput()
        //logln(input.joinToString(separator = "\n"))

        val part1 = getDistance(input, 1)
        val part2 = getDistance(input, 999999)

        return Solution(part1, part2)
    }

    private fun getDistance(input : List<String>, multiplier : Int) : Long {
        val galaxies = findGalaxies(input, multiplier)
        var totalDistance = 0L
        var count = 0
        for (i in 0..<galaxies.size - 1) {
            for (i2 in i + 1..<galaxies.size) {
                count++
                val shortestDistance = shortestDistance(galaxies[i], galaxies[i2])
                //logln("$count - ${galaxies[i]}-->${galaxies[i2]} = $shortestDistance")
                totalDistance += shortestDistance
            }
        }

        return totalDistance
    }

    private fun shortestDistance(g1: Vec2D, g2: Vec2D): Int {
        return when {
            //same line
            g1.y == g2.y -> abs(g1.x - g2.x)
            //same column
            g1.x == g2.x -> abs(g1.y - g2.y)
            else -> abs(g1.y - g2.y) + abs(g1.x - g2.x)
        }
    }

    private fun findGalaxies(input: List<String>, multiplier : Int): List<Vec2D> {
        val galaxies = mutableListOf<Vec2D>()
        var lineVoidOffset = 0
        input.forEachIndexed { lineIndex, line ->
            if (line.contains("#")) {
                var columnVoidOffset = 0
                line.forEachIndexed { charIndex, c ->
                    if (c == '#') {
                        galaxies.add(
                            Vec2D(
                                charIndex + (columnVoidOffset * multiplier),
                                lineIndex + (lineVoidOffset * multiplier)
                            )
                        )
                    } else {
                        if (input.all { l -> l[charIndex] == '.' }) {
                            columnVoidOffset++
                        }
                    }
                }
            } else {
                lineVoidOffset++
            }
        }

        return galaxies
    }
}

