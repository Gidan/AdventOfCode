package util

const val LOG = true

fun log(text: String) {
    if (LOG) print(text)
}

fun logln(text: String) {
    if (LOG) println(text)
}

fun logln() {
    if (LOG) println()
}