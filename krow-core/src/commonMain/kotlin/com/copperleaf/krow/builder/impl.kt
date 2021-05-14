package com.copperleaf.krow.builder

import com.copperleaf.krow.formatters.ascii.colSpec
import com.copperleaf.krow.formatters.ascii.rowSpec
import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.VerticalAlignment

class TableScopeImpl : TableScope {

    override var includeHeaderRow: Boolean = true
    override var includeLeadingColumn: Boolean = true

    internal val header = HeaderScopeImpl()
    internal val body = BodyScopeImpl()

    override fun header(block: HeaderScope.() -> Unit) {
        header.block()
    }

    override fun headerColumns(vararg columnNames: String, block: HeaderCellScope.() -> Unit) {
        header.columns(*columnNames, block = block)
    }

    override fun body(block: BodyScope.() -> Unit) {
        body.block()
    }

    override fun row(rowName: String?, block: BodyRowScope.() -> Unit) {
        body.row(rowName, block)
    }

    override fun rows(vararg rows: Pair<String, List<String>>, block: BodyCellScope.() -> Unit) {
        body.rows(*rows, block = block)
    }

    override fun rows(vararg rows: List<String>, block: BodyCellScope.() -> Unit) {
        body.rows(*rows, block = block)
    }

    override fun cell(columnName: String, rowName: String, block: BodyCellScope.() -> Unit): BodyCellScope {
        // see if we can find a cell at these coordinates by name
        var column: HeaderCellScopeImpl? = header[columnName]
        var row: BodyRowScopeImpl? = body[rowName]

        if (column == null) {
            // add a column
            column = header.column(columnName) as HeaderCellScopeImpl?
        }
        if (row == null) {
            // add a row, with empty cells until the column index
            row = body.row(rowName) {
                for (i in 1..column!!.columnIndex) {
                    cell()
                }
            } as BodyRowScopeImpl?
        }

        return cell(column!!.columnIndex, row!!.rowIndex, block)
    }

    override fun cell(columnIndex: Int, rowIndex: Int, block: BodyCellScope.() -> Unit): BodyCellScope {
        val column = header[columnIndex]
        val row = body[rowIndex]

        val cell = row.cells[columnIndex]

        block(cell)

        return cell
    }

    fun build(): Krow.Table {
        val headerRow = header.build(includeLeadingColumn, body)
        val bodyRows = body.build(header.headerRow, includeLeadingColumn)

        val displayedRows: List<Krow.Row> = if (includeHeaderRow) {
            listOf(headerRow) + bodyRows
        } else {
            bodyRows
        }

        return Krow.Table(
            colSpec = headerRow.colSpec,
            rowSpec = displayedRows.rowSpec,
            includeHeaderRow = includeHeaderRow,
            includeLeadingColumn = includeLeadingColumn,
            rows = displayedRows,
        )
    }
}

class HeaderScopeImpl : HeaderScope {

    internal val headerRow = HeaderRowScopeImpl()

    override fun row(block: HeaderRowScope.() -> Unit) {
        headerRow.block()
    }

    override fun column(columnName: String?, block: HeaderCellScope.() -> Unit): HeaderCellScope {
        return headerRow.column(columnName, block)
    }

    override fun columns(vararg columnNames: String, block: HeaderCellScope.() -> Unit) {
        headerRow.columns(*columnNames, block = block)
    }

    internal operator fun get(columnIndex: Int): HeaderCellScopeImpl {
        return headerRow[columnIndex]
    }

    internal operator fun get(columnName: String): HeaderCellScopeImpl? {
        return headerRow[columnName]
    }

    fun build(includeLeadingColumn: Boolean, body: BodyScopeImpl): Krow.Row {
        val cells = headerRow.build().cells

        return if (includeLeadingColumn) {
            val leadingColumnWidth = body.rows.maxOf { it.rowName.length }
            val headerRowColumn = Krow.Cell(
                data = "",
                width = leadingColumnWidth
            )

            Krow.Row(listOf(headerRowColumn) + cells)
        } else {
            Krow.Row(cells)
        }
    }
}

class HeaderRowScopeImpl : HeaderRowScope {
    internal var autoincrement = 1
    internal val columns = mutableListOf<HeaderCellScopeImpl>()

    override fun column(columnName: String?, block: HeaderCellScope.() -> Unit): HeaderCellScope {
        val newCell = HeaderCellScopeImpl(columnIndex = autoincrement, content = columnName ?: "$autoincrement")
        newCell.block()
        columns.add(newCell)
        autoincrement++
        return newCell
    }

