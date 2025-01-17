package aoc_2024

import Solution
import util.Vec2D

fun main() {
    Puzzle8().solve()
}

class Puzzle8 : Puzzle2024(8) {

    override fun solution(): Solution {
        val input = processInput(getInput())
        val part1 = part1(input)
        val part2 = part2(input)
        return Solution(part1, part2)
    }

    private fun processInput(input: List<String>): Map {
        val map = Map(input[0].length, input.size)
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c != '.') {
                    map.antennas.putIfAbsent(c, mutableListOf())
                    map.antennas[c]!!.add(Vec2D(x, y))
                }
            }
        }
        return map
    }

    private fun part1(map: Map): Number {
        val antinodes = mutableSetOf<Vec2D>()
        map.antennas.entries.forEach { entry ->
            val sameFrequencyAntennas = entry.value
            sameFrequencyAntennas.cycleCouples { a, b ->
                val anX = (b.x - a.x) + b.x
                val anY = (b.y - a.y) + b.y
                val anCoordinates = Vec2D(anX, anY)
                if (map.isInMapBounds(anCoordinates)) {
                    antinodes.add(anCoordinates)
                }
            }
        }
        return antinodes.size
    }

    private fun part2(map: Map): Number {
        val antinodes = mutableSetOf<Vec2D>()
        map.antennas.entries.forEach { entry ->
            val sameFrequencyAntennas = entry.value
            sameFrequencyAntennas.cycleCouples { a, b ->
                antinodes.add(a)
                antinodes.add(b)
                var temp: Vec2D?
                var iteration = 1
                do {
                    val anX = (b.x - a.x) * iteration + b.x
                    val anY = (b.y - a.y) * iteration + b.y
                    temp = Vec2D(anX, anY)
                    iteration++
                    if (map.isInMapBounds(temp)) {
                        antinodes.add(temp)
                    }
                } while (map.isInMapBounds(temp!!))
            }
        }
        return antinodes.size
    }
}


class Map(private val w: Int, private val h: Int) {
    val antennas = mutableMapOf<Char, MutableList<Vec2D>>()

    fun isInMapBounds(coord: Vec2D): Boolean {
        return (coord.x in 0..<w) && (coord.y in 0..<h)
    }
}

fun <T> List<T>.cycleCouples(couple: (a: T, b: T) -> Unit) {
    this.forEach { a ->
        this.filter { it != a }.forEach { b ->
            couple(a, b)
        }
    }
}