package com.copperleaf.krow.utils

import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment
import kotlin.math.ceil
import kotlin.math.floor

// Vertical Alignment
// ---------------------------------------------------------------------------------------------------------------------

fun String.padTop(height: Int, padChar: Char = ' '): String {
    val contentLines = this.lines().toMutableList()
    val width = contentLines.maxOf { it.length }
    val heightToAdd = height - contentLines.size

    return (
        createBlankLinesOf(padChar, width, heightToAdd) +
            contentLines
        ).joinToString(separator = "\n")
}

fun String.padBottom(height: Int, padChar: Char = ' '): String {
    val contentLines = this.lines().toMutableList()
    val width = contentLines.maxOf { it.length }
    val heightToAdd = height - contentLines.size

    return (
        contentLines +
            createBlankLinesOf(padChar, width, heightToAdd)
        ).joinToString(separator = "\n")
}

fun String.padCenterVertical(height: Int, padChar: Char = ' '): String {
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
    padChar: Char = ' ',
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
    padChar: Char,
    alignment: HorizontalAlignment = HorizontalAlignment.LEFT
): String {
    return when (alignment) {
        HorizontalAlignment.LEFT -> {
            padEnd(width, padChar)
        }
        HorizontalAlignment.RIGHT -> {
            padStart(width, padChar)
        }
        HorizontalAlignment.CENTER -> {
            padCenterHorizontal(width, padChar)
        }
    }
}

fun String.padCenterHorizontal(size: Int, padChar: Char = ' '): String {
    var str = this
    if (size <= 0) {
        return str
    }
    val strLen = str.length
    val pads = size - strLen
    if (pads <= 0) {
        return str
    }
    str = str.padStart(strLen + pads / 2, padChar)
    str = str.padEnd(size, padChar)
    return str
}
