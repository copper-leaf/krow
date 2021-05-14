package com.copperleaf.krow.utils

fun createLineOf(char: Char, length: Int): String {
    return CharArray(length) { char }.concatToString()
}

fun createLinesOf(line: String, height: Int): List<String> {
    return (0 until height).map { line }
}

fun createBlankLinesOf(padChar: Char, width: Int, height: Int): List<String> {
    val blankLine = createLineOf(padChar, width)
    return createLinesOf(blankLine, height)
}
