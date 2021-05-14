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
        val totalWidth = table.colSpec.sum() + (table.colSpec.size + 1)
        val totalHeight = table.rowSpec.sum() + (table.rowSpec.size + 1)

        // create empty buffer
        return createLinesOf(
            createLineOf(' ', totalWidth),
            totalHeight
        ).toMutableList()
    }

// Step 2: Measurement
// ---------------------------------------------------------------------------------------------------------------------

    private fun measureCells(table: Krow.Table): List<MeasuredCell> {
        return table.rows.flatMapIndexed { tableRowIndex, tableRow ->
            tableRow.cells.mapIndexed { tableCellIndex, tableCell ->
                measureCell(
                    cell = tableCell,
                    table = table,
                    tableRowIndex = tableRowIndex,
                    tableCellIndex = tableCellIndex
                )
            }
        }
    }

    private fun measureCell(
        table: Krow.Table,
        cell: Krow.Cell,
        tableRowIndex: Int,
        tableCellIndex: Int,
    ): MeasuredCell {
        val colSpan = cell.colSpan
        val width = table.colSpec.subList(tableCellIndex, tableCellIndex + colSpan).sum() + (colSpan - 1)
        val bufferColumn = table.colSpec.subList(0, tableCellIndex).sum() + tableCellIndex

        val rowSpan = cell.rowSpan
        val height = table.rowSpec.subList(tableRowIndex, tableRowIndex + rowSpan).sum() + (rowSpan - 1)
        val bufferLine = table.rowSpec.subList(0, tableRowIndex).sum() + tableRowIndex

        val startRowIndex = tableRowIndex
        val rowStartDescriptor = if (startRowIndex == 0) {
            RowDescriptor.TOP
        } else if (startRowIndex == table.rowSpec.size - 1) {
            RowDescriptor.BOTTOM
        } else {
            RowDescriptor.MIDDLE
        }
        val endRowIndex = tableRowIndex + cell.rowSpan
        val rowEndDescriptor = if (endRowIndex == 0) {
            RowDescriptor.TOP
        } else if (endRowIndex == table.rowSpec.size) {
            RowDescriptor.BOTTOM
        } else {
            RowDescriptor.MIDDLE
        }

        val startColumnIndex = tableCellIndex
        val columnStartDescriptor = if (startColumnIndex == 0) {
            ColumnDescriptor.FIRST
        } else if (startColumnIndex == table.colSpec.size - 1) {
            ColumnDescriptor.LAST
        } else {
            ColumnDescriptor.MIDDLE
        }
        val endColumnIndex = tableCellIndex + cell.colSpan
        val columnEndDescriptor = if (endColumnIndex == 0) {
            ColumnDescriptor.FIRST
        } else if (endColumnIndex == table.colSpec.size) {
            ColumnDescriptor.LAST
        } else {
            ColumnDescriptor.MIDDLE
        }

        return MeasuredCell(
            cell = cell,
            startRowIndex = startRowIndex,
            endRowIndex = endRowIndex,
            startColumnIndex = startColumnIndex,
            endColumnIndex = endColumnIndex,
            colSpan = colSpan,
            rowSpan = rowSpan,
            width = width,
            height = height,
            bufferLine = bufferLine,
            bufferColumn = bufferColumn,
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
        return (0..table.rowSpec.size).flatMapIndexed { tableRowBorderIndex, tableRowBorder ->
            (0..table.colSpec.size).mapIndexed { tableCellBorderIndex, tableCellBorder ->
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
        val bufferLine = table.rowSpec.subList(0, tableRowBorderIndex).sum() + tableRowBorderIndex
        val bufferColumn = table.colSpec.subList(0, tableCellBorderIndex).sum() + tableCellBorderIndex

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
        // Write the cell contents
        canvas.drawText(
            bufferLine,
            bufferColumn,
            cell.printContentWithPadding(width, height)
        )
    }
}
