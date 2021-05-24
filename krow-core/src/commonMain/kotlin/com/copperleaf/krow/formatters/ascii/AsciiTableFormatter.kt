package com.copperleaf.krow.formatters.ascii

import com.copperleaf.krow.formatters.TableFormatter
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.utils.BorderSet
import com.copperleaf.krow.utils.KrowCanvas
import com.copperleaf.krow.utils.SingleBorder
import com.copperleaf.krow.utils.createLineOf
import com.copperleaf.krow.utils.createLinesOf

class AsciiTableFormatter(
    private val borders: BorderSet = SingleBorder()
) : TableFormatter<String> {

    override fun print(table: Krow.Table): String {
        val canvas = KrowCanvas(buffer = createBuffer(table))

        val measuredCells = measureCells(table)
        val measuredBorders = measureBorders(table, measuredCells)

        // print the edges for all cells
        measuredCells.forEach { it.drawCellEdges(canvas) }

        // print the corners for every intersection in the table
        measuredBorders.forEach { it.drawCellCorners(canvas) }

        // draw the text content into each cell
        measuredCells.forEach { it.drawCellContent(canvas) }

        return canvas.toString()
    }

// Step 1: Create Buffer
// ---------------------------------------------------------------------------------------------------------------------

    private fun createBuffer(table: Krow.Table): MutableList<String> {
        val totalWidth = table.colSpec.getFullMeasurement().totalSize
        val totalHeight = table.rowSpec.getFullMeasurement().totalSize

        // create empty buffer
        return createLinesOf(
            createLineOf(' ', totalWidth),
            totalHeight
        ).toMutableList()
    }

// Step 2: Measurement
// ---------------------------------------------------------------------------------------------------------------------

    private fun measureCells(table: Krow.Table): List<MeasuredCell> {
        return table.rows.flatMapIndexed { _, tableRow ->
            tableRow.cells.mapIndexed { _, tableCell ->
                measureCell(
                    cell = tableCell,
                    table = table,
                )
            }
        }
    }

    private fun measureCell(
        table: Krow.Table,
        cell: Krow.Cell,
    ): MeasuredCell {
        val measuredHeight = table.rowSpec.measureRange(cell.rowName, cell.rowSpan)
        val measuredWidth = table.colSpec.measureRange(cell.columnName, cell.colSpan)

        val rowStartDescriptor = if (measuredHeight.startIndex == 0) {
            RowDescriptor.TOP
        } else if (measuredHeight.startIndex == table.rowSpec.sizes.lastIndex) {
            RowDescriptor.BOTTOM
        } else {
            RowDescriptor.MIDDLE
        }
        val rowEndDescriptor = if (measuredHeight.endIndex == 0) {
            RowDescriptor.TOP
        } else if (measuredHeight.endIndex == table.rowSpec.sizes.lastIndex) {
            RowDescriptor.BOTTOM
        } else {
            RowDescriptor.MIDDLE
        }

        val columnStartDescriptor = if (measuredWidth.startIndex == 0) {
            ColumnDescriptor.FIRST
        } else if (measuredWidth.startIndex == table.colSpec.sizes.lastIndex) {
            ColumnDescriptor.LAST
        } else {
            ColumnDescriptor.MIDDLE
        }
        val columnEndDescriptor = if (measuredWidth.endIndex == 0) {
            ColumnDescriptor.FIRST
        } else if (measuredWidth.endIndex == table.colSpec.sizes.lastIndex) {
            ColumnDescriptor.LAST
        } else {
            ColumnDescriptor.MIDDLE
        }

        return MeasuredCell(
            cell = cell,
            heightMeasurement = measuredHeight,
            widthMeasurement = measuredWidth,
            rowStartDescriptor = rowStartDescriptor,
            rowEndDescriptor = rowEndDescriptor,
            columnStartDescriptor = columnStartDescriptor,
            columnEndDescriptor = columnEndDescriptor
        )
    }

    private fun measureBorders(
        table: Krow.Table,
        cells: List<MeasuredCell>,
    ): List<MeasuredBorder> {
        return (0..table.rowSpec.sizes.size).flatMapIndexed { tableRowBorderIndex, _ ->
            (0..table.colSpec.sizes.size).mapIndexed { tableCellBorderIndex, _ ->
                measureBorder(
                    table,
                    cells,
                    tableRowBorderIndex,
                    tableCellBorderIndex,
                )
            }
        }
    }

    private fun measureBorder(
        table: Krow.Table,
        cells: List<MeasuredCell>,
        tableRowBorderIndex: Int,
        tableCellBorderIndex: Int,
    ): MeasuredBorder {
        val rowMeasurement = table.rowSpec.measurePoint(tableRowBorderIndex)
        val bufferLine = rowMeasurement.bufferPosition
        val columnMeasurement = table.colSpec.measurePoint(tableCellBorderIndex)
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

        return MeasuredBorder(
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

// Step 3: Draw Edges
// ---------------------------------------------------------------------------------------------------------------------

    private fun MeasuredCell.drawCellEdges(canvas: KrowCanvas) {
        // Draw the top horizontal rule
        canvas.drawHorizontalLine(
            bufferLine,
            bufferColumn + 1,
            width,
            topHorizontalRule(borders),
        )
        // Draw the bottom horizontal rule
        canvas.drawHorizontalLine(
            bufferLine + height + 1,
            bufferColumn + 1,
            width,
            bottomHorizontalRule(borders),
        )

        // Draw left vertical rule
        canvas.drawVerticalLine(bufferLine + 1, bufferColumn, height, leftVerticalRule(borders))
        // Draw right vertical rule
        canvas.drawVerticalLine(bufferLine + 1, bufferColumn + width + 1, height, rightVerticalRule(borders))
    }

// Step 3: Draw Corners
// ---------------------------------------------------------------------------------------------------------------------

    private fun MeasuredBorder.drawCellCorners(canvas: KrowCanvas) {
        val brush = brush(borders)
        if (brush != null) {
            canvas.drawPoint(
                bufferLine,
                bufferColumn,
                brush
            )
        }
    }

// Step 3: Draw Text
// ---------------------------------------------------------------------------------------------------------------------

    private fun MeasuredCell.drawCellContent(canvas: KrowCanvas) {
        val cellContent = cell.printContentWithPadding(width, height)
        // Write the cell contents
        canvas.drawText(
            bufferLine + 1,
            bufferColumn + 1,
            cellContent
        )
    }
}
