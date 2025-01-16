package aoc_2024

import Solution

fun main() {
    Puzzle7().solve()
}

class Puzzle7 : Puzzle2024(7) {

    override fun solution(): Solution {
        val input = processInput(getInput())
        val part1 = part1(input)
        val part2 = part2(input)
        return Solution(part1, part2)
    }

    enum class Operators {
        PLUS, TIMES, CONCAT
    }

    private val simpleOperator = setOf(Operators.PLUS, Operators.TIMES)
    private val simpleOperatorCache = mutableMapOf<Int, List<List<Operators>>>()
    private val extendedOperator = setOf(Operators.PLUS, Operators.TIMES, Operators.CONCAT)
    private val extendedOperatorCache = mutableMapOf<Int, List<List<Operators>>>()

    private data class Equation(val testValue: Long, val operands: List<Int>) {
        companion object {
            fun from(string: String): Equation {
                val split = string.split(":")
                val testValue = split[0].toLong()
                val operands = split[1].trim().split(" ").map { it.toInt() }.toList()
                return Equation(testValue, operands)
            }
        }

        fun isTrue(operators: Set<Operators>, cache: MutableMap<Int, List<List<Operators>>>): Boolean {
            if (operands.size == 1) {
                return testValue == operands[0].toLong()
            }

            val dispositions = operators.dispositionsWithRepetions(operands.size - 1, cache)

            dispositions.forEach {
                var i = 1
                var result : Long = operands[0].toLong()
                while (i < operands.size) {
                    val operator = it[i-1]
                    val operand = operands[i]
                    result = when (operator) {
                        Operators.PLUS -> result + operand
                        Operators.TIMES -> result * operand
                        Operators.CONCAT -> "$result$operand".toLong()
                    }
                    i++
                }
                if (result == testValue) {
                    return true
                }
            }

            return false
        }
    }

    private fun processInput(input: List<String>): List<Equation> {
        return input.map { Equation.from(it) }.toList()
    }

    private fun part1(input: List<Equation>): Long {
        val start = System.currentTimeMillis()
        val calibration = input.filter { it.isTrue(simpleOperator, simpleOperatorCache) }.sumOf { it.testValue }
        println("Exec Time (ms): ${System.currentTimeMillis() - start}")
        return calibration
    }

    private fun part2(input: List<Equation>): Long {
        val start = System.currentTimeMillis()
        val calibration = input.sortedBy { it.operands.size }.filter { it.isTrue(extendedOperator, extendedOperatorCache) }.sumOf { it.testValue }
        println("Exec Time (ms): ${System.currentTimeMillis() - start}")
        return calibration
    }
}

fun <T> Set<T>.dispositionsWithRepetions(spaces: Int, cache: MutableMap<Int, List<List<T>>>) : List<List<T>> {
    if (cache.containsKey(spaces)) {
        return cache[spaces]!!
    }

    if (spaces == 1) {
        val temp = mutableListOf<List<T>>()
        this.forEach { temp.add(listOf(it)) }
        cache[spaces] = temp
        return cache[spaces]!!
    }

    val dispositions = mutableListOf<List<T>>()
    val subDispositions = dispositionsWithRepetions(spaces - 1, cache)
    subDispositions.forEach { subDisposition ->
        this.forEach { t ->
            val temp = mutableListOf<T>().also {
                it.addAll(subDisposition)
                it.add(t)
            }

            dispositions.add(temp)
        }
    }
    cache[spaces] = dispositions
    return dispositions
}