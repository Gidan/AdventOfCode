package aoc_2023

import Puzzle
import Solution

fun main() {
    Puzzle14().solve()
}

class Puzzle14 : Puzzle() {
    override fun getPuzzleNumber() = 14

    override fun solution() : Solution {
        val input = getInput()

        val board = Board()
        input.forEach { board.add(it) }

        //create a clone for part2
        val board2 = board.clone()

        board.tiltNorth()

        var cycleCount = 0
        var repetitionFound = false

        val gridToCycles = mutableMapOf<String, Int>()
        val gridsList = mutableListOf<Grid>()
        while (!repetitionFound) {
            board2.spinCycle()
            cycleCount++

            val gridStr = board2.grid.toString()
            if (gridToCycles.contains(gridStr)) {
                repetitionFound = true
            } else {
                gridToCycles[gridStr] = cycleCount
            }

            gridsList.add(board2.grid.clone())
        }

        val lastGrid = gridsList[gridsList.size - 1]
        val cycles = gridToCycles[lastGrid.toString()]!!
        val index = ((1000000000 - cycles) % (gridsList.size - cycles)) + cycles - 1
        val grid = gridsList[index]

        return Solution(board.load(), grid.load())
    }

}

class Grid {

    private var grid = mutableListOf<CharArray>()

    fun add(line: String) {
        grid.add(line.toCharArray())
    }

    fun clone(): Grid {
        val newGrid = Grid()
        newGrid.grid = grid.map { it.copyOf() }.toMutableList()
        return newGrid
    }

    fun contentEquals(other: Grid?): Boolean {
        if (other == null) return false
        if (this.grid.size != other.grid.size) return false

        for (row in this.grid.indices) {
            if (!grid[row].contentEquals(other.grid[row])) {
                return false
            }
        }

        return true
    }

    fun load(): Int {
        var load = 0
        val height = grid.size

        grid.forEachIndexed { index, chars ->
            load += chars.count { it == 'O' } * (height - index)
        }

        return load
    }

    override fun toString(): String {
        return grid.joinToString("\n") { l -> l.joinToString("") }
    }

    fun tiltNorth() {
        for (col in 0..<grid[0].size) {
            tiltColumnNorth(col)
        }
    }

    private fun tiltSouth() {
        for (col in 0..<grid[0].size) {
            tiltColumnSouth(col)
        }
    }

    private fun tiltEast() {
        for (row in 0..<grid.size) {
            tiltRowEast(row)
        }
    }

    private fun tiltWest() {
        for (row in 0..<grid.size) {
            tiltRowWest(row)
        }
    }

    fun spinCycle() {
        tiltNorth()
        tiltWest()
        tiltSouth()
        tiltEast()
    }

    private fun tiltColumnNorth(colIndex: Int) {
        var swapped = true
        while (swapped) {
            swapped = false
            for (row in 0..<grid.size - 1) {
                if (grid[row][colIndex] == '.' && grid[row + 1][colIndex] == 'O') {
                    grid[row][colIndex] = 'O'
                    grid[row + 1][colIndex] = '.'
                    swapped = true
                }
            }
        }
    }

    private fun tiltColumnSouth(colIndex: Int) {
        var swapped = true
        while (swapped) {
            swapped = false
            for (row in grid.size - 1 downTo 1) {
                if (grid[row][colIndex] == '.' && grid[row - 1][colIndex] == 'O') {
                    grid[row][colIndex] = 'O'
                    grid[row - 1][colIndex] = '.'
                    swapped = true
                }
            }
        }
    }

    private fun tiltRowWest(row: Int) {
        var swapped = true
        while (swapped) {
            swapped = false
            for (colIndex in 0..<grid[0].size - 1) {
                if (grid[row][colIndex] == '.' && grid[row][colIndex + 1] == 'O') {
                    grid[row][colIndex] = 'O'
                    grid[row][colIndex + 1] = '.'
                    swapped = true
                }
            }
        }
    }

    private fun tiltRowEast(row: Int) {
        var swapped = true
        while (swapped) {
            swapped = false
            for (colIndex in grid[0].size - 1 downTo 1) {
                if (grid[row][colIndex] == '.' && grid[row][colIndex - 1] == 'O') {
                    grid[row][colIndex] = 'O'
                    grid[row][colIndex - 1] = '.'
                    swapped = true
                }
            }
        }
    }
}

class Board {

    fun clone(): Board {
        val newBoard = Board()
        newBoard.grid = grid.clone()
        return newBoard
    }

    var grid = Grid()

    fun add(line: String) {
        grid.add(line)
    }

    override fun toString(): String {
        return grid.toString()
    }

    fun load(): Int {
        return grid.load()
    }

    fun spinCycle() {
        grid.spinCycle()
    }

    fun tiltNorth() {
        grid.tiltNorth()
    }
}

