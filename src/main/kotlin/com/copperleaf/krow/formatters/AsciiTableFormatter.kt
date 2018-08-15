package com.copperleaf.krow.formatters

import com.copperleaf.krow.Cell
import com.copperleaf.krow.KrowTable
import com.copperleaf.krow.TableFormatter
import com.copperleaf.krow.borders.BorderSet
import com.copperleaf.krow.borders.SingleBorder

class AsciiTableFormatter(borders: BorderSet = SingleBorder()) : TableFormatter<String>, BorderSet by borders {

    override fun print(table: KrowTable): String {
        val allCells = table.tableCells
        // determine the height of each row and the width of each column


        // format cells to padded and aligned content blocks
        val rowBlocks = ArrayList<RowBlock>()
        allCells.forEach { row ->
            val cellBlocks = ArrayList<CellBlock>()
            val rowHeight = row.map { cell -> cell.height }.max()

            if(rowHeight != null) {
                row.forEachIndexed { index, cell ->

                    var maxWidth = 0
                    allCells.forEach { row ->
                        maxWidth = Math.max(maxWidth, row[index].width)
                    }

                    val contentRows = ArrayList<String>()
                    cell.alignedContent(maxWidth, rowHeight).forEach { line ->
                        contentRows.add(line)
                    }
                    cellBlocks.add(CellBlock(maxWidth, rowHeight, cell, contentRows))
                }
                rowBlocks.add(RowBlock(rowHeight, cellBlocks))
            }
        }

        // render each block line-by-line
        var output = ""
        var previousWasHeaderRow = false
        rowBlocks.forEachIndexed { index, rowBlock ->
            if(index == 0) {
                if(showT) output += rowBlock.printTopLine()
            }
            else {
                output += rowBlock.printMiddleLine(previousWasHeaderRow)
            }
            output += rowBlock.print()
            if(index == rowBlocks.size - 1) {
                if(showB) output += rowBlock.printBottomLine()
            }
            previousWasHeaderRow = rowBlock.isHeaderRow()
        }

        return output
    }

    private inner class CellBlock(val width: Int, val height: Int, val cell: Cell, val contentRows: ArrayList<String>) {
        fun printAt(index: Int): String {
            return contentRows[index]
        }
    }

    private inner class RowBlock(val height: Int, val cellBlocks: ArrayList<CellBlock>) {

        fun isHeaderRow(): Boolean {
            var header = true
            for (cellBlock in cellBlocks) {
                header = header && cellBlock.cell.header
            }
            return header
        }

        fun print(): String {
            var output = ""
            for(i in 0 until height) {
                var previousWasLeaderCell = false
                cellBlocks.forEachIndexed { index, cellBlock ->
                    if(index == 0) {
                        if(showL) output += vl
                    }
                    else {
                        if(previousWasLeaderCell) {
                            output += if(showLeader) vld else ""
                        }
                        else {
                            output += if(showV) vc else ""
                        }
                    }
                    output += cellBlock.printAt(i)
                    previousWasLeaderCell = cellBlock.cell.leader
                }
                output += if(showR) "$vr$nl" else "$nl"
            }

            return output
        }

        fun printTopLine(): String {
            return printLine(tl, ti, tr, th, tld)
        }

        fun printMiddleLine(isHeaderRow: Boolean): String {
            return if(isHeaderRow) {
                if(showHeader) printLine(hl, hi, hr, hh, hld) else ""
            }
            else {
                if(showH) printLine(cl, ci, cr, ch, cld) else ""
            }
        }

        fun printBottomLine(): String {
            return printLine(bl, bi, br, bh, bld)
        }

        fun printLine(l: Char, c: Char, r: Char, h: Char, vld: Char): String {
            var output = ""
            var previousWasLeaderCell = false
            cellBlocks.forEachIndexed { index, cellBlock ->
                if(index == 0) {
                    if(showL) output += if(previousWasLeaderCell) vld else l
                }
                else {
                    if(previousWasLeaderCell) {
                        output += if(showLeader) vld else ""
                    }
                    else {
                        output += if(showV) c else ""
                    }
                }
                output += "".padStart(cellBlock.width, h)

                previousWasLeaderCell = cellBlock.cell.leader
            }
            output += if(showR) "$r$nl" else "$nl"

            return output
        }
    }

}