package com.copperleaf.krow.builder

import com.copperleaf.krow.builder.KrowTableBuilderLayout.Companion.HEADER_ROW_NAME
import com.copperleaf.krow.builder.KrowTableBuilderLayout.Companion.LEADING_COLUMN_NAME
import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.VerticalAlignment

internal class TableScopeImpl(
    private val layout: KrowTableBuilderLayout = KrowTableBuilderLayout(),
    private val header: HeaderScopeImpl = HeaderScopeImpl(layout),
    private val body: BodyScopeImpl = BodyScopeImpl(layout),
) : TableScope {

    override var includeHeaderRow: Boolean = true
    override var includeLeadingColumn: Boolean = true

    override fun header(block: HeaderScope.() -> Unit) {
        header.block()
    }

    override fun body(block: BodyScope.() -> Unit) {
        body.block()
    }

    fun build(): Krow.Table {
        val headerRow = header.build(includeLeadingColumn)
        val bodyRows = body.build(header.headerRow, includeLeadingColumn)

        // calculate row heights
        return Krow.Table(
            headerRow = headerRow,
            bodyRows = bodyRows,
            includeHeaderRow = includeHeaderRow,
            includeLeadingColumn = includeLeadingColumn,
        )
    }
}

internal class HeaderScopeImpl(
    val layout: KrowTableBuilderLayout,
    val headerRow: HeaderRowScopeImpl = HeaderRowScopeImpl(layout)
) : HeaderScope {

    override fun row(block: HeaderRowScope.() -> Unit) {
        headerRow.block()
    }

    internal operator fun get(columnName: String): HeaderCellScopeImpl? {
        return headerRow[columnName]
    }

    fun build(includeLeadingColumn: Boolean): Krow.Row {
        return headerRow.build(includeLeadingColumn)
    }
}

internal class HeaderRowScopeImpl(
    val layout: KrowTableBuilderLayout
) : HeaderRowScope {

    internal val columns = mutableListOf<HeaderCellScopeImpl>()

    override fun column(columnName: String?, block: HeaderCellScope.() -> Unit): HeaderCellScope {
        val (isNew, position) = layout.getOrCreateColumn(columnName)

        return if (isNew) {
            val cellId = layout.getCellAt(
                rowName = HEADER_ROW_NAME,
                columnName = layout.getColumnName(position),
            )

            val newCell = HeaderCellScopeImpl(
                columnName = columnName ?: cellId.columnName,
                content = columnName ?: cellId.columnName
            )

            columns.add(newCell)

            newCell
        } else {
            columns[position]
        }.apply(block)
    }

    internal operator fun get(columnName: String): HeaderCellScopeImpl? {
        return columns.firstOrNull { it.columnName == columnName }
    }

    fun build(includeLeadingColumn: Boolean): Krow.Row {
        val ownColumnCells = layout
            .getRowCells(HEADER_ROW_NAME)
            .map { cellId ->
                // use preconfigured column, or create an empty one
                columns
                    .firstOrNull { it.columnName == cellId.columnName }
                    ?: HeaderCellScopeImpl(
                        columnName = cellId.columnName,
                        content = cellId.columnName
                    )
            }
            .map { it.build() }

        val actualRowCells = if (includeLeadingColumn) {
            val headerRowColumn = Krow.Cell(
                data = "",
                rowName = HEADER_ROW_NAME,
                columnName = LEADING_COLUMN_NAME,
            )

            listOf(headerRowColumn) + ownColumnCells
        } else {
            ownColumnCells
        }

        return Krow.Row(
            rowName = HEADER_ROW_NAME,
            cells = actualRowCells
        )
    }
}

