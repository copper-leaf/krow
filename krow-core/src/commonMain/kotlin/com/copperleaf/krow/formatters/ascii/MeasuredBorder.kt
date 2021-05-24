package com.copperleaf.krow.formatters.ascii

import com.copperleaf.krow.utils.BorderSet

data class MeasuredBorder(
    val bufferLine: Int = 0,
    val bufferColumn: Int = 0,

    val topCellsAreSame: Boolean,
    val bottomCellsAreSame: Boolean,
    val leftCellsAreSame: Boolean,
    val rightCellsAreSame: Boolean,
) {
    fun brush(borderSet: BorderSet): Char? {
        if (topCellsAreSame && bottomCellsAreSame && leftCellsAreSame && rightCellsAreSame) {
            // all surrounding cells are the same, don't draw this point
            return null
        }
        if (topCellsAreSame && bottomCellsAreSame) {
            // we are between two horizontally-expanded cells, use a horizontal rule
            return borderSet.h
        }
        if (leftCellsAreSame && rightCellsAreSame) {
            // we are between two vertically-expanded cells, use a vertical rule
            return borderSet.v
        }
        if (topCellsAreSame && leftCellsAreSame) {
            return borderSet.tl
        }
        if (topCellsAreSame && rightCellsAreSame) {
            return borderSet.tr
        }
        if (bottomCellsAreSame && leftCellsAreSame) {
            return borderSet.bl
        }
        if (bottomCellsAreSame && rightCellsAreSame) {
            return borderSet.br
        }
        if (topCellsAreSame) {
            return borderSet.ti
        }
        if (bottomCellsAreSame) {
            return borderSet.bi
        }
        if (leftCellsAreSame) {
            return borderSet.cl
        }
        if (rightCellsAreSame) {
            return borderSet.cr
        }

        return borderSet.ci
    }
}
