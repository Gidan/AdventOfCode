package aoc_2024

import Puzzle

abstract class Puzzle2024(private val puzzleNumber : Int) : Puzzle() {
    override fun getPuzzleNumber(): Int = puzzleNumber
    override fun getYearEdition(): Int = 2024
}