    override fun columns(vararg columnNames: String, block: HeaderCellScope.() -> Unit) {
        for (columnName in columnNames) {
            column(columnName, block)
        }
    }

    internal operator fun get(columnIndex: Int): HeaderCellScopeImpl {
        return columns[columnIndex]
    }

    internal operator fun get(columnName: String): HeaderCellScopeImpl? {
        return columns.firstOrNull { it.columnName == columnName }
    }

    fun build(): Krow.Row {
        return Krow.Row(
            columns.map { it.build() }
        )
    }
}

class BodyScopeImpl : BodyScope {
    internal var autoincrement = 1
    internal val rows = mutableListOf<BodyRowScopeImpl>()

    override fun row(rowName: String?, block: BodyRowScope.() -> Unit) {
        val newRow = BodyRowScopeImpl(rowIndex = autoincrement, rowName = rowName ?: "$autoincrement")
        newRow.block()
        rows.add(newRow)
        autoincrement++
    }

    override fun rows(vararg rows: Pair<String, List<String>>, block: BodyCellScope.() -> Unit) {
        for ((rowName, rowCells) in rows) {
            row(rowName) {
                cells(*rowCells.toTypedArray(), block = block)
            }
        }
    }

    override fun rows(vararg rows: List<String>, block: BodyCellScope.() -> Unit) {
        for (rowCells in rows) {
            row {
                cells(*rowCells.toTypedArray(), block = block)
            }
        }
    }

    internal operator fun get(rowIndex: Int): BodyRowScopeImpl {
        return rows[rowIndex]
    }

    internal operator fun get(rowName: String): BodyRowScopeImpl? {
        return rows.firstOrNull { it.rowName == rowName }
    }

    fun build(headerRow: HeaderRowScopeImpl, includeLeadingColumn: Boolean): List<Krow.Row> {
        return rows.map { it.build(headerRow, includeLeadingColumn) }
    }
}

class BodyRowScopeImpl(
    val rowIndex: Int,
    override var rowName: String
) : BodyRowScope {

    internal var autoincrement = 1
    internal val cells = mutableListOf<BodyCellScopeImpl>()

    override fun cell(cellContent: String?, block: BodyCellScope.() -> Unit): BodyCellScope {
        val newCell = BodyCellScopeImpl(columnIndex = autoincrement, content = cellContent ?: "$autoincrement")
        newCell.block()
        cells.add(newCell)
        autoincrement += newCell.colSpan
        return newCell
    }

    override fun cells(vararg cellContents: String, block: BodyCellScope.() -> Unit) {
        for (cellContent in cellContents) {
            cell(cellContent, block)
        }
    }

    internal operator fun get(columnIndex: Int): BodyCellScopeImpl {
        return cells[columnIndex]
    }

    fun build(headerRow: HeaderRowScopeImpl, includeLeadingColumn: Boolean): Krow.Row {
        val dataCells = cells.map { it.build(headerRow) }
        val cells = if (includeLeadingColumn) {
            listOf(Krow.Cell(data = rowName)) + dataCells
        } else {
            dataCells
        }

        return Krow.Row(
            cells
        )
    }
}

class HeaderCellScopeImpl(
    var columnIndex: Int,
    override var content: String,
    override var columnName: String = content,
    override var width: Int? = null,
    override var verticalAlignment: VerticalAlignment? = null,
    override var horizontalAlignment: HorizontalAlignment? = null,
) : HeaderCellScope {
    fun build(): Krow.Cell {
        return Krow.Cell(
            data = content,
            width = width,
            horizontalAlignment = horizontalAlignment ?: HorizontalAlignment.LEFT,
            verticalAlignment = verticalAlignment ?: VerticalAlignment.TOP,
        )
    }
}

class BodyCellScopeImpl(
    var columnIndex: Int,
    override var content: String,
    override var verticalAlignment: VerticalAlignment? = null,
    override var horizontalAlignment: HorizontalAlignment? = null,
    override var colSpan: Int = 1,
    override var rowSpan: Int = 1
) : BodyCellScope {
    fun build(headerRow: HeaderRowScopeImpl): Krow.Cell {
        val columnCell = headerRow.columns[columnIndex - 1]

        return Krow.Cell(
            data = content,
            rowSpan = rowSpan,
            colSpan = colSpan,
            horizontalAlignment = horizontalAlignment ?: columnCell.horizontalAlignment ?: HorizontalAlignment.LEFT,
            verticalAlignment = verticalAlignment ?: columnCell.verticalAlignment ?: VerticalAlignment.TOP,
        )
    }
}
