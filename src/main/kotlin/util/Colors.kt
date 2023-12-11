package util

enum class Colors(
    private val code: String
) {

    RESET("\u001B[0m"),

    RED("\u001B[0;31m"),

    GREEN("\u001B[0;32m"),

    YELLOW("\u001B[33m"),

    BLUE("\u001B[0;34m"),

    MAGENTA("\u001B[0;35m"),

    CYAN("\u001B[0;96m"),

    WHITE("\u001B[0;37m");

    override fun toString(): String {
        return code
    }
}