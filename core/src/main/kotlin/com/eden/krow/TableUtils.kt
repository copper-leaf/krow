package com.eden.krow

import java.util.regex.Pattern

@DslMarker
annotation class TableMarker

enum class VerticalAlignment {
    TOP, BOTTOM, CENTER
}

enum class HorizontalAlignment {
    LEFT, RIGHT, CENTER
}

fun krow(init: KrowTable.() -> Unit): KrowTable {
    val table = KrowTable()
    table.init()
    return table
}

fun String?.wrap(width: Int): MutableList<String> {
    val matchList = ArrayList<String>()

    if (this != null && this.isNotBlank()) {
        if(width > 0) {
            val regex = Pattern.compile("(.{1," + width + "}(?:\\s|\$))|(.{0," + width + "})", Pattern.DOTALL)
            val regexMatcher = regex.matcher(this)
            while (regexMatcher.find()) {
                val line = regexMatcher.group().trim()
                if (line.isNotBlank()) {
                    matchList.add(line)
                }
            }
        }
    }

    if(matchList.size == 0) {
        matchList.add("" + this)
    }

    return matchList
}

fun String.padCenter(size: Int, padChar: Char = ' '): String {
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
