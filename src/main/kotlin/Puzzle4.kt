import kotlin.math.pow

fun main() {
    Puzzle4().solve()
}

class Puzzle4 {

    fun solve() {
        println("AdventOfCode 2023 puzzle #4")
        val cards = Util.getInput(4)
        var totalScore = 0
        var cardsCount = 0
        for (card in cards) {
            totalScore += checkTicket(card)
            val cardIdx = cards.indexOf(card)
            cardsCount++
            cardsCount += countCards(card, cardIdx, cards)
        }

        println("totalScore: $totalScore")
        println("totalCards: $cardsCount")
    }

    private val cardRegex = "^Card +[0-9]+: +([0-9 ]+) [|] +([0-9 ]+)\$".toRegex()
    private val splitter = "\\D+".toRegex()

    private val cardMap = mutableMapOf<String, List<Int>>()

    private fun checkTicket(card: String): Int {
        println(card)
        val matchingNumbers = matchingNumbers(card)
        return 2.0.pow(matchingNumbers.size - 1).toInt()
    }

    private fun matchingNumbers(card: String): List<Int> {
        if (cardMap.containsKey(card)) {
            return cardMap[card]!!
        }
        val matchEntire = cardRegex.matchEntire(card)
        assert(matchEntire?.groupValues?.size == 3)

        val winningNumbers = matchEntire?.groupValues?.get(1)?.split(splitter)?.map { it.toInt() } ?: listOf()
        val myNumbers = matchEntire?.groupValues?.get(2)?.split(splitter)?.map { it.toInt() } ?: listOf()

        val myNumbersSrt = myNumbers.sorted()
        val winningNumbersSrt = winningNumbers.sorted()

        val matchingNumbers = mutableListOf<Int>()

        for (winningNumberIdx in winningNumbersSrt.indices) {
            val winningNumber = winningNumbersSrt[winningNumberIdx]
            var myNumberIdx = 0
            while (myNumberIdx < myNumbers.size) {
                val myNumber = myNumbersSrt[myNumberIdx]
                if (myNumber == winningNumber) {
                    matchingNumbers.add(myNumber)
                    break
                }
                myNumberIdx++
            }
        }

        cardMap[card] = matchingNumbers

        return matchingNumbers
    }

    private fun countCards(card: String, cardIdx: Int, cards: List<String>): Int {
        val matchingNumbers = matchingNumbers(card)
        var count = matchingNumbers.size
        for (i in (cardIdx + 1)..cardIdx + matchingNumbers.size) {
            val nextCard = cards[i]
            count += countCards(nextCard, i, cards)
        }
        return count
    }


}
