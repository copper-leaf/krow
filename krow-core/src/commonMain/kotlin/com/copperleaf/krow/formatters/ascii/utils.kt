package com.copperleaf.krow.formatters.ascii

import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.utils.createLineOf
import com.copperleaf.krow.utils.padVertical
import com.copperleaf.krow.utils.wrap

//val Krow.Row.colSpec: List<Int> get() = cells.map { it.intrinsicWidthWithPadding }
//val List<Krow.Row>.rowSpec: Map<String, Int> get() = this.map { it.cells.maxOf { it.intrinsicHeight } }
//val Krow.Row.height: Int get() = cells.maxOf { it.intrinsicHeight }

val Krow.Cell.intrinsicWidthWithPadding: Int
    get() {
        val contentWidth = if (width != null) {
            width
        } else {
            data.lines().maxOf { it.length }
        }

        return contentWidth + (2 * padding)
    }
val Krow.Cell.intrinsicHeight: Int get() = data.lines().size

fun Krow.Cell.printContentWithPadding(maxWidth: Int, height: Int): String {
    return data.wrap(maxWidth - (padding * 2), alignment = horizontalAlignment)
        .padVertical(height, alignment = verticalAlignment, padChar = ' ')
        .lines()
        .joinToString(separator = "\n") {
            createLineOf(' ', padding) + it + createLineOf(' ', padding)
        }
}
