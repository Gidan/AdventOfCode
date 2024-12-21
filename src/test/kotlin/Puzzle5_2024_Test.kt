import aoc_2024.Puzzle5
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class Puzzle5_2024_Test {

    @Test
    fun fixUpdateTest() {

        val puzzle = Puzzle5()
        val input = puzzle.getInput()
        puzzle.processInput(input)

        assertEquals(listOf(97,75,47,61,53), puzzle.fixUpdate(listOf(75,97,47,61,53)))
        assertEquals(listOf(97,75,47,29,13), puzzle.fixUpdate(listOf(97,13,75,29,47)))
        assertEquals(listOf(61,29,13), puzzle.fixUpdate(listOf(61,13,29)))
    }

}

