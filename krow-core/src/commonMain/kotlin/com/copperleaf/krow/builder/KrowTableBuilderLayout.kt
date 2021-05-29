package com.copperleaf.krow.builder

class CellId(
    val columnName: String,
    val columnIndex: Int,
    val rowName: String,
    val rowIndex: Int,
    var occupied: Boolean = false,
) {
    override fun toString(): String {
        return if (occupied) {
            "($rowName:$rowIndex, $columnName:$columnIndex)!"
        } else {
            "($rowName:$rowIndex, $columnName:$columnIndex)?"
        }
    }
}

@KrowTableDsl
class KrowTableBuilderLayout {
    companion object {
        const val HEADER_ROW_NAME = "header"
        const val LEADING_COLUMN_NAME = "leading"
    }

    private val columns: MutableList<String> = mutableListOf()
    private val rows: MutableList<String> = mutableListOf()
    private val grid: MutableList<MutableList<CellId>> = mutableListOf()

    init {
        getOrCreateRow(HEADER_ROW_NAME)
    }

    fun getRowCells(rowName: String): List<CellId> {
        check(rows.contains(rowName)) { "Row with name '$rowName' does not exist!" }
        return grid[rows.indexOf(rowName)]
    }

    fun getOrCreateColumn(columnName: String?): Pair<Boolean, Int> {
        // if rowName is null, add a new row with a name of the next index
        val actualColumnName = columnName ?: (columns.lastIndex + 2).toString()

        val isNew = if (!columns.contains(actualColumnName)) {
            columns.add(actualColumnName)
            val columnIndex = columns.indexOf(actualColumnName)

            // we do not have this row already. Add the column to all rows and then recompute the layout
            rows.forEachIndexed { rowIndex, rowName ->
                grid[rowIndex].add(
                    CellId(
                        columnName = actualColumnName,
                        columnIndex = columnIndex,
                        rowName = rowName,
                        rowIndex = rowIndex,
                        occupied = rowName == HEADER_ROW_NAME // header cells are always considered occuipied
                    )
                )
            }
            true
        } else {
            false
        }

        return isNew to columns.indexOf(actualColumnName)
    }

    fun getOrCreateRow(rowName: String?): Pair<Boolean, Int> {
        // if rowName is null, add a new row with a name of the next index
        val actualRowName = rowName ?: (rows.lastIndex + 1).toString()

        val isNew = if (!rows.contains(actualRowName)) {
            // we do not have this row already. Add the row and then recompute the layout
            rows.add(actualRowName)
            val rowIndex = rows.indexOf(actualRowName)

            val newRow = columns
                .mapIndexed { columnIndex, columnName ->
                    CellId(
                        columnName = columnName,
                        columnIndex = columnIndex,
                        rowName = actualRowName,
                        rowIndex = rowIndex
                    )
                }
                .toMutableList()

            grid.add(newRow)
            true
        } else {
            false
        }

        return isNew to rows.indexOf(actualRowName)
    }

    fun placeCellInRow(rowName: String?, rowSpan: Int, colSpan: Int): CellId {
        val (_, startRowId) = getOrCreateRow(rowName)

        // determine if there are any free spaces left in the row. If not, add columns to make it fit
        val startColumnId = if (grid[startRowId].all { it.occupied }) {
            getOrCreateColumn(null).second
        } else {
            grid[startRowId].first { !it.occupied }.columnIndex
        }

        return placeExpandedCell(
            startRowId = startRowId,
            rowSpan = rowSpan,
            startColumnId = startColumnId,
            colSpan = colSpan
        )
    }

    fun placeCellExact(rowName: String, columnName: String, rowSpan: Int, colSpan: Int): CellId {
        val (_, startRowId) = getOrCreateRow(rowName)
        val (_, startColumnId) = getOrCreateColumn(columnName)

        // ensure this exact cell is not already occupied
        val cell = getCellAt(rowName, columnName)
        check(!cell.occupied) {
            "Cell at position $cell is already occupied!"
        }

        return placeExpandedCell(
            startRowId = startRowId,
            rowSpan = rowSpan,
            startColumnId = startColumnId,
            colSpan = colSpan
        )
    }

    private fun placeExpandedCell(
        startRowId: Int,
        rowSpan: Int,
        startColumnId: Int,
        colSpan: Int
    ): CellId {
        for (rowId in startRowId until (startRowId + rowSpan)) {
            // go through the rows and mark the cells as occupied, creating new rows as needed
            if (rowId > rows.lastIndex) {
                getOrCreateRow(null)
            }

            for (columnId in startColumnId until (startColumnId + colSpan)) {
                // go through the columns for each row and mark the cells as occupied, creating new columns as needed
                if (columnId > columns.lastIndex) {
                    getOrCreateColumn(null)
                }

                val thisCell = grid[rowId][columnId]

                // if any cell is already occupied, it is considered an error as we'd have to overwrite it to accept
                // this new cell
                check(!thisCell.occupied) { "Cell at position (row=$rowId, column=$colSpan) is already occupied!" }

                thisCell.occupied = true
            }
        }

        return grid[startRowId][startColumnId]
    }

    fun getColumnName(index: Int): String {
        return columns[index]
    }

    fun getCellAt(rowName: String, columnName: String): CellId {
        val rowIndex = rows.indexOf(rowName)
        val columnIndex = columns.indexOf(columnName)

        check(rowIndex != -1) { "row with name '$rowName' does not exist!" }
        check(columnIndex != -1) { "column with name '$columnName' does not exist!" }

        return grid[rowIndex][columnIndex]
    }
}
