package com.copperleaf.krow.formatters.ascii

import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.utils.BorderSet

data class MeasuredCell(
    val cell: Krow.Cell = Krow.Cell(""),

    val startRowIndex: Int,
    val endRowIndex: Int,
    val startColumnIndex: Int,
    val endColumnIndex: Int,

    val colSpan: Int = 0,
    val rowSpan: Int = 0,

    val width: Int = 0,
    val height: Int = 0,

    val bufferLine: Int = 0,
    val bufferColumn: Int = 0,

    val rowStartDescriptor: RowDescriptor,
    val rowEndDescriptor: RowDescriptor,
    val columnStartDescriptor: ColumnDescriptor,
    val columnEndDescriptor: ColumnDescriptor,
) {
    fun leftVerticalRule(borderSet: BorderSet): Char {
        return borderSet.vl.first()
    }

    fun rightVerticalRule(borderSet: BorderSet): Char {
        return borderSet.vr.first()
    }

    fun topHorizontalRule(borderSet: BorderSet): Char {
        return borderSet.th.first()
    }

    fun bottomHorizontalRule(borderSet: BorderSet): Char {
        return borderSet.bh.first()
    }
}
