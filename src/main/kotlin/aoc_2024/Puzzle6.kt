package aoc_2024

import Solution
import util.Vec2D

fun main() {
    Puzzle6().solve()
}

class Puzzle6 : Puzzle2024(6) {

    private val guards = listOf('>', '^', '<', 'v')

    override fun solution(): Solution {
        val input = getInput()
        val distinctPositions = part1(input)
        val part1 = distinctPositions.size
        val part2 = part2(input, distinctPositions)
        return Solution(part1, part2)
    }

    private fun findStartPosition(input: List<String>): Vec2D {
        for ((i, line) in input.withIndex()) {
            for ((ic, c) in line.withIndex()) {
                if (guards.any { it == c }) {
                    return Vec2D(ic, i)
                }
            }
        }

        throw Exception("start position not found")
    }

    private val incrementMap = mapOf(
        '<' to Vec2D(-1, 0),
        '^' to Vec2D(0, -1),
        '>' to Vec2D(1, 0),
        'v' to Vec2D(0, 1)
    )

    private val nextDirectionMap = mapOf(
        '<' to '^',
        '^' to '>',
        '>' to 'v',
        'v' to '<'
    )

    private fun part1(input: List<String>): Set<Vec2D> {
        val distinctPositions = mutableSetOf<Vec2D>()
        var position = findStartPosition(input)
        var currentDirection = input[position.y][position.x]
        while (!position.isOutOfBounds(input)) {
            distinctPositions.add(position)
            val increment = incrementMap[currentDirection]!!
            val nextPosition = position + increment
            if (nextPosition.isOutOfBounds(input)) break
            val nextChar = input[nextPosition.y][nextPosition.x]
            when (nextChar) {
                '.', '^', '>', '<', 'v' -> position = nextPosition
                '#' -> currentDirection = nextDirectionMap[currentDirection]!!
            }
        }

        return distinctPositions
    }

    private fun Vec2D.isOutOfBounds(input: List<String>): Boolean {
        val height = input.size
        if (height == 0) return true
        val width = input[0].length
        if (this.x < 0 || this.x >= width) return true
        if (this.y < 0 || this.y >= height) return true
        return false
    }

    private fun part2(input: List<String>, distinctPositions: Set<Vec2D>): Int {
        val startPosition = findStartPosition(input)
        return distinctPositions.filter { it != startPosition }.count {
            isLoop(input, startPosition, Vec2D(it.x, it.y))
        }
    }

    private data class PosDir(val position: Vec2D, val direction: Char)

    private fun isLoop(input: List<String>, start: Vec2D, obstruction: Vec2D): Boolean {
        var position = start
        var currentDirection = input[position.y][position.x]
        val distinctPosDirs = mutableSetOf<PosDir>()
        while (!position.isOutOfBounds(input)) {
            if (distinctPosDirs.contains(PosDir(position, currentDirection))) return true
            distinctPosDirs.add(PosDir(position, currentDirection))
            val increment = incrementMap[currentDirection]!!
            val nextPosition = position + increment
            if (nextPosition.isOutOfBounds(input)) break
            val nextChar = if (nextPosition == obstruction) {
                'O'
            } else {
                input[nextPosition.y][nextPosition.x]
            }
            when (nextChar) {
                '.', '^', '>', '<', 'v' -> position = nextPosition
                '#', 'O' -> currentDirection = nextDirectionMap[currentDirection]!!
            }
        }

        return false
    }


}