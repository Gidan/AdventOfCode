package aoc_2024

import Solution
import util.Vec2D

fun main() {
    Puzzle10().solve()
}

class Puzzle10 : Puzzle2024(10) {

    override fun solution(): Solution {
        val map = TopographicMap.parse(getInput())
        val part1 = part1(map)
        val part2 = part2(map)
        return Solution(part1, part2)
    }

    private fun part1(map: TopographicMap): Number {
        return map.trailHeads().sumOf {
            val set = mutableSetOf<Vec2D>()
            map.visit(it, null, set)
            set.size
        }
    }

    private fun part2(map: TopographicMap): Number {
        return map.trailHeads().sumOf {
            map.visit(it, null)
        }
    }

    class TopographicMap(val input: List<String>) {

        private var trailheads = mutableSetOf<Vec2D>()
        fun trailHeads(): Set<Vec2D> = trailheads

        init {
            input.forEachIndexed { lineIndex, line ->
                line.forEachIndexed { charIndex, c ->
                    if (c == '0') {
                        trailheads.add(Vec2D(charIndex, lineIndex))
                    }
                }
            }
        }

        companion object {
            fun parse(input: List<String>): TopographicMap {
                val topographicMap = TopographicMap(input)
                return topographicMap
            }
        }

        private fun contains(pos: Vec2D): Boolean {
            return pos.y >= 0 && pos.y < input.size && pos.x >= 0 && pos.x < input[0].length
        }

        fun visit(pos: Vec2D, previousPos: Vec2D?, trailEnds: MutableSet<Vec2D>) {
            if (!contains(pos)) {
                return
            }

            if (input[pos.y][pos.x] == '.') {
                return
            }

            val current = input[pos.y][pos.x].digitToInt()
            val previous = previousPos?.let {
                input[previousPos.y][previousPos.x].digitToInt()
            } ?: -1

            if (current != previous + 1) {
                return
            }

            if (current == 9) {
                trailEnds.add(pos)
                return
            }

            pos.adjacents()
                .filter { contains(it) }
                .filter { it != previousPos }
                .forEach { visit(it, pos, trailEnds) }
        }

        fun visit(pos: Vec2D, previousPos: Vec2D?): Int {
            if (!contains(pos)) {
                return 0
            }

            if (input[pos.y][pos.x] == '.') {
                return 0
            }

            val current = input[pos.y][pos.x].digitToInt()
            val previous = previousPos?.let {
                input[previousPos.y][previousPos.x].digitToInt()
            } ?: -1

            if (current != previous + 1) {
                return 0
            }

            if (current == 9) {
                return 1
            }

            return pos.adjacents()
                .filter { contains(it) }
                .filter { it != previousPos }
                .sumOf { visit(it, pos) }
        }
    }
}

private fun Vec2D.adjacents() : List<Vec2D> = listOf(
    Vec2D(this.x, this.y - 1),
    Vec2D(this.x - 1, this.y),
    Vec2D(this.x + 1, this.y),
    Vec2D(this.x, this.y + 1),
)




