import kotlin.test.Test
import kotlin.test.assertEquals

class Puzzle7Test {

    @Test
    fun sorting_hand() {
        assertEquals("AAAQT", Hand("AAQTA").sorted)
        assertEquals("2345A", Hand("A2345").sorted)
        assertEquals("AAAAT", Hand("TAAAA").sorted)
        assertEquals("23AQT", Hand("23TQA").sorted)
        assertEquals("45AKT", Hand("T54KA").sorted)
        assertEquals("45JKT", Hand("T54KJ").sorted)

        assertEquals("AAAQT", Hand("AAQTA").sortedWj)
        assertEquals("2345A", Hand("A2345").sortedWj)
        assertEquals("AAAAT", Hand("TAAAA").sortedWj)
        assertEquals("23AQT", Hand("23TQA").sortedWj)
        assertEquals("45AKT", Hand("T54KA").sortedWj)
        assertEquals("J45KT", Hand("T54KJ").sortedWj)
    }

    @Test
    fun charMap_test() {
        val hand = Hand("AAQTA")
        assertEquals(3, hand.charMap['A'])
        assertEquals(1, hand.charMap['Q'])
        assertEquals(1, hand.charMap['T'])

        val hand2 = Hand("AA233")
        assertEquals(2, hand2.charMap['A'])
        assertEquals(1, hand2.charMap['2'])
        assertEquals(2, hand2.charMap['3'])
    }

    @Test
    fun hand_test() {
        assertEquals(Hand.Type.HIGH_CARD, Hand("234TQ").type)
        assertEquals(Hand.Type.THREE_OF_A_KIND, Hand("AATA2").type)
        assertEquals(Hand.Type.FOUR_OF_A_KIND, Hand("AATAA").type)
        assertEquals(Hand.Type.PAIR, Hand("AAT23").type)
        assertEquals(Hand.Type.PAIR, Hand("A4T2A").type)
        assertEquals(Hand.Type.TWO_PAIR, Hand("AAT22").type)
        assertEquals(Hand.Type.TWO_PAIR, Hand("A2TA2").type)
        assertEquals(Hand.Type.FULL_HOUSE, Hand("AATTT").type)
        assertEquals(Hand.Type.FULL_HOUSE, Hand("ATATT").type)

        assertEquals(Hand.Type.HIGH_CARD, Hand("234TQ").typeWj)
        assertEquals(Hand.Type.PAIR, Hand("23JTQ").typeWj)
        assertEquals(Hand.Type.THREE_OF_A_KIND, Hand("23JJQ").typeWj)
        assertEquals(Hand.Type.FOUR_OF_A_KIND, Hand("23JJJ").typeWj)
        assertEquals(Hand.Type.FIVE_OF_A_KIND, Hand("2JJJJ").typeWj)
        assertEquals(Hand.Type.FIVE_OF_A_KIND, Hand("22JJJ").typeWj)
        assertEquals(Hand.Type.FOUR_OF_A_KIND, Hand("22AJJ").typeWj)
        assertEquals(Hand.Type.FULL_HOUSE, Hand("22J33").typeWj)
        assertEquals(Hand.Type.THREE_OF_A_KIND, Hand("24J33").typeWj)
        assertEquals(Hand.Type.FULL_HOUSE, Hand("22AAJ").typeWj)
        assertEquals(Hand.Type.FOUR_OF_A_KIND, Hand("2JAAA").typeWj)
    }

    @Test
    fun hand_comapre_test() {
        assertEquals(0, Hand("234TQ").compareTo(Hand("234TQ")))
        assertEquals(-1, Hand("AAA23").compareTo(Hand("AAA22")))
        assertEquals(1, Hand("AAA22").compareTo(Hand("AAA23")))
        assertEquals(1, Hand("AATA2").compareTo(Hand("AAT22")))
        assertEquals(-1, Hand("AATA2").compareTo(Hand("ATATT")))

        assertEquals(0, Hand("23JTQ").compareToWj(Hand("23JTQ")))
        assertEquals(1, Hand("33JTQ").compareToWj(Hand("23JTQ")))
        assertEquals(1, Hand("77777").compareToWj(Hand("J7777")))
        assertEquals(-1, Hand("AAJ33").compareToWj(Hand("AAA33")))
    }


}

