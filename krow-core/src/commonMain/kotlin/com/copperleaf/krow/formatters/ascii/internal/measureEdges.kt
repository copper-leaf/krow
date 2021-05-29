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
    return measureVerticalEdges(cells, rowSpec, colSpec) + measureHorizontalEdges(cells, rowSpec, colSpec)
}

// Vertical
// ---------------------------------------------------------------------------------------------------------------------

internal fun measureVerticalEdges(
    cells: List<MeasuredCell>,
    rowSpec: TableSpec,
    colSpec: TableSpec,
): List<MeasuredEdge.VerticalEdge> {
    return (0 until rowSpec.sizes.size).flatMapIndexed { tableRowBorderIndex, _ ->
        (0..colSpec.sizes.size).mapIndexed { tableCellBorderIndex, _ ->
            measureVerticalEdge(
                cells,
                rowSpec,
                colSpec,
                tableRowBorderIndex,
                tableCellBorderIndex,
            )
        }
    }
}

private fun measureVerticalEdge(
    cells: List<MeasuredCell>,
    rowSpec: TableSpec,
    colSpec: TableSpec,
    tableRowBorderIndex: Int,
    tableCellBorderIndex: Int,
): MeasuredEdge.VerticalEdge {
    val rowMeasurement = rowSpec.measureLine(tableRowBorderIndex)
    val bufferLine = rowMeasurement.bufferPosition
    val columnMeasurement = colSpec.measurePoint(tableCellBorderIndex)
    val bufferColumn = columnMeasurement.bufferPosition

    val currentIndex: Pair<Int, Int> = tableRowBorderIndex to tableCellBorderIndex
    val leftCell: MeasuredCell? = cells[currentIndex + (0 to -1)]
    val rightCell: MeasuredCell? = cells[currentIndex + (0 to 0)]

    return MeasuredEdge.VerticalEdge(
        row = bufferLine + 1,
        column = bufferColumn,
        height = rowMeasurement.totalSize,
        leftCellMissing = leftCell == null,
        rightCellMissing = rightCell == null,
        leftAndRightCellsAreSame = leftCell === rightCell,
    )
}

// Horizontal
// ---------------------------------------------------------------------------------------------------------------------

internal fun measureHorizontalEdges(
    cells: List<MeasuredCell>,
    rowSpec: TableSpec,
    colSpec: TableSpec,
): List<MeasuredEdge.HorizontalEdge> {
    return (0..rowSpec.sizes.size).flatMapIndexed { tableRowBorderIndex, _ ->
        (0 until colSpec.sizes.size).mapIndexed { tableCellBorderIndex, _ ->
            measureHorizontalEdges(
                cells,
                rowSpec,
                colSpec,
                tableRowBorderIndex,
                tableCellBorderIndex,
            )
        }
    }
}

private fun measureHorizontalEdges(
    cells: List<MeasuredCell>,
    rowSpec: TableSpec,
    colSpec: TableSpec,
    tableRowBorderIndex: Int,
    tableCellBorderIndex: Int,
): MeasuredEdge.HorizontalEdge {
    val rowMeasurement = rowSpec.measurePoint(tableRowBorderIndex)
    val bufferLine = rowMeasurement.bufferPosition
    val columnMeasurement = colSpec.measureLine(tableCellBorderIndex)
    val bufferColumn = columnMeasurement.bufferPosition

    val currentIndex: Pair<Int, Int> = tableRowBorderIndex to tableCellBorderIndex
    val topCell: MeasuredCell? = cells[currentIndex + (-1 to 0)]
    val bottomCell: MeasuredCell? = cells[currentIndex + (0 to 0)]

    return MeasuredEdge.HorizontalEdge(
        row = bufferLine,
        column = bufferColumn + 1,
        width = columnMeasurement.totalSize,
        topCellMissing = topCell == null,
        bottomCellMissing = bottomCell == null,
        topAndBottomCellsAreSame = topCell === bottomCell,
    )
}

internal sealed class MeasuredEdge : Drawable {
    internal class VerticalEdge(
        val row: Int,
        val column: Int,
        val height: Int,
        val leftCellMissing: Boolean,
        val rightCellMissing: Boolean,
        val leftAndRightCellsAreSame: Boolean,
    ) : MeasuredEdge() {

        private fun brush(borderSet: BorderSet): String? {
            if (leftAndRightCellsAreSame) {
                return null
            }
            if (leftCellMissing) {
                return borderSet.verticalLeftEdge
            }
            if (rightCellMissing) {
                return borderSet.verticalLeftEdge
            }

            return borderSet.verticalEdge
        }

        override fun draw(canvas: KrowCanvas, borderSet: BorderSet) {
            val brush = brush(borderSet)
            if (brush != null) {
                canvas.drawVerticalLine(
                    row,
                    column,
                    height,
                    brush
                )
            }
        }
    }

    internal data class HorizontalEdge(
        val row: Int,
        val column: Int,
        val width: Int,
        val topCellMissing: Boolean,
        val bottomCellMissing: Boolean,
        val topAndBottomCellsAreSame: Boolean,
    ) : MeasuredEdge() {

        private fun brush(borderSet: BorderSet): String? {
            if (topAndBottomCellsAreSame) {
                return null
            }
            if (topCellMissing) {
                return borderSet.horizontalTopEdge
            }
            if (bottomCellMissing) {
                return borderSet.horizontalBottomEdge
            }

            return borderSet.horizontalEdge
        }

        override fun draw(canvas: KrowCanvas, borderSet: BorderSet) {
            val brush = brush(borderSet)
            if (brush != null) {
                canvas.drawHorizontalLine(
                    row,
                    column,
                    width,
                    brush,
                )
            }
        }
    }
}
