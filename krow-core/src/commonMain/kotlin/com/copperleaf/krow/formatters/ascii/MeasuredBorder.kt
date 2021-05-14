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
            return borderSet.h.first()
        }
        if (leftCellsAreSame && rightCellsAreSame) {
            // we are between two vertically-expanded cells, use a vertical rule
            return borderSet.v.first()
        }
        if (topCellsAreSame && leftCellsAreSame) {
            return borderSet.tl.first()
        }
        if (topCellsAreSame && rightCellsAreSame) {
            return borderSet.tr.first()
        }
        if (bottomCellsAreSame && leftCellsAreSame) {
            return borderSet.bl.first()
        }
        if (bottomCellsAreSame && rightCellsAreSame) {
            return borderSet.br.first()
        }
        if (topCellsAreSame) {
            return borderSet.ti.first()
        }
        if (bottomCellsAreSame) {
            return borderSet.bi.first()
        }
        if (leftCellsAreSame) {
            return borderSet.cl.first()
        }
        if (rightCellsAreSame) {
            return borderSet.cr.first()
        }

        return borderSet.ci.first()
    }
}
