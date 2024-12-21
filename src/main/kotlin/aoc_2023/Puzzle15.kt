package aoc_2023

import Puzzle
import Solution

fun main() {
    Puzzle15().solve()
}

class Puzzle15 : Puzzle() {
    override fun getPuzzleNumber() = 15

    override fun solution() : Solution {
        val input = getInput()

        val instructions = input.joinToString("").split(",")
        val part1 = instructions.sumOf { hash(it) }

        val boxes = Array(256) { _ -> Box() }

        instructions.forEach { i ->
            if (i.contains("=")) {
                val (label, focalLength) = i.split("=")
                val boxNumber = hash(label)
                val box = boxes[boxNumber]
                box.addOrUpdate(Lens(label, focalLength.toInt()))
            } else {
                val label = i.substringBefore('-')
                val boxNumber = hash(label)
                val box = boxes[boxNumber]
                box.remove(label)
            }
        }

        val totalFocusingPower =
            boxes.mapIndexed { index, box -> box.focusingPower(index + 1) }.reduce { acc, p -> acc + p }

        return Solution(part1, totalFocusingPower)
    }

    private fun hash(str: String): Int {
        var hash = 0
        str.forEach { c ->
            hash += c.code
            hash *= 17
            hash %= 256
        }
        return hash
    }
}

class Box {
    private val lenses = mutableListOf<Lens>()

    // mapping lens label to index in the lenses array
    private val lensesMap = mutableMapOf<String, Int>()

    fun remove(label: String) {
        if (lensesMap.containsKey(label)) {
            val index = lensesMap[label]!!
            //update indexes
            for (i in index..<lenses.size) {
                val lens = lenses[i]
                val currentIndex = lensesMap[lens.label]!!
                lensesMap[lens.label] = currentIndex - 1
            }
            lenses.removeAt(index)
            lensesMap.remove(label)
        }
    }

    fun addOrUpdate(lens: Lens) {
        if (lensesMap.containsKey(lens.label)) {
            //already that type of lens in the box
            val index = lensesMap[lens.label]!!
            val l = lenses[index]
            l.focalLength = lens.focalLength
        } else {
            lenses.add(lens)
            lensesMap[lens.label] = lenses.size - 1
        }
    }

    fun isEmpty() = lenses.isEmpty()

    fun focusingPower(boxNumber: Int): Int {
        return lensesMap.entries.sumOf { (_, index) ->
            val lens = lenses[index]
            boxNumber * (index + 1) * lens.focalLength
        }
    }
}

data class Lens(val label: String, var focalLength: Int)

