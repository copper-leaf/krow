package com.copperleaf.krow.formatters.ascii.internal

import com.copperleaf.krow.formatters.ascii.intrinsicHeight
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.TableSpec
import com.copperleaf.krow.model.toTableSpec

// TODO: measure cells first, and take the greatest height of all cells in a row
fun measureRowSpec(
     table: Krow.Table,
 ): TableSpec {
    return table.visibleRows
        .map { tableRow ->
            val rowHeight = tableRow.cells.maxOf { it.intrinsicHeight }
            tableRow.rowName to rowHeight
        }
        .toTableSpec()
}
