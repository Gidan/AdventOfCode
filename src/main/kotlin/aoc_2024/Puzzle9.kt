package aoc_2024

import Solution

fun main() {
    Puzzle9().solve()
}

class Puzzle9 : Puzzle2024(9) {

    override fun solution(): Solution {
        val input = processInput(getInput())
        val part1 = part1(input)
        val part2 = part2(input)
        return Solution(part1, part2)
    }

    private fun processInput(input: List<String>): String {
        return input[0]
    }

    private fun expandDisk(disk: String): MutableList<Block> {
        val sb = mutableListOf<Block>()
        var isFile = true
        var fileId = 0
        disk.forEach { c ->
            val blocks = c.digitToInt()
            if (isFile) {
                for (i in 0 until blocks) {
                    sb.add(Block.File(fileId))
                }
                fileId++
            } else {
                for (i in 0 until blocks) {
                    sb.add(Block.EMPTY())
                }
            }

            isFile = !isFile
        }

        return sb
    }

    private fun part1(disk: String): Number {
        val expandDisk = expandDisk(disk)

        var left = 0
        var right = expandDisk.size - 1
        while (left < right) {
            if (expandDisk[left] !is Block.EMPTY) {
                left++
                continue
            }

            if (expandDisk[right] !is Block.File) {
                right--
                continue
            }

            val empty = expandDisk[left]
            expandDisk[left] = expandDisk[right]
            expandDisk[right] = empty
        }

        return expandDisk.filterIsInstance<Block.File>()
            .mapIndexed { index, block -> index.toLong() * block.fileId.toLong() }
            .sum()
    }

    private fun part2(disk: String): Number {
        val expandDisk = expandDisk(disk)

        val fileIds = expandDisk.filter { it !is Block.EMPTY }.distinctBy { it.id }.map { it.id }.reversed()

        for (fileId in fileIds) {
            val rightStart = expandDisk.indexOfFirst { it.id == fileId }
            val rightEnd = expandDisk.indexOfLast { it.id == fileId }
            val blocksToMoveCount = rightEnd - rightStart + 1
            var leftStart = 0
            var leftEnd = 0
            while (leftStart < rightStart) {
                if (expandDisk[leftStart] !is Block.EMPTY) {
                    leftStart++
                    leftEnd = leftStart
                    continue
                }

                if (expandDisk[leftEnd] is Block.EMPTY) {
                    leftEnd++
                    continue
                }

                val availableSpace = leftEnd - leftStart
                if (availableSpace >= blocksToMoveCount) {
                    for (i in 0..<blocksToMoveCount) {
                        expandDisk[leftStart + i] = expandDisk[rightStart + i]
                        expandDisk[rightStart + i] = Block.EMPTY()
                    }
                    break
                } else {
                    leftStart += availableSpace
                    leftEnd = leftStart
                }
            }
        }

        return expandDisk
            .mapIndexed { index, block -> index.toLong() * block.id.toLong() }.filter { it > 0 }
            .sum()
    }
}

sealed class Block(val id: Int) {
    class EMPTY : Block(-1)
    data class File(val fileId: Int) : Block(fileId)
}

fun List<Block>.log() {
    val sb = StringBuilder()
    this.forEach { block ->
        when (block) {
            is Block.File -> sb.append("[${block.fileId}]")
            is Block.EMPTY -> sb.append(".")
        }
    }
    println(sb.toString())
}


