import util.Vec2D
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

class Puzzle10Test {

    @Test
    fun vector_equality_test() {
        assert(Vec2D(0,0) == Vec2D(0,0))
        assertNotSame(Vec2D(0,0), Vec2D(0,0))
    }

    @Test
    fun vector_set_test() {
        val pathPoints = mutableSetOf<Vec2D>()
        pathPoints.add(Vec2D(0,0))
        pathPoints.add(Vec2D(1,0))
        assertEquals(2, pathPoints.size)
        pathPoints.add(Vec2D(1,0))
        assertEquals(2, pathPoints.size)
    }




}

