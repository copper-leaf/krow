package com.copperleaf.krow.formatters.ascii

import com.copperleaf.krow.formatters.TableFormatter
import com.copperleaf.krow.formatters.ascii.internal.measureCells
import com.copperleaf.krow.formatters.ascii.internal.measureColSpec
import com.copperleaf.krow.formatters.ascii.internal.measureCorners
import com.copperleaf.krow.formatters.ascii.internal.measureEdges
import com.copperleaf.krow.formatters.ascii.internal.measureRowSpec
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.utils.BorderSet
import com.copperleaf.krow.utils.KrowCanvas
import com.copperleaf.krow.utils.SingleBorder

class AsciiTableFormatter(
    private val borders: BorderSet = SingleBorder()
) : TableFormatter<String> {

    override fun print(table: Krow.Table): String {
        // ----- step 1: measurement -----
        //   a) measure width of columns, based on hardcoded specification and natural width for content in each cell
        val colSpec = measureColSpec(table)

        //   b) given column widths, determine actual height of each row, and use that setup rowSpec
        val rowSpec = measureRowSpec(table, colSpec)

        //   c) measure actual width of cells, given column-width constraints
        val measuredCells = measureCells(table, rowSpec, colSpec)

        //   d) iterate through grid's actual corner and edge positions and determine context for each location
        val measuredCorners = measureCorners(measuredCells, rowSpec, colSpec)
        val measuredEdges = measureEdges(measuredCells, rowSpec, colSpec)

        // ----- step 2: prepare canvas for drawing -----
        val totalWidth = colSpec.getFullMeasurement().totalSize
        val totalHeight = rowSpec.getFullMeasurement().totalSize
        val canvas = KrowCanvas(totalWidth, totalHeight)

        // ----- step 3: drawing -----
        measuredEdges.forEach { it.draw(canvas, borders) }
        measuredCorners.forEach { it.draw(canvas, borders) }
        measuredCells.forEach { it.draw(canvas, borders) }

        return canvas.toString()
    }
}
