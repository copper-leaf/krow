package com.copperleaf.krow.utils

class KrowCanvas(
    val buffer: MutableList<String>
) {

    override fun toString(): String {
        return buffer.joinToString(separator = "\n")
    }

    /**
     * Overwrites a part of the specified line, starting from the specified index, with another string.
     */
    private fun draw(line: Int, startColumn: Int, replacement: String) {
        buffer[line] = buffer[line].replaceRange(startColumn, startColumn + replacement.length, replacement)
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
        val data = text.lines()
        data.forEachIndexed { i, s ->
            draw(bufferLine + i + 1, bufferColumn + 1, s)
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
