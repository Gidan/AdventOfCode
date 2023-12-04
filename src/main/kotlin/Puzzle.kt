import java.io.File

abstract class Puzzle {

    fun solve() {
        println("AdventOfCode 2023 puzzle #${getPuzzleNumber()}")
        solution()
    }

    protected abstract fun solution()

    protected abstract fun getPuzzleNumber() : Int

    protected fun getInput(): List<String> {
        val path = Puzzle::class.java.getResource("${getPuzzleNumber()}/input.txt")
        val lines = mutableListOf<String>()
        path?.let {
            val filePath = it.path
            lines.addAll(File(filePath).readLines())
        }
        return lines
    }

}