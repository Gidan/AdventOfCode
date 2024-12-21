import java.io.File
import java.lang.Exception
import java.nio.file.Paths

abstract class Puzzle {

    fun solve() : Solution{
        println("AdventOfCode ${getYearEdition()} puzzle #${getPuzzleNumber()}")
        val solution = solution()
        println("part1: ${solution.part1}")
        println("part2: ${solution.part2}")
        return solution
    }

    protected abstract fun solution() : Solution

    protected abstract fun getPuzzleNumber() : Int

    open fun getYearEdition() : Int = 2023

    fun getInput(): List<String> {
        val path = Paths.get("/${getYearEdition()}", "${getPuzzleNumber()}", "input.txt")
        val res = javaClass.getResource(path.toString())
            ?: throw Exception("Input file not found [resources/${getPuzzleNumber()}/input.txt]")
        val lines = mutableListOf<String>()
        res.let {
            val filePath = it.path
            lines.addAll(File(filePath).readLines())
        }
        return lines
    }

}

data class Solution(val part1 : Number, val part2 : Number)