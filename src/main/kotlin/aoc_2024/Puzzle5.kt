package aoc_2024

import Solution

fun main() {
    Puzzle5().solve()
}

class Puzzle5 : Puzzle2024(5) {
    override fun solution(): Solution {
        processInput(getInput())
        val part1 = part1()
        val part2 = part2()
        return Solution(part1, part2)
    }

    private val after = HashMap<Int, HashSet<Int>>()
    private val before = HashMap<Int, HashSet<Int>>()
    private val updates = ArrayList<List<Int>>()

    fun processInput(input: List<String>) {
        val rulePattern = Regex("\\d{2}\\|\\d{2}")
        val updatePattern = Regex("^\\d{2}(,\\d{2})*$")
        for (line in input) {
            if (line.isEmpty()) {
                continue
            }

            if (rulePattern.matches(line)) {
                val pages = line.split("|")
                val leftPage = pages[0].toInt()
                val rightPage = pages[1].toInt()
                after.computeIfAbsent(leftPage) { HashSet() }
                after[leftPage]!!.add(rightPage)
                before.computeIfAbsent(rightPage) { HashSet() }
                before[rightPage]!!.add(leftPage)
            } else if (updatePattern.matches(line)) {
                val pages = line.split(",").map { it.toInt() }.toList()
                updates.add(pages)
            }
        }
    }

    private fun part1(): Int {
        return updates.filter { checkUpdateIsValid(it) }
            .map { findMedianValue(it) }
            .reduce { acc, i -> acc + i }
    }

    fun checkUpdateIsValid(update: List<Int>): Boolean {
        var i = 0
        while (i < update.size) {
            val current = update[i]
            if (i > 0) {
                val left = update.subList(0, i)
                if (after.contains(current)) {
                    val invalid = left.any { after[current]!!.contains(it) }
                    if (invalid) return false
                }
            }

            if (i < update.size - 1) {
                val right = update.subList(i + 1, update.size)
                if (before.contains(current)) {
                    val invalid = right.any { before[current]!!.contains(it) }
                    if (invalid) return false
                }
            }

            i++
        }
        return true
    }

    private fun findMedianValue(update: List<Int>): Int {
        return update[update.size / 2]
    }

    private fun part2(): Int {
        val invalids = updates.filter { !checkUpdateIsValid(it) }
        val sum = invalids.map { fixUpdate(it) }.map { findMedianValue(it) }.reduce { acc, i -> acc + i }
        return sum
    }

    fun fixUpdate(update: List<Int>): List<Int> {
        var l = 0
        val mutUpdate = update.toMutableList()
        while (l < mutUpdate.size - 1) {
            println(mutUpdate)
            var r = l + 1
            while (r < mutUpdate.size) {
                val current = mutUpdate[l]
                val next = mutUpdate[r]
                if (before.containsKey(current)) {
                    if (before[current]!!.contains(next)) {
                        mutUpdate[l] = next
                        mutUpdate[r] = current
                    }
                }
                r++
            }
            l++
        }

        return mutUpdate
    }
}