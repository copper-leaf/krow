package com.copperleaf.krow.formatters.ascii

import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.model.TableSpecMeasurement
import com.copperleaf.krow.utils.BorderSet

data class MeasuredCell(
    val cell: Krow.Cell,

    val heightMeasurement: TableSpecMeasurement,
    val widthMeasurement: TableSpecMeasurement,

    val startRowIndex: Int = heightMeasurement.startIndex,
    val endRowIndex: Int = heightMeasurement.endIndex,
    val startColumnIndex: Int = widthMeasurement.startIndex,
    val endColumnIndex: Int = widthMeasurement.endIndex,

    val colSpan: Int = 0,
    val rowSpan: Int = 0,

    val width: Int = widthMeasurement.totalSize,
    val height: Int = heightMeasurement.totalSize,

    val bufferLine: Int = heightMeasurement.bufferPosition,
    val bufferColumn: Int = widthMeasurement.bufferPosition,

    val rowStartDescriptor: RowDescriptor,
    val rowEndDescriptor: RowDescriptor,
    val columnStartDescriptor: ColumnDescriptor,
    val columnEndDescriptor: ColumnDescriptor,
) {
    fun leftVerticalRule(borderSet: BorderSet): Char {
        return borderSet.vl
    }

    fun rightVerticalRule(borderSet: BorderSet): Char {
        return borderSet.vr
    }

    fun topHorizontalRule(borderSet: BorderSet): Char {
        return borderSet.th
    }

    fun bottomHorizontalRule(borderSet: BorderSet): Char {
        return borderSet.bh
    }
}
