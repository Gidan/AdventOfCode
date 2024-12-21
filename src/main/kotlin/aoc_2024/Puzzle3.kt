package aoc_2024

import Solution

fun main() {
    Puzzle3().solve()
}

class Puzzle3 : Puzzle2024(3) {
    override fun solution(): Solution {
        val input = getInput()
        val part1 = part1(input)
        val part2 = part2(input)
        return Solution(part1, part2)
    }

    private fun part1(input: List<String>): Int {
        var accumulation = 0
        var count = 0
        var expectedCharAlphabet = "m"
        var currentNumber = 0
        val numberStack = mutableListOf<Int>()
        val sequence = StringBuilder()
        for (line in input) {
            var i = 0
            while (i < line.length) {
                val currentChar = line[i]
                if (expectedCharAlphabet.contains(currentChar)) {
                    sequence.append(currentChar)
                    when {
                        currentChar == 'm' -> {
                            expectedCharAlphabet = "u"
                        }

                        currentChar == 'u' -> {
                            expectedCharAlphabet = "l"
                        }

                        currentChar == 'l' -> {
                            expectedCharAlphabet = "("
                        }

                        currentChar == '(' -> {
                            expectedCharAlphabet = "0123456789"
                        }

                        currentChar.isDigit() -> {
                            if (numberStack.size == 0) {
                                expectedCharAlphabet = "0123456789,"
                            } else {
                                expectedCharAlphabet = "0123456789)"
                            }

                            currentNumber = currentNumber * 10 + currentChar.digitToInt()
                        }

                        currentChar == ',' -> {
                            expectedCharAlphabet = "0123456789"
                            numberStack.add(currentNumber)
                            currentNumber = 0
                        }

                        currentChar == ')' -> {
                            expectedCharAlphabet = "m"
                            numberStack.add(currentNumber)
                            currentNumber = 0
                            if (numberStack.size == 2) {
                                val mul = numberStack[0] * numberStack[1]
                                accumulation += mul
                                println("$sequence = $mul -> $accumulation")
                                count++
                            }
                            sequence.clear()
                            numberStack.clear()
                        }
                    }
                } else {
                    numberStack.clear()
                    currentNumber = 0
                    sequence.clear()
                    expectedCharAlphabet = "m"
                }
                i++
            }
        }

        println(count)
        return accumulation
    }

    private fun part2(input: List<String>): Int {
        var accumulation = 0
        var count = 0
        var enabled = true
        var expectedCharAlphabet = "m"
        var currentNumber = 0
        val numberStack = mutableListOf<Int>()
        val sequence = StringBuilder()
        for (line in input) {
            var i = 0
            while (i < line.length) {
                val currentChar = line[i]
                if (expectedCharAlphabet.contains(currentChar)) {
                    sequence.append(currentChar)
                    when {
                        currentChar == 'd' -> {
                            expectedCharAlphabet = "o"
                        }

                        currentChar == 'o' -> {
                            expectedCharAlphabet = "(n"
                        }

                        currentChar == 'n' -> {
                            expectedCharAlphabet = "'"
                        }

                        currentChar == '\'' -> {
                            expectedCharAlphabet = "t"
                        }

                        currentChar == 't' -> {
                            expectedCharAlphabet = "("
                        }

                        currentChar == 'm' -> {
                            expectedCharAlphabet = "u"
                        }

                        currentChar == 'u' -> {
                            expectedCharAlphabet = "l"
                        }

                        currentChar == 'l' -> {
                            expectedCharAlphabet = "("
                        }

                        currentChar == '(' -> {
                            if (sequence.toString() == "do(" || sequence.toString() == "don't(") {
                                expectedCharAlphabet = ")"
                            } else {
                                expectedCharAlphabet = "0123456789"
                            }
                        }

                        currentChar.isDigit() -> {
                            if (numberStack.size == 0) {
                                expectedCharAlphabet = "0123456789,"
                            } else {
                                expectedCharAlphabet = "0123456789)"
                            }

                            currentNumber = currentNumber * 10 + currentChar.digitToInt()
                        }

                        currentChar == ',' -> {
                            expectedCharAlphabet = "0123456789"
                            numberStack.add(currentNumber)
                            currentNumber = 0
                        }

                        currentChar == ')' -> {
                            if (sequence.toString() == "do()") {
                                enabled = true
                                println("$sequence = $accumulation")
                                expectedCharAlphabet = "md"
                            } else if (sequence.toString() == "don't()") {
                                enabled = false
                                println("$sequence = $accumulation")
                                expectedCharAlphabet = "d"
                            } else {
                                numberStack.add(currentNumber)
                                currentNumber = 0
                                if (numberStack.size == 2) {
                                    val mul = numberStack[0] * numberStack[1]
                                    accumulation += mul
                                    println("$sequence = $mul -> $accumulation")
                                    count++
                                }
                                expectedCharAlphabet = "md"
                            }
                            numberStack.clear()
                            sequence.clear()
                        }
                    }
                } else {
                    numberStack.clear()
                    currentNumber = 0
                    sequence.clear()
                    expectedCharAlphabet = if (enabled) "md" else "d"
                }
                i++
            }
        }

        println(count)
        return accumulation
    }
}