fun main() {
    Puzzle2().solve()
}

class Puzzle2 : Puzzle() {

    private val gameId = "^Game ([0-9]{1,3}):".toRegex()
    private val redMatcher = "([0-9]{1,2}) red".toRegex()
    private val greenMatcher = "([0-9]{1,2}) green".toRegex()
    private val blueMatcher = "([0-9]{1,2}) blue".toRegex()

    override fun solution() {
        val lines = getInput()
        val games = lines.map {
            val id = gameId.find(it)?.groups?.get(1)?.value?.toInt() ?: -1
            val reveals = it.split(":")[1].split(";").map { r ->
                val red = redMatcher.find(r)?.groupValues?.get(1)?.toInt() ?: -1
                val green = greenMatcher.find(r)?.groupValues?.get(1)?.toInt() ?: -1
                val blue = blueMatcher.find(r)?.groupValues?.get(1)?.toInt() ?: -1
                Reveal(red, green, blue)
            }.toCollection(mutableListOf())
            val g = Game(id, reveals)
            //println("${if (g.isPossible()) "POSSIBLE" else "NOT POSSIBLE"} - $g")
            g
        }.toCollection(mutableListOf())

        val sum = games.filter { game -> game.isPossible() }.map { game -> game.id }.reduce{g1, g2 -> g1 + g2}
        println("part1: $sum")


        val totalPower = games.map { it.power() }.reduce{ g1, g2 -> g1 + g2 }
        println("part2: $totalPower")
    }

    override fun getPuzzleNumber() = 2

}

data class Game(val id: Int, val reveals: List<Reveal>) {
    private val redLimit = 12
    private val greenLimit = 13
    private val blueLimit = 14

    fun isPossible() : Boolean {
        return reveals.all {reveal ->
            (reveal.red == -1 || reveal.red <= redLimit)
                    &&
                    (reveal.green == -1 || reveal.green <= greenLimit)
                    &&
                    (reveal.blue == -1 || reveal.blue <= blueLimit)
        }
    }

    fun power() : Int {
        val maxRed = reveals.maxBy { it.red }.red
        val maxGreen = reveals.maxBy { it.green }.green
        val maxBlue = reveals.maxBy { it.blue }.blue
        return maxRed * maxGreen * maxBlue
    }
}

data class Reveal(val red: Int, val green: Int, val blue: Int)