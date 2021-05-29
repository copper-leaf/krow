package com.copperleaf.krow.utils

fun createLineOf(char: String, length: Int): String {
    return CharArray(length).map { char }.joinToString(separator = "")
}

fun createLinesOf(line: String, height: Int): List<String> {
    return (0 until height).map { line }
}

fun createBlankLinesOf(padChar: String, width: Int, height: Int): List<String> {
    val blankLine = createLineOf(padChar, width)
    return createLinesOf(blankLine, height)
}
