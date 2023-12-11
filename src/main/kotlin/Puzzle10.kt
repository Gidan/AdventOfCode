class Puzzle10 : Puzzle() {
    override fun getPuzzleNumber() = 10

    val pipeMap = mapOf(
        'F' to '╔',
        '7' to '╗',
        '-' to '═',
        '|' to '║',
        'L' to '╚',
        'J' to '╝',
        '.' to '.',
        'S' to 'S'
    )

    val moveMap = mapOf(
        'F' to '╔',
        '7' to '╗',
        '-' to '═',
        '|' to '║',
        'L' to '╚',
        'J' to '╝',
        '.' to '.',
        'S' to 'S'
    )

    override fun solution() {
        val input = getInput()
        lateinit var start : Pair<Int, Int>
        input.forEachIndexed { index, line ->
            if (line.contains("S")) {
                val c = line.indexOf('S')
                start = Pair(index, c)
            }
            val l = line.map { c -> pipeMap[c] }.joinToString(separator = "")
            println(l)
        }

        var i1 = start.copy()
        var i2 = start.copy()
        var loopClosed = false
        var steps = 0
        while (!loopClosed) {
            val i1c = input[i1.first][i1.second]
            val i2c = input[i2.first][i2.second]
            i1 = move(i1c)
            steps++
        }
    }

    private fun move(c: Char): Pair<Int, Int> {
//        when (c) {
//            'S' -> {
//
//            }
//            '.' -> {
//
//            }
//            '-' -> {
//
//            }
//        }
        return Pair(0,0)
    }
}

fun main() {
    Puzzle10().solve()
}
