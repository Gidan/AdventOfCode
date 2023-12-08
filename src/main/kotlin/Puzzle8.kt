import util.lcm

fun main() {
    Puzzle8().solve()
}

class Puzzle8 : Puzzle() {

    override fun getPuzzleNumber() = 8

    private val nodeMap = mutableMapOf<String, Node>()
    private lateinit var instructions: String

    override fun solution() {
        val input = getInput()
        instructions = input[0]

        for (i in 2..<input.size) {
            val node = parseNode(input[i])
            nodeMap[node.label] = node
        }

        part1()
        part2()
    }

    private fun part1() {
        if (nodeMap.containsKey("AAA")) {
            var currentNode = nodeMap["AAA"]!!
            var steps = 0
            var directionIndex = -1
            while (currentNode.label != "ZZZ") {
                steps++
                directionIndex = (directionIndex + 1) % instructions.length
                val d = instructions[directionIndex]

                when (d) {
                    'L' -> currentNode = nodeMap[currentNode.left]!!
                    'R' -> currentNode = nodeMap[currentNode.right]!!
                }
            }

            println("part1 total:$steps")
        }

    }

    private fun part2() {
        var currentNodes = nodeMap.keys.filter { k -> k.endsWith("A") }.map { k -> nodeMap[k]!! }
        val steps = currentNodes.map { n ->
            var currentNode = n
            var steps = 0uL
            var directionIndex = -1
            while (!currentNode.label.endsWith('Z')) {
                steps++
                directionIndex = (directionIndex + 1) % instructions.length
                val d = instructions[directionIndex]

                when (d) {
                    'L' -> currentNode = nodeMap[currentNode.left]!!
                    'R' -> currentNode = nodeMap[currentNode.right]!!
                }
            }
            steps
        }.lcm()

        println("part2 total:$steps")
    }

    private fun parseNode(str: String): Node {
        val tokens = str.split(" = ")
        val label = tokens[0]
        val left = tokens[1].split(",")[0].removePrefix("(").trim()
        val right = tokens[1].split(",")[1].removePrefix(",").removeSuffix(")").trim()
        return Node(label, left, right)
    }
}

data class Node(val label: String, val left: String, val right: String)

