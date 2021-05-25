package com.copperleaf.krow.formatters.ascii.internal

import com.copperleaf.krow.model.TableSpec
import com.copperleaf.krow.utils.BorderSet
import com.copperleaf.krow.utils.Drawable
import com.copperleaf.krow.utils.KrowCanvas

/**
 * Identify all unique corners of the table (the corner of any cell or cells), and determine the exact buffer position
 * and context of each corner to determine the specific ASCII character to render at that corner or intersection.
 */
internal fun measureCorners(
    cells: List<MeasuredCell>,
    rowSpec: TableSpec,
    colSpec: TableSpec,
): List<MeasuredCorner> {
    return (0..rowSpec.sizes.size).flatMapIndexed { tableRowBorderIndex, _ ->
        (0..colSpec.sizes.size).mapIndexed { tableCellBorderIndex, _ ->
            measureCorner(
                cells,
                rowSpec,
                colSpec,
                tableRowBorderIndex,
                tableCellBorderIndex,
            )
        }
    }
}

private fun measureCorner(
    cells: List<MeasuredCell>,
    rowSpec: TableSpec,
    colSpec: TableSpec,
    tableRowBorderIndex: Int,
    tableCellBorderIndex: Int,
): MeasuredCorner {
    val rowMeasurement = rowSpec.measurePoint(tableRowBorderIndex)
    val bufferLine = rowMeasurement.bufferPosition
    val columnMeasurement = colSpec.measurePoint(tableCellBorderIndex)
    val bufferColumn = columnMeasurement.bufferPosition

    val currentIndex: Pair<Int, Int> = tableRowBorderIndex to tableCellBorderIndex
    val topLeftCell: MeasuredCell? = cells[currentIndex + (-1 to -1)]
    val topRightCell: MeasuredCell? = cells[currentIndex + (-1 to 0)]
    val bottomLeftCell: MeasuredCell? = cells[currentIndex + (0 to -1)]
    val bottomRightCell: MeasuredCell? = cells[currentIndex + (0 to 0)]

    val topCellsAreSame = topLeftCell === topRightCell
    val bottomCellsAreSame = bottomLeftCell === bottomRightCell
    val leftCellsAreSame = topLeftCell === bottomLeftCell
    val rightCellsAreSame = topRightCell === bottomRightCell

    return MeasuredCorner(
        bufferLine = bufferLine,
        bufferColumn = bufferColumn,

        topCellsAreSame = topCellsAreSame,
        bottomCellsAreSame = bottomCellsAreSame,
        leftCellsAreSame = leftCellsAreSame,
        rightCellsAreSame = rightCellsAreSame,
    )
}

private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return (this.first + other.first) to (this.second + other.second)
}

private operator fun List<MeasuredCell>.get(position: Pair<Int, Int>): MeasuredCell? {
    return this.firstOrNull { position in it }
}

private operator fun MeasuredCell.contains(position: Pair<Int, Int>): Boolean {
    return position.first in (startRowIndex until endRowIndex) &&
        position.second in (startColumnIndex until endColumnIndex)
}

internal data class MeasuredCorner(
    val bufferLine: Int = 0,
    val bufferColumn: Int = 0,

    val topCellsAreSame: Boolean,
    val bottomCellsAreSame: Boolean,
    val leftCellsAreSame: Boolean,
    val rightCellsAreSame: Boolean,
) : Drawable {
    private fun brush(borderSet: BorderSet): Char? {
        if (topCellsAreSame && bottomCellsAreSame && leftCellsAreSame && rightCellsAreSame) {
            // all surrounding cells are the same, don't draw this point
            return null
        }
        if (topCellsAreSame && bottomCellsAreSame) {
            // we are between two horizontally-expanded cells, use a horizontal rule
            return borderSet.h
        }
        if (leftCellsAreSame && rightCellsAreSame) {
            // we are between two vertically-expanded cells, use a vertical rule
            return borderSet.v
        }
        if (topCellsAreSame && leftCellsAreSame) {
            return borderSet.tl
        }
        if (topCellsAreSame && rightCellsAreSame) {
            return borderSet.tr
        }
        if (bottomCellsAreSame && leftCellsAreSame) {
            return borderSet.bl
        }
        if (bottomCellsAreSame && rightCellsAreSame) {
            return borderSet.br
        }
        if (topCellsAreSame) {
            return borderSet.ti
        }
        if (bottomCellsAreSame) {
            return borderSet.bi
        }
        if (leftCellsAreSame) {
            return borderSet.cl
        }
        if (rightCellsAreSame) {
            return borderSet.cr
        }

        return borderSet.ci
    }

    override fun draw(canvas: KrowCanvas, borderSet: BorderSet) {
        val brush = brush(borderSet)
        if (brush != null) {
            canvas.drawPoint(
                bufferLine,
                bufferColumn,
                brush
            )
        }
    }
}
