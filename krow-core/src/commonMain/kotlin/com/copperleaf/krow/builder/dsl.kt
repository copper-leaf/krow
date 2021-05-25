package com.copperleaf.krow.builder

import com.copperleaf.krow.formatters.TableFormatter
import com.copperleaf.krow.model.Krow

/**
 * Open a configuration [block] to build a Table. The [KrowTableDsl] allows you to configure the columns and rows within
 * the table, and the body cells may span multiple rows/columns within the rendered table.
 *
 * The resulting [Krow.Table] is intended as an intermediate representation of the table. It is not designed to be
 * inspected, and should only be used to pass to a [TableFormatter] to be displayed.
 *
 * A [Krow.Table] is immutable after creation, and may be rendered multiple times, by multiple formatters. It is
 * expected that a single table instance can be used in any Formatter, and that you do not need to create the table
 * separately for each Formatter.
 */
fun krow(block: TableScope.() -> Unit): Krow.Table {
    return TableScopeImpl().apply(block).build()
}

/**
 * Adds each item in [columnNames] as a new column or looks up the column at that name, and applies [block] to each
 * column.
 */
@KrowTableDsl
fun TableScope.headerColumns(vararg columnNames: String, block: HeaderCellScope.() -> Unit = {}) {
    header {
        columns(*columnNames, block = block)
    }
}

/**
 * Shortcut to adding a body row from the root Table DSL.
 *
 * @see BodyScope.row
 */
@KrowTableDsl
fun TableScope.bodyRow(rowName: String? = null, block: BodyRowScope.() -> Unit = {}) {
    body {
        row(rowName, block = block)
    }
}

/**
 * Shortcut to adding a body row from the root Table DSL.
 *
 * @see BodyScope.rows
 */
@KrowTableDsl
fun TableScope.bodyRows(vararg rows: Pair<String, List<String>>, block: MutableBodyCellScope.() -> Unit = {}) {
    body {
        rows(*rows, block = block)
    }
}

/**
 * Shortcut for adding multiple rows with content cells for each row. Each item in [rows] will create a new anonymous
 * row and add the list as the content for each cell in that row.
 *
 * @see BodyScope.rows
 */
@KrowTableDsl
fun TableScope.bodyRows(vararg rows: List<String>, block: MutableBodyCellScope.() -> Unit = {}) {
    body {
        rows(*rows, block = block)
    }
}

/**
 * @see BodyScope.rows
 */
@KrowTableDsl
fun TableScope.bodyRows(vararg rows: String, block: BodyRowScope.() -> Unit = {}) {
    body {
        rows(*rows, block = block)
    }
}

/**
 * @see BodyScope.cellAt
 */
@KrowTableDsl
fun TableScope.cellAt(rowName: String, columnName: String, block: MutableBodyCellScope.() -> Unit = {}): BodyCellScope {
    var cell: BodyCellScope? = null
    body {
        cell = cellAt(rowName, columnName, block = block)
    }
    return cell!!
}

/**
 * @see HeaderRowScope.column
 */
@KrowTableDsl
fun HeaderScope.column(columnName: String? = null, block: HeaderCellScope.() -> Unit = {}): HeaderCellScope {
    var cell: HeaderCellScope? = null
    row {
        cell = column(columnName, block = block)
    }
    return cell!!
}

/**
 * Adds each item in [columnNames] as a new column or looks up the column at that name, and applies [block] to each
 * column.
 */
@KrowTableDsl
fun HeaderScope.columns(vararg columnNames: String, block: HeaderCellScope.() -> Unit = {}) {
    row {
        columns(*columnNames, block = block)
    }
}

/**
 * Adds each item in [columnNames] as a new column or looks up the column at that name, and applies [block] to each
 * column.
 */
@KrowTableDsl
fun HeaderRowScope.columns(vararg columnNames: String, block: HeaderCellScope.() -> Unit = {}) {
    for (columnName in columnNames) {
        column(columnName, block)
    }
}

/**
 * Shortcut to for adding multiple rows, with the content for the cells in each row. The [Pair.first] value will be used
 * as the row name, and [Pair.second] value will be the content for each cell in that row.
 *
 * @see BodyScope.row
 * @see BodyRowScope.cells
 */
@KrowTableDsl
fun BodyScope.rows(vararg rows: Pair<String, List<String>>, block: MutableBodyCellScope.() -> Unit = {}) {
    for ((rowName, rowCells) in rows) {
        row(rowName) {
            cells(*rowCells.toTypedArray(), block = block)
        }
    }
}

/**
 * Shortcut for adding multiple rows. Each item in [rows] will create a new row or look up an existing row with that
 * rowName, and apply the configuration [block] on each.
 *
 * @see BodyScope.row
 */
@KrowTableDsl
fun BodyScope.rows(vararg rows: String, block: BodyRowScope.() -> Unit = {}) {
    for (rowName in rows) {
        row(rowName, block)
    }
}

/**
 * Shortcut for adding multiple rows with content cells for each row. Each item in [rows] will create a new anonymous
 * row and add the list as the content for each cell in that row.
 *
 * @see BodyScope.row
 * @see BodyRowScope.cells
 */
@KrowTableDsl
fun BodyScope.rows(vararg rows: List<String>, block: MutableBodyCellScope.() -> Unit = {}) {
    for (rowCells in rows) {
        row {
            cells(*rowCells.toTypedArray(), block = block)
        }
    }
}

/**
 * @see BodyRowScope.cellAt
 */
@KrowTableDsl
fun BodyScope.cellAt(rowName: String, columnName: String, block: MutableBodyCellScope.() -> Unit = {}): BodyCellScope {
    var cell: BodyCellScope? = null
    row(rowName) {
        cell = cellAt(columnName = columnName, cellContent = "", block)
    }

    return cell!!
}

/**
 * Adds each item in [cellContents] as a new cell in this row.
 */
@KrowTableDsl
fun BodyRowScope.cells(vararg cellContents: String, block: MutableBodyCellScope.() -> Unit = {}) {
    for (cellContent in cellContents) {
        cell(cellContent, block)
    }
}
