import kotlin.math.sign

fun main() {
    Puzzle7().solve()
}

class Puzzle7 : Puzzle() {
    override fun getPuzzleNumber() = 7

    val handMap = mutableListOf<Hand>()

    override fun solution() {
        readHands()

        var totalWin = 0uL
        var totalWinWj = 0uL

        val sortedHands = handMap.sortedWith { h1, h2 -> h1.compareTo(h2) }
        val sortedHandsWj = handMap.sortedWith { h1, h2 -> h1.compareToWj(h2) }
        logln("sorted")
        logln(sortedHands.joinToString(separator = "\n"))
        logln("sortedWj")
        logln(sortedHandsWj.joinToString(separator = "\n"))

        for (i in sortedHands.indices) {
            totalWin += sortedHands[i].bid.toUInt() * (i.toUInt() + 1u)
            totalWinWj += sortedHandsWj[i].bid.toUInt() * (i.toUInt() + 1u)
        }

        println("part1: $totalWin")
        println("part2: $totalWinWj")

    }

    private fun readHands() {
        val input = getInput()
        for (line in input) {
            val tokens = line.split(" ")
            val bid = tokens[1].toInt()
            val hand = Hand(tokens[0], bid)
            handMap.add(hand)
            logln(hand)
        }
    }
}

class Hand(val hand: String, val bid: Int = 0) : Comparable<Hand> {

    companion object {
        private const val cards = "23456789TJQKA"
        private const val cardsWj = "J23456789TQKA"
    }

    val sorted: String
    val sortedWj: String
    val charMap = mutableMapOf<Char, Int>()


    val type: Type
    val typeWj: Type

    init {
        sorted = hand.toCharArray().sortedArray().joinToString(separator = "")
        sortedWj = hand.toCharArray().sortedWith { c1, c2 -> sortCardWithJack(c1, c2) }.joinToString(separator = "")

        for (c in hand) {
            val current = charMap.getOrPut(c) { 0 }
            charMap[c] = current + 1
        }

        type = type()
        typeWj = typeWithJack()
    }

    enum class Type {
        HIGH_CARD, // 1
        PAIR, // 2
        TWO_PAIR, // 2+2
        THREE_OF_A_KIND, // 3
        FULL_HOUSE, // 3 + 2
        FOUR_OF_A_KIND, //4
        FIVE_OF_A_KIND, //5
    }

    private fun sortCardWithJack(c1: Char, c2: Char): Int {
        if (c1 == c2) return 0
        if (c1 == 'J') return -1
        if (c2 == 'J') return 1
        return (c1 - c2).sign
    }

    private fun type(): Type {
        return when {
            charMap.values.size == 1 -> Type.FIVE_OF_A_KIND
            charMap.values.any { v -> v == 4 } -> Type.FOUR_OF_A_KIND
            charMap.values.size == 5 -> Type.HIGH_CARD
            charMap.values.any { v -> v == 3 } && charMap.values.any { v -> v == 2 } -> Type.FULL_HOUSE
            charMap.values.any { v -> v == 3 } -> Type.THREE_OF_A_KIND
            charMap.values.filter { v -> v == 2 }.size == 2 -> Type.TWO_PAIR
            charMap.values.any { v -> v == 2 } -> Type.PAIR
            else -> {
                Type.HIGH_CARD
            }
        }
    }

    private fun typeWithJack(): Type {
        return when {
            charMap.values.size == 1 -> Type.FIVE_OF_A_KIND
            charMap.values.any { v -> v == 4 } -> {
                if (charMap.containsKey('J')) {
                    Type.FIVE_OF_A_KIND
                } else {
                    Type.FOUR_OF_A_KIND
                }
            }

            charMap.values.size == 5 -> {
                if (charMap.containsKey('J')) {
                    Type.PAIR
                } else {
                    Type.HIGH_CARD
                }
            }

            charMap.values.any { v -> v == 3 } && charMap.values.any { v -> v == 2 } -> {
                if (charMap.containsKey('J')) {
                    Type.FIVE_OF_A_KIND
                } else {
                    Type.FULL_HOUSE
                }
            }

            charMap.values.any { v -> v == 3 } -> {
                if (charMap.containsKey('J')) {
                    Type.FOUR_OF_A_KIND
                } else {
                    Type.THREE_OF_A_KIND
                }
            }

            charMap.values.filter { v -> v == 2 }.size == 2 -> {
                if (charMap.containsKey('J')) {
                    if (charMap['J'] == 2) {
                        Type.FOUR_OF_A_KIND
                    } else {
                        Type.FULL_HOUSE
                    }
                } else {
                    Type.TWO_PAIR
                }
            }

            charMap.values.any { v -> v == 2 } -> {
                if (charMap.containsKey('J')) {
                    Type.THREE_OF_A_KIND
                } else {
                    Type.PAIR
                }
            }

            else -> {
                Type.HIGH_CARD
            }
        }
    }

    override fun toString(): String {
        return "($hand sorted:$sorted type:${type.name} bid:$bid)"
    }

    override fun compareTo(other: Hand): Int {
        if (other.sorted == this.sorted) return 0

        if (other.type == this.type) {
            //check cards
            for (i in 0..4) {
                val otherCvalue = cards.indexOf(other.hand[i])
                val thisCvalue = cards.indexOf(this.hand[i])
                if (otherCvalue != thisCvalue) {
                    return (thisCvalue - otherCvalue).sign
                }
            }
        } else {
            return (this.type.ordinal - other.type.ordinal).sign
        }

        return 0
    }

    fun compareToWj(other: Hand): Int {
        if (other.sortedWj == this.sortedWj) return 0

        if (other.typeWj == this.typeWj) {
            //check cards
            for (i in 0..4) {
                val otherCvalue = cardsWj.indexOf(other.hand[i])
                val thisCvalue = cardsWj.indexOf(this.hand[i])
                if (otherCvalue != thisCvalue) {
                    return (thisCvalue - otherCvalue).sign
                }
            }
        } else {
            return (this.typeWj.ordinal - other.typeWj.ordinal).sign
        }

        return 0
    }



}

