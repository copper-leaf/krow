package com.copperleaf.krow.formatters.ascii.internal

import com.copperleaf.krow.formatters.ascii.measureContentHeight
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.TableSpec
import com.copperleaf.krow.model.toTableSpec

/**
 * Given the general layout of cells in the table and the column widths in ASCII characters, measure how tall each
 * cell will ultimately be, and determine the height of each row in ASCII characters.
 */
internal fun measureRowSpec(
    table: Krow.Table,
    colSpec: TableSpec,
): TableSpec {
    return table.visibleRows
        .map { tableRow -> tableRow.rowName to determineHeightOfRow(tableRow, colSpec) }
        .toTableSpec()
}

private fun determineHeightOfRow(
    tableRow: Krow.Row,
    colSpec: TableSpec,
): Int {
    return tableRow.cells.maxOf { determineActualHeightOfCell(it, colSpec) }
}

private fun determineActualHeightOfCell(
    cell: Krow.Cell,
    colSpec: TableSpec,
): Int {
    val measurement = colSpec.measureRange(cell.columnName, cell.colSpan)

    val availableWidth = measurement.totalSize

    return cell.measureContentHeight(availableWidth)
}
