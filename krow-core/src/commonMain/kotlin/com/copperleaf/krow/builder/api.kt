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
    fun rows(vararg rows: Pair<String, List<String>>, block: MutableBodyCellScope.() -> Unit = {})
    fun rows(vararg rows: List<String>, block: MutableBodyCellScope.() -> Unit = {})

    fun cellAt(rowName: String, columnName: String, block: MutableBodyCellScope.() -> Unit): BodyCellScope
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
    fun rows(vararg rows: Pair<String, List<String>>, block: MutableBodyCellScope.() -> Unit = {})
    fun rows(vararg rows: List<String>, block: MutableBodyCellScope.() -> Unit = {})

    fun cellAt(rowName: String, columnName: String, block: MutableBodyCellScope.() -> Unit): BodyCellScope
}

@TableMarker
interface BodyRowScope {
    val rowName: String
    fun cell(cellContent: String? = null, block: MutableBodyCellScope.() -> Unit = {}): BodyCellScope
    fun cell(columnName: String, cellContent: String? = null, block: MutableBodyCellScope.() -> Unit = {}): BodyCellScope
    fun cells(vararg cellContents: String, block: MutableBodyCellScope.() -> Unit = {})
}

@TableMarker
interface HeaderCellScope {
    var width: Int?
    val columnName: String
    var verticalAlignment: VerticalAlignment?
    var horizontalAlignment: HorizontalAlignment?
    var content: String
}

@TableMarker
interface BodyCellScope {
    var verticalAlignment: VerticalAlignment?
    var horizontalAlignment: HorizontalAlignment?
    var content: String
    val colSpan: Int
    val rowSpan: Int
}

@TableMarker
interface MutableBodyCellScope : BodyCellScope {
    override var colSpan: Int
    override var rowSpan: Int
}

fun krow(block: TableScope.() -> Unit): Krow.Table {
    return TableScopeImpl().apply(block).build()
}
