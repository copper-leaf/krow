package com.copperleaf.krow.model

sealed class Krow {
    abstract val children: List<Krow>

    data class Table(
        val colSpec: List<Int>,
        val rowSpec: List<Int>,
        var includeHeaderRow: Boolean,
        var includeLeadingColumn: Boolean,
        val rows: List<Row>
    ) : Krow() {
        override val children: List<Krow> get() = rows
    }

    class Row(
        val cells: List<Cell>
    ) : Krow() {
        constructor(vararg rows: Cell) : this(rows.toList())

        override val children: List<Krow> get() = cells
    }

    class Cell(
        val data: String,
        val rowSpan: Int = 1,
        val colSpan: Int = 1,
        val padding: Int = 1,
        val width: Int? = null,
        val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.LEFT,
        val verticalAlignment: VerticalAlignment = VerticalAlignment.TOP,
    ) : Krow() {
        override val children: List<Krow> = emptyList()
    }
}
