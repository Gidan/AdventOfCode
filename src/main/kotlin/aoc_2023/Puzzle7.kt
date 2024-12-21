package aoc_2023

import Puzzle
import Solution
import util.logln
import kotlin.math.sign

fun main() {
    Puzzle7().solve()
}

class Puzzle7 : Puzzle() {
    override fun getPuzzleNumber() = 7

    private val hands = mutableListOf<Hand>()
    private val jokerHands = mutableListOf<JokerHand>()

    override fun solution() : Solution {
        readHands()

        var totalWin = 0
        var totalWinWj = 0

        val sortedHands = hands.sortedWith { h1, h2 -> h1.compareTo(h2) }
        val sortedHandsWj = jokerHands.sortedWith { h1, h2 -> h1.compareTo(h2) }

        for (i in sortedHands.indices) {
            totalWin += sortedHands[i].bid * (i + 1)
            totalWinWj += sortedHandsWj[i].bid * (i + 1)
        }

        return Solution(totalWin, totalWinWj)
    }

    private fun readHands() {
        val input = getInput()
        input.forEach { line ->
            val tokens = line.split(" ")
            val bid = tokens[1].toInt()
            hands.add(Hand(tokens[0], bid))
            jokerHands.add(JokerHand(tokens[0], bid))
        }
    }
}

open class Hand(private val hand: String, val bid: Int = 0) : Comparable<Hand> {

    companion object {
        const val CARDS = "23456789TJQKA"
    }

    open val sorted: String = hand.toCharArray().sortedArray().joinToString(separator = "")
    val charMap = mutableMapOf<Char, Int>()

    val type: Type by lazy {
        type()
    }

    init {
        for (c in hand) {
            val current = charMap.getOrPut(c) { 0 }
            charMap[c] = current + 1
        }
    }

    enum class Type {
        HIGH_CARD, // 1
        PAIR, // 2
        TWO_PAIR, // 2 + 2
        THREE_OF_A_KIND, // 3
        FULL_HOUSE, // 3 + 2
        FOUR_OF_A_KIND, // 4
        FIVE_OF_A_KIND, // 5
    }

    open fun type(): Type {
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

    override fun toString(): String {
        return "($hand sorted:$sorted type:${type.name} bid:$bid)"
    }

    protected open fun cardsOrdering(): String {
        return CARDS
    }

    override fun compareTo(other: Hand): Int {
        if (other.sorted == this.sorted) return 0

        if (other.type == this.type) {
            //check cards
            for (i in 0..4) {
                val otherCvalue = cardsOrdering().indexOf(other.hand[i])
                val thisCvalue = cardsOrdering().indexOf(this.hand[i])
                if (otherCvalue != thisCvalue) {
                    return (thisCvalue - otherCvalue).sign
                }
            }
        } else {
            return (this.type.ordinal - other.type.ordinal).sign
        }

        return 0
    }


}

class JokerHand(val hand: String, bid: Int = 0) : Hand(hand, bid) {

    companion object {
        const val CARDS = "J23456789TQKA"
        private val JOKER_COMPARATOR: Comparator<Char> = Comparator { c1, c2 ->
            when {
                c1 == c2 -> 0
                c1 == 'J' -> -1
                c2 == 'J' -> 1
                else -> {
                    (c1 - c2).sign
                }
            }
        }
    }

    override val sorted: String =
        hand.toCharArray().sortedWith(JOKER_COMPARATOR).joinToString(separator = "")

    override fun cardsOrdering(): String {
        return CARDS
    }

    override fun type(): Type {
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
                if (charMap.containsKey('J'))
                    Type.THREE_OF_A_KIND
                else
                    Type.PAIR
            }

            else -> {
                Type.HIGH_CARD
            }
        }
    }


}

