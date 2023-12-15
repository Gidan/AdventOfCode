import java.io.File
import java.lang.Exception

abstract class Puzzle {

    fun solve() {
        println("AdventOfCode 2023 puzzle #${getPuzzleNumber()}")
        solution()
    }

    protected abstract fun solution()

    protected abstract fun getPuzzleNumber() : Int

    protected fun getInput(): List<String> {
        val path = Puzzle::class.java.getResource("${getPuzzleNumber()}/input.txt")
            ?: throw Exception("Input file not found [resources/${getPuzzleNumber()}/input.txt]")
        val lines = mutableListOf<String>()
        path.let {
            val filePath = it.path
            lines.addAll(File(filePath).readLines())
        }
        return lines
    }

}