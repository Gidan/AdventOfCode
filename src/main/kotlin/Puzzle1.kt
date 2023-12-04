import java.io.File

fun main() {
    Puzzle1().solve()
}

class Puzzle1 {

    fun solve(){
        println("AdventOfCode 2023 puzzle #1")
        val lines = Util.getInput(1)
        var part1Sum = 0
        var part2Sum = 0

        var firstDigitIndex = Int.MAX_VALUE
        var lastDigitIndex = -1
        var firstDigit = 0
        var lastDigit = 0

        val numbers = listOf(
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine"
        )

        for (line in lines) {
            //println(line)
            var part1LineNumber = 0
            var part2LineNumber = 0
            for ((i, c) in line.withIndex()) {
                if (c.isDigit()) {
                    firstDigitIndex = i
                    firstDigit = c.digitToInt()
                    part1LineNumber += 10 * firstDigit
                    break
                }
            }
            for (i in line.length -1 downTo 0) {
                if (line[i].isDigit()) {
                    lastDigitIndex = i
                    lastDigit = line[i].digitToInt()
                    part1LineNumber += lastDigit
                    break
                }
            }

            for ((n, word) in numbers.withIndex()) {
                val wordIndex = line.indexOf(word)
                if (wordIndex != -1 && wordIndex < firstDigitIndex) {
                    firstDigitIndex = wordIndex
                    firstDigit = n + 1
                }

                val lastWordIndex = line.lastIndexOf(word)
                if (lastWordIndex != -1 && lastWordIndex > lastDigitIndex) {
                    lastDigitIndex = lastWordIndex
                    lastDigit = n + 1
                }
            }

            part2LineNumber = firstDigit * 10 + lastDigit
            part1Sum += part1LineNumber
            part2Sum += part2LineNumber
        }

        println("p1: $part1Sum")
        println("p2: $part2Sum")
    }


}