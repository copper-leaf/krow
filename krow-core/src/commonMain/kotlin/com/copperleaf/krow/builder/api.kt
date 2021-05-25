package com.copperleaf.krow.builder

import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment

@DslMarker
annotation class KrowTableDsl

@KrowTableDsl
interface TableScope {
    /**
     * Whether the table should be rendered with the header row visible
     */
    var includeHeaderRow: Boolean

    /**
     * Whether the table should be rendered with the leading column visible
     */
    var includeLeadingColumn: Boolean

    /**
     * Configure the table's Header to define the available Columns and their attributes
     */
    fun header(block: HeaderScope.() -> Unit)

    /**
     * Configure the table's Body to define the data Rows, the rows' Cells, and their attributes
     */
    fun body(block: BodyScope.() -> Unit)
}

@KrowTableDsl
interface HeaderScope {
    /**
     * Configure the table's Header Row to define the available Columns and their attributes
     */
    fun row(block: HeaderRowScope.() -> Unit)
}

@KrowTableDsl
interface HeaderRowScope {

    /**
     * Add a new Column, or lookup an existing column. Configure that column inside the builder [block].
     *
     * If [columnName] is not provided, a default column name will be generated automatically.
     */
    fun column(columnName: String? = null, block: HeaderCellScope.() -> Unit = {}): HeaderCellScope
}

@KrowTableDsl
interface BodyScope {
    /**
     * Add a new Row, or lookup and existing Row. Configure that row inside the builder [block].
     *
     * If [rowName] is not provided, a default row name will be generated automatically.
     */
    fun row(rowName: String? = null, block: BodyRowScope.() -> Unit)
}

@KrowTableDsl
interface BodyRowScope {
    val rowName: String

    /**
     * Add a new Cell to this column, placing it at the next available column in this row. Additionally, columns will
     * be added to accept this cell if the row is already full, or the cell's [MutableBodyCellScope.colSpan] extends
     * beyond the current table's columns.
     *
     * An exception will be thrown if the cell configures its [MutableBodyCellScope.colSpan] or
     * [MutableBodyCellScope.rowSpan] such that it would overlap an existing cell.
     */
    fun cell(cellContent: String? = null, block: MutableBodyCellScope.() -> Unit = {}): BodyCellScope

    /**
     * Add a new Cell to this column, placing it at the exact [columnName] position.
     *
     * An exception will be thrown if the position at this row/column is already occupied by another cell. Additionally,
     * an exception will be thrown if the cell configures its [MutableBodyCellScope.colSpan] or
     * [MutableBodyCellScope.rowSpan] such that it would overlap an existing cell.
     */
    fun cellAt(
        columnName: String,
        cellContent: String? = null,
        block: MutableBodyCellScope.() -> Unit = {}
    ): BodyCellScope
}

/**
 * Configuration for a Cell in the table Header. The [columnName] must be provided when calling the builder function, or
 * it will be generated automatically. The [columnName] cannot be changed once created.
 *
 * If [width] is provided, the column will be rendered with that exact width. Otherwise, the column width will be
 * calculated automatically based on the width of the body cells in the column.
 *
 * By default, [content] is the [columnName], but it can be changed if needed.
 */
@KrowTableDsl
interface HeaderCellScope {
    var width: Int?
    val columnName: String
    var verticalAlignment: VerticalAlignment?
    var horizontalAlignment: HorizontalAlignment?
    var content: String
}

/**
 * Configuration for a Cell in a row in the table Body. Body cells may span multiple rows and/or columns freely, so long
 * as they do not overlap other cells in the table. The [rowSpan] and [colSpan] may not be changed after the cell has
 * been created.
 */
@KrowTableDsl
interface BodyCellScope {
    var verticalAlignment: VerticalAlignment?
    var horizontalAlignment: HorizontalAlignment?
    var content: String
    val colSpan: Int
    val rowSpan: Int
}

/**
 * Configuration for a Cell in a row in the table Body. Body cells may span multiple rows and/or columns freely, so long
 * as they do not overlap other cells in the table. The [rowSpan] and [colSpan] may not be changed after the cell has
 * been created.
 */
@KrowTableDsl
interface MutableBodyCellScope : BodyCellScope {
    override var colSpan: Int
    override var rowSpan: Int
}
