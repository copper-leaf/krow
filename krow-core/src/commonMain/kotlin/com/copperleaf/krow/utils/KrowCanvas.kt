package com.copperleaf.krow.utils

interface Drawable {
    fun draw(canvas: KrowCanvas, borderSet: BorderSet)
}

class KrowCanvas private constructor(
    val buffer: MutableList<String>
) {

    constructor(width: Int, height: Int) : this(
        createLinesOf(
            createLineOf(' ', width),
            height
        ).toMutableList()
    )

    override fun toString(): String {
        return buffer.joinToString(separator = "\n")
    }

    /**
     * Overwrites a part of the specified line, starting from the specified index, with another string.
     */
    private fun draw(line: Int, startColumn: Int, replacement: String) {
//        try {
            buffer[line] = buffer[line].replaceRange(startColumn, startColumn + replacement.length, replacement)
//        }
//        catch (e: Exception) {
//            println("Crash attempting to draw '$replacement' at (line=$line, column=$startColumn):")
//            println(this)
//        }
    }

    fun drawHorizontalLine(
        line: Int,
        column: Int,
        length: Int,
        horizontal: Char,
    ) {
        draw(
            line,
            column,
            createLineOf(horizontal, length)
        )
    }

    fun drawVerticalLine(
        line: Int,
        column: Int,
        height: Int,
        vertical: Char,
    ) {
        repeat(height) {
            draw(line + it, column, vertical.toString())
        }
    }

    fun drawText(
        bufferLine: Int,
        bufferColumn: Int,
        text: String
    ) {
        text.lines().forEachIndexed { i, s ->
            draw(bufferLine + i, bufferColumn, s)
        }
    }

    fun drawPoint(
        line: Int,
        column: Int,
        point: Char,
    ) {
        draw(line, column, point.toString())
    }
}
