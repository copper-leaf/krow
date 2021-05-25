package com.copperleaf.krow.formatters.ascii.internal

import com.copperleaf.krow.formatters.ascii.ColumnDescriptor
import com.copperleaf.krow.formatters.ascii.RowDescriptor
import com.copperleaf.krow.formatters.ascii.printContentWithPadding
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.TableSpec
import com.copperleaf.krow.model.TableSpecMeasurement
import com.copperleaf.krow.utils.BorderSet
import com.copperleaf.krow.utils.Drawable
import com.copperleaf.krow.utils.KrowCanvas

internal fun measureCells(
    table: Krow.Table,
    rowSpec: TableSpec,
    colSpec: TableSpec,
): List<MeasuredCell> {
    return table.visibleRows.flatMapIndexed { _, tableRow ->
        tableRow.cells.mapIndexed { _, tableCell ->
            measureCell(
                cell = tableCell,
                rowSpec = rowSpec,
                colSpec = colSpec
            )
        }
    }
}

private fun measureCell(
    cell: Krow.Cell,
    rowSpec: TableSpec,
    colSpec: TableSpec,
): MeasuredCell {
    val measuredHeight = rowSpec.measureRange(cell.rowName, cell.rowSpan)
    val measuredWidth = colSpec.measureRange(cell.columnName, cell.colSpan)

    val rowStartDescriptor = if (measuredHeight.startIndex == 0) {
        RowDescriptor.TOP
    } else if (measuredHeight.startIndex == rowSpec.sizes.lastIndex) {
        RowDescriptor.BOTTOM
    } else {
        RowDescriptor.MIDDLE
    }
    val rowEndDescriptor = if (measuredHeight.endIndex == 0) {
        RowDescriptor.TOP
    } else if (measuredHeight.endIndex == rowSpec.sizes.lastIndex) {
        RowDescriptor.BOTTOM
    } else {
        RowDescriptor.MIDDLE
    }

    val columnStartDescriptor = if (measuredWidth.startIndex == 0) {
        ColumnDescriptor.FIRST
    } else if (measuredWidth.startIndex == colSpec.sizes.lastIndex) {
        ColumnDescriptor.LAST
    } else {
        ColumnDescriptor.MIDDLE
    }
    val columnEndDescriptor = if (measuredWidth.endIndex == 0) {
        ColumnDescriptor.FIRST
    } else if (measuredWidth.endIndex == colSpec.sizes.lastIndex) {
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

internal data class MeasuredCell(
    val cell: Krow.Cell,

    val heightMeasurement: TableSpecMeasurement,
    val widthMeasurement: TableSpecMeasurement,

    val startRowIndex: Int = heightMeasurement.startIndex,
    val endRowIndex: Int = heightMeasurement.endIndex,
    val startColumnIndex: Int = widthMeasurement.startIndex,
    val endColumnIndex: Int = widthMeasurement.endIndex,

    val colSpan: Int = 0,
    val rowSpan: Int = 0,

    val width: Int = widthMeasurement.totalSize,
    val height: Int = heightMeasurement.totalSize,

    val bufferLine: Int = heightMeasurement.bufferPosition,
    val bufferColumn: Int = widthMeasurement.bufferPosition,

    val rowStartDescriptor: RowDescriptor,
    val rowEndDescriptor: RowDescriptor,
    val columnStartDescriptor: ColumnDescriptor,
    val columnEndDescriptor: ColumnDescriptor,
) : Drawable {

    override fun draw(canvas: KrowCanvas, borderSet: BorderSet) {
        val cellContent = cell.printContentWithPadding(width, height)
        // Write the cell contents
        canvas.drawText(
            bufferLine + 1,
            bufferColumn + 1,
            cellContent
        )
    }
}
