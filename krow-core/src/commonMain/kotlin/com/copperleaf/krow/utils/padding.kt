package com.copperleaf.krow.utils

import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment
import kotlin.math.ceil
import kotlin.math.floor

// Vertical Alignment
// ---------------------------------------------------------------------------------------------------------------------

fun String.padTop(height: Int, padChar: String = " "): String {
    val contentLines = this.lines().toMutableList()
    val width = contentLines.maxOf { it.length }
    val heightToAdd = height - contentLines.size

    return (
        createBlankLinesOf(padChar, width, heightToAdd) +
            contentLines
        ).joinToString(separator = "\n")
}

fun String.padBottom(height: Int, padChar: String = " "): String {
    val contentLines = this.lines().toMutableList()
    val width = contentLines.maxOf { it.length }
    val heightToAdd = height - contentLines.size

    return (
        contentLines +
            createBlankLinesOf(padChar, width, heightToAdd)
        ).joinToString(separator = "\n")
}

fun String.padCenterVertical(height: Int, padChar: String = " "): String {
    val contentLines = this.lines().toMutableList()
    val width = contentLines.maxOf { it.length }
    val heightToAdd = height - contentLines.size
    val topLines = floor(heightToAdd / 2f).toInt()
    val bottomLines = ceil(heightToAdd / 2f).toInt()

    return (
        createBlankLinesOf(padChar, width, topLines) +
            contentLines +
            createBlankLinesOf(padChar, width, bottomLines)
        ).joinToString(separator = "\n")
}

fun String.padVertical(
    height: Int,
    padChar: String = " ",
    alignment: VerticalAlignment = VerticalAlignment.TOP
): String {
    return when (alignment) {
        VerticalAlignment.TOP -> padBottom(height, padChar)
        VerticalAlignment.BOTTOM -> padTop(height, padChar)
        VerticalAlignment.CENTER -> padCenterVertical(height, padChar)
    }
}

// Horizontal Alignment
// ---------------------------------------------------------------------------------------------------------------------

fun String.padHorizontal(
    width: Int,
    padChar: String,
    alignment: HorizontalAlignment = HorizontalAlignment.LEFT
): String {
    return when (alignment) {
        HorizontalAlignment.LEFT -> {
            padEnd(width, padChar.first())
        }
        HorizontalAlignment.RIGHT -> {
            padStart(width, padChar.first())
        }
        HorizontalAlignment.CENTER -> {
            padCenterHorizontal(width, padChar)
        }
    }
}

fun String.padCenterHorizontal(size: Int, padChar: String = " "): String {
    var str = this
    if (size <= 0) {
        return str
    }
    val strLen = str.length
    val pads = size - strLen
    if (pads <= 0) {
        return str
    }
    str = str.padStart(strLen + pads / 2, padChar.first())
    str = str.padEnd(size, padChar.first())
    return str
}
