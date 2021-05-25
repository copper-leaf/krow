package com.copperleaf.krow.formatters.ascii.internal

import com.copperleaf.krow.model.TableSpec
import com.copperleaf.krow.utils.BorderSet
import com.copperleaf.krow.utils.Drawable
import com.copperleaf.krow.utils.KrowCanvas

/**
 * Identify all unique edges of the table (line between any 2 cells, or between a cell and the edge of the table), and
 * determine the exact buffer position and context of each corner to determine the specific ASCII characters to render
 * for that line.
 */
@Suppress("UNUSED_PARAMETER")
internal fun measureEdges(
    cells: List<MeasuredCell>,
    rowSpec: TableSpec,
    colSpec: TableSpec,
): List<MeasuredEdge> {
    return cells.flatMap { measureEdges(it) }
}

private fun measureEdges(
    cell: MeasuredCell
): List<MeasuredEdge> {
    return with(cell) {
        val topLine = bufferLine
        val bottomLine = bufferLine + height + 1
        val leftColumn = bufferColumn
        val rightColumn = bufferColumn + width + 1

        listOf(
            MeasuredEdge.VerticalEdge(
                row = topLine + 1,
                column = leftColumn,
                height = height,
                char = { vl }
            ),
            MeasuredEdge.VerticalEdge(
                row = topLine + 1,
                column = rightColumn,
                height = height,
                char = { vr }
            ),
            MeasuredEdge.HorizontalEdge(
                row = topLine,
                column = leftColumn + 1,
                width = width,
                char = { th }
            ),
            MeasuredEdge.HorizontalEdge(
                row = bottomLine,
                column = leftColumn + 1,
                width = width,
                char = { bh }
            )
        )
    }
}

internal sealed class MeasuredEdge : Drawable {
    internal class VerticalEdge(
        val row: Int,
        val column: Int,
        val height: Int,
        val char: BorderSet.() -> Char,
    ) : MeasuredEdge() {
        override fun draw(canvas: KrowCanvas, borderSet: BorderSet) {
            canvas.drawVerticalLine(row, column, height, borderSet.char())
        }
    }

    internal data class HorizontalEdge(
        val row: Int,
        val column: Int,
        val width: Int,
        val char: BorderSet.() -> Char,
    ) : MeasuredEdge() {
        override fun draw(canvas: KrowCanvas, borderSet: BorderSet) {
            canvas.drawHorizontalLine(
                row,
                column,
                width,
                borderSet.char(),
            )
        }
    }
}
