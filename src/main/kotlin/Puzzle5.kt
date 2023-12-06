import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

fun main() {
    Puzzle5().solve()
}

class Puzzle5 : Puzzle() {

    override fun getPuzzleNumber() = 5

    private lateinit var seeds: List<ULong>
    private var seedsRanges = mutableListOf<ULongRange>()

    private val seedToSoil = mutableListOf<Range>()
    private val soilToFertilizer = mutableListOf<Range>()
    private val fertilizerToWater = mutableListOf<Range>()
    private val waterToLight = mutableListOf<Range>()
    private val lightToTemperature = mutableListOf<Range>()
    private val temperatureToHumidity = mutableListOf<Range>()
    private val humidityToLocation = mutableListOf<Range>()

    private val maps = mapOf(
        "seed-to-soil" to seedToSoil,
        "soil-to-fertilizer" to soilToFertilizer,
        "fertilizer-to-water" to fertilizerToWater,
        "water-to-light" to waterToLight,
        "light-to-temperature" to lightToTemperature,
        "temperature-to-humidity" to temperatureToHumidity,
        "humidity-to-location" to humidityToLocation,
    )

    private val headerRegex = "^([a-z-]+) map:$".toRegex()
    private val dataLineRegex = "^[0-9 ]+$".toRegex()

    override fun solution() {
        val input = getInput()

        val inputHeight = input.size
        var lineIdx = 0

        lateinit var currentRangeList: MutableList<Range>

        while (lineIdx < inputHeight) {
            val line = input[lineIdx]
            when {
                line.startsWith("seeds") -> {
                    seeds = line.removePrefix("seeds: ").toULongList()
                    for (seedIdx in seeds.indices step 2) {
                        val start = seeds[seedIdx]
                        val len = seeds[seedIdx + 1]
                        seedsRanges.add(start..<start + len)
                    }
                    println("seeds: $seeds")
                }

                headerRegex.matches(line) -> {
                    val matchAt = headerRegex.matchAt(line, 0)
                    matchAt?.groups?.get(0)?.let {
                        val mapName = it.value.removeSuffix(" map:")
                        println(mapName)
                        currentRangeList = maps[mapName]!!
                    }
                }

                line.isEmpty() -> {
                    println()
                }

                dataLineRegex.matches(line) -> {
                    val numbers = line.toULongList()
                    val destinationStart = numbers[0]
                    val sourceStart = numbers[1]
                    val rangeLen = numbers[2]
                    val range = Range(sourceStart, destinationStart, rangeLen)
                    println("$destinationStart $sourceStart $rangeLen = $range")
                    currentRangeList.add(range)
                }
            }

            lineIdx++
        }
        println()

        seeds.minOfOrNull { seed ->
            processSeed(seed)
        }.let { min ->
            println("lowest location: $min")
        }

        val mutex = Mutex()
        val locations = mutableListOf<ULong>()
        runBlocking {
            withContext(Dispatchers.Default) {
                seedsRanges.forEach { range ->
                    launch {
                        processRange(range) {
                            mutex.withLock {
                                locations.add(it)
                            }
                        }
                    }
                }
            }
        }
        println("lowest location part2: ${locations.min()}")

    }

    private suspend fun processRange(range: ULongRange, done: suspend (ULong) -> Unit) {
        for (seed in range.first..range.last) {
            val location = processSeed(seed)
            done(location)
        }
    }

    private fun processSeed(seed: ULong): ULong {
        //println("\nprocessing seed:$seed")
        var value = seed
        for ((name, rangeList) in maps.entries) {
            val start = value
            value = rangeList.firstOrNull { rangePair -> rangePair.contains(value) }?.let { rangePair ->
                rangePair.destination(value)
            } ?: value
            //println("processing:$start currentMap:$name -> $value")
        }
        return value
    }
}


data class Range(val source: ULong, val destination: ULong, val rangeLen: ULong) {
    fun contains(n: ULong): Boolean {
        return source <= n && n < (source + rangeLen)
    }

    fun destination(n: ULong): ULong {
        return destination + (n - source)
    }

    override fun toString(): String {
        return "[$source-${source + rangeLen - 1u}][$destination:${destination + rangeLen - 1u}]"
    }
}

fun String.toULongList(): List<ULong> = this.split(" ").map { it.toULong() }
