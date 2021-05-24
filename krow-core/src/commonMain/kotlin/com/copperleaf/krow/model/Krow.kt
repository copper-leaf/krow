package com.copperleaf.krow.model

data class TableSpecMeasurement(
    val startIndex: Int,
    val endIndex: Int,
    val totalSize: Int,
    val bufferPosition: Int,
)

data class TableSpec(
    internal val names: List<String>,
    internal val sizes: List<Int>,
) {
    fun getFullMeasurement(): TableSpecMeasurement {
        return measureRange(names.first(), names.size, 1)
    }

    fun measurePoint(index: Int): TableSpecMeasurement {
        val bufferSizeSublistBeforeThisPosition = sizes.subList(0, index)
        val bufferPosition = bufferSizeSublistBeforeThisPosition.sum() + index

        return TableSpecMeasurement(
            startIndex = index,
            endIndex = index,
            totalSize = bufferPosition,
            bufferPosition = bufferPosition
        )
    }

    fun measureRange(start: String, span: Int): TableSpecMeasurement {
        return measureRange(start, span, -1)
    }

    private fun measureRange(start: String, span: Int, totalSizeAdjust: Int): TableSpecMeasurement {
        val startIndex = names.indexOf(start)
        val endIndex = startIndex + span

        val sizesSublist = sizes.subList(startIndex, endIndex)
        val totalSize = sizesSublist.sum() + (span + totalSizeAdjust)

        val bufferSizeSublistBeforeThisPosition = sizes.subList(0, startIndex)
        val bufferPosition = bufferSizeSublistBeforeThisPosition.sum() + startIndex

        return TableSpecMeasurement(
            startIndex = startIndex,
            endIndex = endIndex,
            totalSize = totalSize,
            bufferPosition = bufferPosition
        )
    }
}

fun List<Pair<String, Int>>.toTableSpec() : TableSpec {
    val names = this.map { it.first }
    val values = this.map { it.second }

    return TableSpec(names, values)
}

sealed class Krow {
    abstract val children: List<Krow>

    data class Table(
        val colSpec: TableSpec,
        val rowSpec: TableSpec,
        var includeHeaderRow: Boolean,
        var includeLeadingColumn: Boolean,
        val rows: List<Row>
    ) : Krow() {
        override val children: List<Krow> get() = rows
    }

    data class Row(
        val rowName: String,
        val cells: List<Cell>
    ) : Krow() {
        override val children: List<Krow> get() = cells
    }

    data class Cell(
        val data: String,
        val rowSpan: Int = 1,
        val colSpan: Int = 1,
        val rowName: String,
        val columnName: String,
        val padding: Int = 1,
        val width: Int? = null,
        val horizontalAlignment: HorizontalAlignment = HorizontalAlignment.LEFT,
        val verticalAlignment: VerticalAlignment = VerticalAlignment.TOP,
    ) : Krow() {
        override val children: List<Krow> = emptyList()

        override fun toString(): String {
            return "($rowName, $columnName): $data"
        }
    }
}
