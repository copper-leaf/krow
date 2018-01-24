package com.eden.krow.formatters

import com.eden.krow.BorderSet
import com.eden.krow.KrowTable
import com.eden.krow.TableFormatter
import com.eden.krow.borders.SingleBorder

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
                    cellBlocks.add(CellBlock(maxWidth, rowHeight, contentRows))
                }
                rowBlocks.add(RowBlock(rowHeight, cellBlocks))
            }
        }

        // render each block line-by-line
        var output = ""
        rowBlocks.forEachIndexed { index, rowBlock ->
            if(index == 0) {
                output += rowBlock.printTopLine()
            }
            else {
                output += rowBlock.printMiddleLine()
            }
            output += rowBlock.print()
            if(index == rowBlocks.size - 1) {
                output += rowBlock.printBottomLine()
            }
        }

        return output
    }

    private inner class CellBlock(val width: Int, val height: Int, val contentRows: ArrayList<String>) {
        fun printAt(index: Int): String {
            return contentRows[index]
        }
    }

    private inner class RowBlock(val height: Int, val cellBlocks: ArrayList<CellBlock>) {
        fun print(): String {
            var output = ""
            for(i in 0 until height) {
                cellBlocks.forEachIndexed { index, cellBlock ->
                    output += v
                    output += cellBlock.printAt(i)
                }
                output += "$v$nl"
            }

            return output
        }

        fun printTopLine(): String {
            return printLine(tl, t, tr)
        }

        fun printMiddleLine(): String {
            return printLine(cl, c, cr)
        }

        fun printBottomLine(): String {
            return printLine(bl, b, br)
        }

        fun printLine(l: Char, c: Char, r: Char): String {
            var output = ""
            cellBlocks.forEachIndexed { index, cellBlock ->
                if(index == 0) {
                    output += l
                }
                else {
                    output += c
                }
                output += "".padStart(cellBlock.width, h)
            }
            output += "$r$nl"

            return output
        }
    }



}