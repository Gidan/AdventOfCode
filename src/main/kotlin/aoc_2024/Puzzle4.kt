package aoc_2024

import Solution
import util.Vec2D

fun main() {
    Puzzle4().solve()
}

class Puzzle4 : Puzzle2024(4) {
    override fun solution(): Solution {
        val input = getInput()
        val part1 = part1(input)
        val part2 = part2(input)
        return Solution(part1, part2)
    }

    private fun part1(input: List<String>): Int {
        val width = input[0].length
        val height = input.size
        var count = 0
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(1, 0))) count++
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(1, 1))) count++
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(0, 1))) count++
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(-1, 1))) count++
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(-1, 0))) count++
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(-1, -1))) count++
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(0, -1))) count++
                if (isXMAS(Vec2D(x, y), 'X', input, Vec2D(1, -1))) count++
            }
        }
        return count
    }

    private fun part2(input: List<String>): Int {
        val width = input[0].length
        val height = input.size
        var count = 0
        for (y in 0..<height) {
            for (x in 0..<width) {
                if (isX_MAS(Vec2D(x, y), input)) count++
            }
        }
        return count
    }

    private fun isXMAS(
        position: Vec2D,
        expectedChar: Char,
        input: List<String>,
        direction: Vec2D
    ): Boolean {
        val width = input[0].length
        val height = input.size
        if (position.x < 0 || position.x >= width || position.y < 0 || position.y >= height) {
            return false
        }

        if (input[position.y][position.x] == expectedChar) {
            if (expectedChar == 'S') {
                return true
            }
            val nextExpectedChar = when (expectedChar) {
                'X' -> 'M'
                'M' -> 'A'
                else -> 'S'
            }
            return isXMAS(position + direction, nextExpectedChar, input, direction)
        }
        return false
    }

    private fun isX_MAS(
        position: Vec2D,
        input: List<String>
    ): Boolean {
        val width = input[0].length
        val height = input.size
        if (position.x <= 0 || position.x >= width - 1 || position.y <= 0 || position.y >= height - 1) {
            return false
        }

        if (input[position.y][position.x] != 'A') {
            return false
        }

        return ((
                (input[position.y - 1][position.x - 1] == 'M' && input[position.y + 1][position.x + 1] == 'S')
                        ||
                        (input[position.y - 1][position.x - 1] == 'S' && input[position.y + 1][position.x + 1] == 'M'))

                &&

                ((input[position.y - 1][position.x + 1] == 'M' && input[position.y + 1][position.x - 1] == 'S')
                        ||
                        (input[position.y - 1][position.x + 1] == 'S' && input[position.y + 1][position.x - 1] == 'M')))
    }
}