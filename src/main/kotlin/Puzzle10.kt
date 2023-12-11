import util.*

class Puzzle10 : Puzzle() {
    override fun getPuzzleNumber() = 10

    private val pipeMap = mapOf(
        'F' to '╔',
        '7' to '╗',
        '-' to '═',
        '|' to '║',
        'L' to '╚',
        'J' to '╝',
        '.' to '.',
        'S' to 'S'
    )

    private val directionsMap = mapOf(
        'F' to Pair(Vec2D(1, 0), Vec2D(0, 1)),
        '7' to Pair(Vec2D(0, 1), Vec2D(-1, 0)),
        '-' to Pair(Vec2D(1, 0), Vec2D(-1, 0)),
        '|' to Pair(Vec2D(0, -1), Vec2D(0, 1)),
        'L' to Pair(Vec2D(0, -1), Vec2D(1, 0)),
        'J' to Pair(Vec2D(-1, 0), Vec2D(0, -1)),
        '.' to Pair(Vec2D(0, 0), Vec2D(0, 0))
    )

    private val separator = "_".repeat(100)

    private fun highlightStart(l: String): String {
        if (l.contains('S')) {
            return l.replace("S", "${Colors.CYAN}S${Colors.RESET}")
        }
        return l
    }

    override fun solution() {
        val input = getInput()
        lateinit var start: Vec2D
        val printableGrid = mutableListOf<String>()
        input.forEachIndexed { index, line ->
            if (line.contains("S")) {
                val c = line.indexOf('S')
                start = Vec2D(c, index)
            }
            val l = line.map { c -> pipeMap[c] }.joinToString(separator = "")
            printableGrid.add(l)
            logln(highlightStart(l))
        }

        logln(separator)

        var i1 = start.copy()
        var i2 = start.copy()
        var i1prev = start.copy()
        var i2prev = start.copy()
        var loopClosed = false
        var steps = 0

        val perimiter = mutableSetOf<Vec2D>()
        perimiter.add(start)

        while (!loopClosed) {
            val i1move = checkDirections(i1, i1prev, true, input)
            val i2move = checkDirections(i2, i2prev, false, input)
            i1prev = i1.copy()
            i2prev = i2.copy()
            i1 += i1move
            i2 += i2move
            perimiter.add(i1)
            perimiter.add(i2)
            if (i1 == i2) {
                loopClosed = true
            }

            steps++
            printGrid(printableGrid, i1, i2)
            logln(separator)
        }

        printGridArea(printableGrid, setOf(), perimiter)

        //calc path area
        var inside = 0
        input.forEachIndexed { lineIndex, line ->
            var bordersCount = 0
            var lineArea = 0
            val sanitizedLine = line.replace("L-*7".toRegex()) {
                "|${" ".repeat(it.value.length - 1)}"
            }.replace("F-*J".toRegex()) {
                "|${" ".repeat(it.value.length - 1)}"
            }

            sanitizedLine.forEachIndexed { charIndex, char ->
                val pos = Vec2D(charIndex, lineIndex)
                if (pos !in perimiter) {
                    if ((bordersCount / 2) * 2 != bordersCount) {
                        inside++
                        lineArea++
                    }
                } else {
                    if (char == '|') {
                        bordersCount++
                    }
                }
            }
            logln("$sanitizedLine : $lineArea")
        }

        println("part1 steps: $steps")
        println("part2 insideArea: $inside")
    }

    private fun printGridArea(grid: List<String>, visited: Set<Vec2D>, perimeter: Set<Vec2D>) {
        if (LOG) {
            grid.forEachIndexed { lineIndex, line ->
                line.forEachIndexed { charIndex, c ->
                    val element = Vec2D(charIndex, lineIndex)
                    if (visited.contains(element)) {
                        log("${Colors.BLUE}$c${Colors.RESET}")
                    } else if (perimeter.contains(element)) {
                        log("${Colors.YELLOW}$c${Colors.RESET}")
                    } else {
                        log(c.toString())
                    }
                }
                logln()
            }
            logln()
        }
    }

    private fun printGrid(grid: List<String>, i1: Vec2D, i2: Vec2D) {
        if (LOG) {
            grid.forEachIndexed { lineIndex, line ->
                var l = line

                if (i1.y != i2.y) {
                    //different lines
                    if (lineIndex == i1.y) {
                        l = l.replaceRange(i1.x..<i1.x + 1, "${Colors.RED}${l[i1.x]}${Colors.RESET}")
                    }
                    if (lineIndex == i2.y) {
                        l = l.replaceRange(i2.x..<i2.x + 1, "${Colors.YELLOW}${l[i2.x]}${Colors.RESET}")
                    }
                } else {
                    //same line
                    if (lineIndex == i1.y) {
                        if (i1.x > i2.x) {
                            l = l.replaceRange(i1.x..<i1.x + 1, "${Colors.RED}${l[i1.x]}${Colors.RESET}")
                            l = l.replaceRange(i2.x..<i2.x + 1, "${Colors.YELLOW}${l[i2.x]}${Colors.RESET}")
                        } else if (i1.x < i2.x) {
                            l = l.replaceRange(i2.x..<i2.x + 1, "${Colors.YELLOW}${l[i2.x]}${Colors.RESET}")
                            l = l.replaceRange(i1.x..<i1.x + 1, "${Colors.RED}${l[i1.x]}${Colors.RESET}")
                        } else {
                            l = l.replaceRange(i1.x..<i1.x + 1, "${Colors.GREEN}${l[i1.x]}${Colors.RESET}")
                        }
                    }
                }

                logln(highlightStart(l))
            }
        }
    }

    private fun identifyStart(start: Vec2D, grid: List<String>) : Pair<Vec2D, Vec2D> {
        val openings = mutableListOf<Vec2D>()

        //check top
        if (start.y > 0) {
            if (listOf('|', '7', 'F').any { it == grid[start.y - 1][start.x] }) {
                openings.add(Vec2D(0, -1))
            }
        }
        //check right
        if (start.x < grid[0].length - 1) {
            if (listOf('-', '7', 'J').any { it == grid[start.y][start.x + 1] }) {
                openings.add(Vec2D(1, 0))
            }
        }
        //check bottom
        if (start.y < grid.size - 1) {
            if (listOf('|', 'J', 'L').any { it == grid[start.y + 1][start.x] }) {
                openings.add(Vec2D(0, 1))
            }
        }
        //check left
        if (start.x > 0) {
            if (listOf('-', 'L', 'F').any { it == grid[start.y][start.x - 1] }) {
                openings.add(Vec2D(-1, 0))
            }
        }

        return Pair(openings[0], openings[1])
    }

    private fun checkDirections(curPos: Vec2D, prevPos: Vec2D, clockwise: Boolean, grid: List<String>): Vec2D {
        val c = grid[curPos.y][curPos.x]
        return if (c == 'S') {
            val openings = identifyStart(curPos, grid)
            if (clockwise) openings.first else openings.second
        } else {
            val directions = directionsMap[c]
            if (clockwise) {
                if (directions!!.first + curPos != prevPos) directions.first else directions.second
            } else {
                if (directions!!.second + curPos != prevPos) directions.second else directions.first
            }
        }
    }
}

fun main() {
    Puzzle10().solve()
}