internal class BodyScopeImpl(
    private val layout: KrowTableBuilderLayout
) : BodyScope {
    internal val rows = mutableListOf<BodyRowScopeImpl>()

    override fun row(rowName: String?, block: BodyRowScope.() -> Unit) {
        val (isNew, rowIndex) = layout.getOrCreateRow(rowName)

        if (isNew) {
            val newRow = BodyRowScopeImpl(
                layout = layout,
                rowName = rowName ?: "$rowIndex"
            )
            rows.add(newRow)
            newRow
        } else {
            rows.first { it.rowName == rowName }
        }.block()
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

internal class BodyRowScopeImpl(
    private val layout: KrowTableBuilderLayout,
    override var rowName: String
) : BodyRowScope {

    internal val cells = mutableListOf<BodyCellScopeImpl>()

    override fun cell(cellContent: String?, block: MutableBodyCellScope.() -> Unit): BodyCellScope {
        val newCell = MutableBodyCellScopeImpl(
            rowName = rowName,
            content = cellContent ?: ""
        )
        newCell.block()

        val position = layout.placeCellInRow(
            rowName = rowName,
            rowSpan = newCell.rowSpan,
            colSpan = newCell.colSpan
        )

        val committedCell = newCell.commit(position.columnName)

        cells.add(committedCell)

        return committedCell
    }

    override fun cellAt(
        columnName: String,
        cellContent: String?,
        block: MutableBodyCellScope.() -> Unit
    ): BodyCellScope {
        return try {
            val position = layout.getCellAt(
                rowName = rowName,
                columnName = columnName,
            )
            // cell exists, configure existing one
            cells.first { it.columnName == columnName }.apply(block)
        } catch (e: Exception) {
            // cell does not exist, create and add a new one

            val newCell = MutableBodyCellScopeImpl(
                rowName = rowName,
                content = cellContent ?: ""
            )
            newCell.block()

            val position = layout.placeCellExact(
                rowName = rowName,
                columnName = columnName,
                rowSpan = newCell.rowSpan,
                colSpan = newCell.colSpan
            )

            val committedCell = newCell.commit(position.columnName)

            cells.add(committedCell)

            committedCell
        }
    }

    internal operator fun get(columnIndex: Int): BodyCellScopeImpl {
        return cells[columnIndex]
    }

    fun build(headerRow: HeaderRowScopeImpl, includeLeadingColumn: Boolean): Krow.Row {
        val dataCells = cells.map { it.build(headerRow) }
        val cells = if (includeLeadingColumn) {
            listOf(
                Krow.Cell(
                    data = rowName,
                    rowName = rowName,
                    columnName = LEADING_COLUMN_NAME
                )
            ) + dataCells
        } else {
            dataCells
        }

        return Krow.Row(
            rowName = rowName,
            cells = cells
        )
    }
}

internal class HeaderCellScopeImpl(
    override val columnName: String,
    override var content: String,
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
            rowName = HEADER_ROW_NAME,
            columnName = columnName
        )
    }
}

internal class BodyCellScopeImpl(
    var rowName: String,
    var columnName: String,
    override var content: String,
    override var verticalAlignment: VerticalAlignment?,
    override var horizontalAlignment: HorizontalAlignment?,
    val initialColSpan: Int,
    val initialRowSpan: Int
) : MutableBodyCellScope {
    override var colSpan: Int
        get() = initialColSpan
        set(_) {
            error("colSpan cannot be changed after creation")
        }
    override var rowSpan: Int
        get() = initialRowSpan
        set(_) {
            error("rowSpan cannot be changed after creation")
        }

    fun build(headerRow: HeaderRowScopeImpl): Krow.Cell {
        val columnCell = headerRow[columnName]

        return Krow.Cell(
            data = content,
            rowSpan = rowSpan,
            colSpan = colSpan,
            horizontalAlignment = horizontalAlignment ?: columnCell?.horizontalAlignment ?: HorizontalAlignment.LEFT,
            verticalAlignment = verticalAlignment ?: columnCell?.verticalAlignment ?: VerticalAlignment.TOP,
            rowName = rowName,
            columnName = columnName
        )
    }
}

internal class MutableBodyCellScopeImpl(
    var rowName: String,
    override var content: String,
    override var verticalAlignment: VerticalAlignment? = null,
    override var horizontalAlignment: HorizontalAlignment? = null,
    override var colSpan: Int = 1,
    override var rowSpan: Int = 1
) : MutableBodyCellScope {
    fun commit(columnName: String): BodyCellScopeImpl {
        return BodyCellScopeImpl(
            rowName = rowName,
            columnName = columnName,
            content = content,
            verticalAlignment = verticalAlignment,
            horizontalAlignment = horizontalAlignment,
            initialColSpan = colSpan,
            initialRowSpan = rowSpan,
        )
    }
}
