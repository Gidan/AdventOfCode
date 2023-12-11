package util

const val LOG = true

fun log(text: String) {
    if (LOG) print(text)
}

fun logln(text: String) {
    if (LOG) println(text)
}

fun logln(obj: Any) {
    if (LOG) println(obj)
}

fun logln() {
    if (LOG) println()
}