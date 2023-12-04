import java.io.File

class Util {
    companion object {
        fun getInput(puzzleNumber : Int): List<String> {
            val path = Util::class.java.getResource("$puzzleNumber/input.txt")
            val lines = mutableListOf<String>()
            path?.let {
                val filePath = it.path
                lines.addAll(File(filePath).readLines())
            }
            return lines
        }
    }

}

