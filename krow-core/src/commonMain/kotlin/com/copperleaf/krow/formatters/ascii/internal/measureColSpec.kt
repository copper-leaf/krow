package com.copperleaf.krow.formatters.ascii.internal

import com.copperleaf.krow.formatters.ascii.intrinsicWidthWithPadding
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.TableSpec
import com.copperleaf.krow.model.toTableSpec

fun measureColSpec(
    table: Krow.Table,
): TableSpec {
    val cellsGroupedByColumn: Map<String, List<Krow.Cell>> = table.visibleRows
        .flatMap { it.cells }
        .groupBy { it.columnName }

    // calculate column widths
    return table.headerRow
        .cells
        .map { headerColumnCell ->
            val cellsInColumn = cellsGroupedByColumn[headerColumnCell.columnName]!!
            headerColumnCell.columnName to measureColumnWidth(headerColumnCell, cellsInColumn)
        }
        .toTableSpec()
}

private fun measureColumnWidth(
    headerColumnCell: Krow.Cell,
    cellsInColumn: List<Krow.Cell>
): Int {
    if (headerColumnCell.width != null) {
        // column has fixed width
        return headerColumnCell.width
    }

    // auto-size column to the content width of the cells in this column that do not span multiple columns
    val cellsInColumnWithoutColSpan = cellsInColumn.filter { it.colSpan == 1 }
    if(cellsInColumnWithoutColSpan.isNotEmpty()) {
        return cellsInColumnWithoutColSpan
            .maxOf { cell: Krow.Cell -> cell.intrinsicWidthWithPadding }
    }

    // default to a width of 12 if we can't figure anything else out
    return 12
}
