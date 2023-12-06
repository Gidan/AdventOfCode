class Puzzle6 : Puzzle() {

    override fun getPuzzleNumber() = 6

    override fun solution() {
        val input = getInput()
        val timesAsString = input[0].removePrefix("Time:")
            .trimStart()
            .split(" ")
            .filter { it.isNotEmpty() }
        val distancesAsString =
            input[1].removePrefix("Distance:")
                .trimStart()
                .split(" ")
                .filter { it.isNotEmpty() }

        val times = timesAsString.map{it.toULong()}
        val distances = distancesAsString.map{it.toULong()}
        println("input times:$times")
        println("input distances:$distances")

        val timesJoined = timesAsString.joinToString(" ").replace(" ", "").toULong()
        val distancesJoined = distancesAsString.joinToString(" ").replace(" ", "").toULong()
        println("input joined times:$timesJoined")
        println("input joined distances:$distancesJoined")

        val waysToWin = mutableListOf<ULong>()

        var raceIndex = 0
        while (raceIndex < times.size) {
            val raceTime = times[raceIndex]
            val recordDistance = distances[raceIndex]
            waysToWin.add(waysToWinRace(raceTime, recordDistance))
            raceIndex++
        }

        println("part1: ${waysToWin.reduce { acc, w -> acc * w }}")

        val waysToWinRace = waysToWinRace(timesJoined, distancesJoined)
        println("part2: $waysToWinRace")
    }

    private fun waysToWinRace(raceTime: ULong, recordDistance: ULong): ULong {
        var waysToWinThisRace = 0uL

        for (pushTime in 0uL..raceTime) {
            val distance = pushTime * (raceTime - pushTime)
            if (distance > recordDistance) {
                waysToWinThisRace++
            }
        }

        return waysToWinThisRace
    }
}

fun main() {
    Puzzle6().solve()
}