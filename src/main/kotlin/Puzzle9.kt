class Puzzle9 : Puzzle() {
    override fun getPuzzleNumber() = 9

    override fun solution() {
        val input = getInput()
        val numbersList = input.map { line ->
            val map = line.split(" ").map { n -> n.toInt() }
            map
        }

        logln(numbersList)

        val part1Sum = numbersList.map { nList ->
            findNextValue(nList)
        }.reduce { acc, i -> acc + i }
        println(part1Sum)

        val part2Sum = numbersList.map { nList ->
            findPreviousValue(nList)
        }.reduce { acc, i -> acc + i }
        println(part2Sum)
    }

    private fun findNextValue(list : List<Int>) : Int {
        val differences = mutableListOf<List<Int>>()
        differences.add(list)
        do {
            val currentList = differences[differences.size-1]
            val mutList = mutableListOf<Int>()
            for (i in 0..<currentList.size-1) {
                mutList.add(currentList[i+1]-currentList[i])
            }
            differences.add(mutList)
        } while (!differences[differences.size-1].all { it == 0 })

        val nextNumber = differences.map { it[it.size - 1] }.reduce { acc, i -> acc + i }
        return nextNumber
    }

    private fun findPreviousValue(list : List<Int>) : Int {
        val differences = mutableListOf<List<Int>>()
        differences.add(list)
        do {
            val currentList = differences[differences.size-1]
            val mutList = mutableListOf<Int>()
            for (i in 0..<currentList.size-1) {
                mutList.add(currentList[i+1]-currentList[i])
            }
            differences.add(mutList)
        } while (!differences[differences.size-1].all { it == 0 })

        val firstNumbers = differences.map { it[0] }
        var previousNumber = firstNumbers[firstNumbers.size-1]
        for (i in firstNumbers.size-2 downTo 0 ) {
            previousNumber=firstNumbers[i] - previousNumber
        }

        return previousNumber
    }


}

fun main() {
    Puzzle9().solve()
}