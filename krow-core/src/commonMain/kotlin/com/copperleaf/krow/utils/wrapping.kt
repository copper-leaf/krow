package com.copperleaf.krow.utils

import com.copperleaf.krow.model.HorizontalAlignment

private sealed class WrapToken {
    object Space : WrapToken()
    object Newline : WrapToken()
    data class Word(val word: String) : WrapToken()
}

private fun String.tokenize(): List<WrapToken> {
    val tokens = mutableListOf<WrapToken>()
    var temp = ""

    val addTempToToken = {
        if (temp.isNotEmpty()) {
            tokens.add(WrapToken.Word(temp))
            temp = ""
        }
    }

    toCharArray().forEach {
        when (it) {
            '\n' -> {
                addTempToToken()
                tokens.add(WrapToken.Newline)
            }
            ' ' -> {
                addTempToToken()
                if (tokens.lastOrNull() !is WrapToken.Space) {
                    tokens.add(WrapToken.Space)
                } else {
                    // repeated whitespace, just treat it all as one
                }
            }
            else -> {
                temp += it
            }
        }
    }

    addTempToToken()

    return tokens.toList()
}

// TODO: handle words that are need to be broken down into more than 2 lines
fun String?.wrap(
    maxWidth: Int,
    padChar: String = " ",
    alignment: HorizontalAlignment = HorizontalAlignment.LEFT
): String {
    if (this.isNullOrBlank()) return createLineOf(padChar, maxWidth)

    val lines = mutableListOf<String>()

    val currentLine = StringBuilder(maxWidth)
    var cursor = 0

    val addLineToBuffer = {
        lines.add(currentLine.toString().trim().padHorizontal(maxWidth, padChar, alignment))
        currentLine.clear()
        cursor = 0
    }

    val tokenList = this
        .tokenize()
        .filterNot { it is WrapToken.Space } // don't actually care about spaces, we add them in manually, not based on input spaces
        .toMutableList()

    while (tokenList.isNotEmpty()) {
        val token = tokenList.removeFirst()
        val result = token.handleToken(maxWidth, cursor)

        if (result.advanceLine) {
            addLineToBuffer()
        }
        if (result.textToAppend != null) {
            // write word on current line
            currentLine.append("${result.textToAppend} ")
            cursor += result.textToAppend.length + 1
        }
        if (result.tokensToPushBack != null) {
            tokenList.addAll(0, result.tokensToPushBack.map { WrapToken.Word(it) })
        }
    }

    // add the final line to the buffer
    addLineToBuffer()

    return lines.joinToString(separator = "\n")
}

private data class HandleTokenResult(
    val advanceLine: Boolean,
    val textToAppend: String? = null,
    val tokensToPushBack: List<String>? = null,
)

private fun WrapToken.handleToken(
    maxWidth: Int,
    cursor: Int
): HandleTokenResult {
    return when (this) {
        is WrapToken.Newline -> handleNewlineToken(maxWidth, cursor)
        is WrapToken.Space -> handleSpaceToken(maxWidth, cursor)
        is WrapToken.Word -> handleWordToken(maxWidth, cursor)
    }
}

@Suppress("UNUSED_PARAMETER", "UNUSED")
private fun WrapToken.Newline.handleNewlineToken(
    maxWidth: Int,
    cursor: Int
): HandleTokenResult {
    return HandleTokenResult(
        advanceLine = true,
    )
}

@Suppress("UNUSED_PARAMETER", "UNUSED")
private fun WrapToken.Space.handleSpaceToken(
    maxWidth: Int,
    cursor: Int
): HandleTokenResult {
    return HandleTokenResult(
        advanceLine = false,
    )
}

private fun WrapToken.Word.handleWordToken(
    maxWidth: Int,
    cursor: Int
): HandleTokenResult {
    if (word.canFitOnLine(maxWidth, cursor)) return HandleTokenResult(
        advanceLine = false,
        textToAppend = word
    )
    if (word.canFitOnNextLine(maxWidth, cursor)) return HandleTokenResult(
        advanceLine = true,
        textToAppend = word
    )

    // cannot fit on this line, and it's longer than the allowed length. We must chop it down and hyphenate it
    return HandleTokenResult(
        advanceLine = false,
        tokensToPushBack = word.getHyphenatedSplits(maxWidth, cursor)
    )
}

private fun String.canFitOnLine(
    maxWidth: Int,
    cursor: Int
): Boolean {
    val spaceLeftOnLine = maxWidth - cursor
    return length <= spaceLeftOnLine
}

@Suppress("UNUSED_PARAMETER")
private fun String.canFitOnNextLine(
    maxWidth: Int,
    cursor: Int
): Boolean {
    return length <= maxWidth
}

/**
 * Decide how to hypenate this word so it is maximally readable. The resulting split will:
 *
 * - include at least 1 character plus a hyphen on the current line, if it will fit. Otherwise, move it all to the next line
 * - The remaining strings, all split such that they will each be at max [maxWidth] after including any needed hyphens
 * - include a hyphen at the end of each piece, except for the last piece
 */
internal fun String.getHyphenatedSplits(
    maxWidth: Int,
    cursor: Int
): List<String> {
    if (this.length <= maxWidth) return listOf(this)

    val hasSplitOnCurrentLine = (maxWidth - cursor) >= 2

    val firstSplitSize = if (hasSplitOnCurrentLine) {
        (maxWidth - cursor) - 1
    } else {
        0
    }

    val firstTokenSplit = this.substring(0, firstSplitSize)
    val remainingString = this.substring(firstSplitSize)
    val chunks = remainingString.chunked(maxWidth - 1)
    val remainingSplits = chunks
        .mapIndexed { index, it ->
            if (it.length == maxWidth - 1 && index != chunks.lastIndex) {
                "$it-"
            } else {
                it
            }
        }

    val actualRemainingSplits = if (remainingSplits.last().length == 1) {
        val (secondLast, last) = remainingSplits.takeLast(2)
        remainingSplits.dropLast(2) + (secondLast.dropLast(1) + last)
    } else {
        remainingSplits
    }

    return if (hasSplitOnCurrentLine) {
        listOf("$firstTokenSplit-", *actualRemainingSplits.toTypedArray())
    } else {
        actualRemainingSplits
    }
}
