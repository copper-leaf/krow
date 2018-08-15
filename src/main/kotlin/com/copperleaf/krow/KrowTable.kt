package com.copperleaf.krow

import com.copperleaf.krow.formatters.AsciiTableFormatter

@TableMarker
class KrowTable(val formatter: TableFormatter<String> = AsciiTableFormatter()) : TableFormatter<String> by formatter {

    var columnNames: MutableList<String> = ArrayList()
    var rowNames: MutableList<String> = ArrayList()
    val rows: MutableList<MutableList<Cell>> = ArrayList()

    var showHeaders = true
    var showLeaders = true

    val basicCell = Cell(this, 0, 0, 0, 0, false, false)

    val tableCells: List<List<Cell>>
        get() {
            val rows = ArrayList<ArrayList<Cell>>()

            if(showHeaders) {
                val headerRow = ArrayList<Cell>()
                val headerLeaderCell = Cell(this, 0, 0, 0, 0, true, true)
                initCell(headerLeaderCell)
                headerRow.add(headerLeaderCell)
                columnNames.forEachIndexed { i, name ->
                    val headerCell = Cell(this, i, i, 0, 0, true, false)
                    headerCell.content = name
                    initCell(headerCell)
                    headerRow.add(headerCell)
                }
                rows.add(headerRow)
            }

            this.rows.forEachIndexed { y, row ->
                val newRow = ArrayList<Cell>()

                if(showLeaders) {
                    val leaderCell = Cell(this, 0, 0, y, y, false, true)
                    leaderCell.content = rowNames[y]
                    initCell(leaderCell)
                    newRow.add(leaderCell)
                }

                newRow.addAll(row)
                rows.add(newRow)
            }

            return rows
        }

    fun print(): String {
        return print(this)
    }

    fun <T> print(tableFormatter: TableFormatter<T>): T {
        return tableFormatter.print(this)
    }

    private fun expandToFit(width: Int, height: Int) {
        if(height >= rows.size) {
            for(i in rows.size .. height) {
                rows.add(ArrayList())
            }
        }

        rows.forEachIndexed { y, row ->
            if(width >= row.size) {
                for(x in row.size .. width) {
                    val cell = Cell(this, x, x, y, y, false, false)
                    initCell(cell)
                    row.add(cell)
                }
            }
        }
    }

    private fun initCell(cell: Cell) {
        cell.verticalAlignment = basicCell.verticalAlignment
        cell.horizontalAlignment = basicCell.horizontalAlignment
        cell.wrapTextAt = basicCell.wrapTextAt

        cell.paddingLeft = basicCell.paddingLeft
        cell.paddingRight = basicCell.paddingRight
        cell.paddingTop = basicCell.paddingTop
        cell.paddingBottom = basicCell.paddingBottom
    }

    fun columns(vararg columnNames: String) {
        this.columnNames = columnNames.toMutableList()
    }
    fun rows(vararg rowNames: String) {
        this.rowNames = rowNames.toMutableList()
    }

    fun cell(column: Int, row: Int, init: Cell.() -> Unit): Cell {
        expandToFit(column-1, row-1)
        rows[row-1][column-1].init()
        return rows[row-1][column-1]
    }

    fun cell(column: String, row: String, init: Cell.() -> Unit): Cell {
        var columnIndex: Int = columnNames.indexOf(column)
        var rowIndex: Int = rowNames.indexOf(row)

        if(columnIndex< 0) {
            columnNames.add(column)
            columnIndex = columnNames.size - 1
        }
        if(rowIndex < 0) {
            rowNames.add(row)
            rowIndex = rowNames.size - 1
        }

        return cell(columnIndex+1, rowIndex+1, init)
    }

    fun row(rowName: String, init: Cell.() -> Unit) {
        var rowIndex: Int = rowNames.indexOf(rowName)
        if(rowIndex >= 0) {
            basicCell.init()
            rows[rowIndex].forEach {
                it.init()
            }
        }
    }

    fun column(columnName: String, init: Cell.() -> Unit) {
        var columnIndex: Int = columnNames.indexOf(columnName)
        if(columnIndex >= 0) {
            basicCell.init()
            rows.forEach {
                it[columnIndex].init()
            }
        }
    }

    fun table(init: Cell.() -> Unit) {
        basicCell.init()
        rows.forEach {
            it.forEach { it.init() }
        }
    }

}
