package com.copperleaf.krow.builder

import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.VerticalAlignment

@DslMarker
annotation class TableMarker

@TableMarker
interface TableScope {
    var includeHeaderRow: Boolean
    var includeLeadingColumn: Boolean

    fun header(block: HeaderScope.() -> Unit)
    fun headerColumns(vararg columnNames: String, block: HeaderCellScope.() -> Unit = {})

    fun body(block: BodyScope.() -> Unit)
    fun row(rowName: String? = null, block: BodyRowScope.() -> Unit)
    fun rows(vararg rows: Pair<String, List<String>>, block: BodyCellScope.() -> Unit = {})
    fun rows(vararg rows: List<String>, block: BodyCellScope.() -> Unit = {})

    fun cell(columnName: String, rowName: String, block: BodyCellScope.() -> Unit): BodyCellScope
    fun cell(columnIndex: Int, rowIndex: Int, block: BodyCellScope.() -> Unit): BodyCellScope
}

@TableMarker
interface HeaderScope {
    fun row(block: HeaderRowScope.() -> Unit)
    fun column(columnName: String? = null, block: HeaderCellScope.() -> Unit = {}): HeaderCellScope
    fun columns(vararg columnNames: String, block: HeaderCellScope.() -> Unit = {})
}

@TableMarker
interface HeaderRowScope {
    fun column(columnName: String? = null, block: HeaderCellScope.() -> Unit = {}): HeaderCellScope
    fun columns(vararg columnNames: String, block: HeaderCellScope.() -> Unit = {})
}

@TableMarker
interface BodyScope {
    fun row(rowName: String? = null, block: BodyRowScope.() -> Unit)
    fun rows(vararg rows: Pair<String, List<String>>, block: BodyCellScope.() -> Unit = {})
    fun rows(vararg rows: List<String>, block: BodyCellScope.() -> Unit = {})
}

@TableMarker
interface BodyRowScope {
    var rowName: String
    fun cell(cellContent: String? = null, block: BodyCellScope.() -> Unit = {}): BodyCellScope
    fun cells(vararg cellContents: String, block: BodyCellScope.() -> Unit = {})
}

@TableMarker
interface HeaderCellScope {
    var width: Int?
    var columnName: String
    var verticalAlignment: VerticalAlignment?
    var horizontalAlignment: HorizontalAlignment?
    var content: String
}

@TableMarker
interface BodyCellScope {
    var verticalAlignment: VerticalAlignment?
    var horizontalAlignment: HorizontalAlignment?
    var content: String
    var colSpan: Int
    var rowSpan: Int
}

fun krow(block: TableScope.() -> Unit): Krow.Table {
    return TableScopeImpl().apply(block).build()
}
