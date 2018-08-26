package com.copperleaf.krow

@TableMarker
class Cell(
        val table: KrowTable,
        var startColumn: Int,
        var endColumn: Int,
        var startRow: Int,
        var endRow: Int,
        val header: Boolean,
        val leader: Boolean
) {
    var wrapTextAt: Int = 0
    var content: String = ""
    var verticalAlignment: VerticalAlignment = VerticalAlignment.TOP
    var horizontalAlignment: HorizontalAlignment = HorizontalAlignment.LEFT

    var paddingLeft: Int = 1
    var paddingRight: Int = 1
    var paddingTop: Int = 0
    var paddingBottom: Int = 0

    val width: Int
        get() {
            return paddingLeft + paddingRight + contentWidth
        }
    val height: Int
        get() {
            return paddingTop + paddingBottom + contentHeight
        }

    val contentWidth: Int
        get() {
            return if (content.length > wrapTextAt) {
                wrapTextAt
            } else {
                content.length
            }
        }

    val contentHeight: Int
        get() {
            return if (content.length > wrapTextAt) {
                content.wrap(wrapTextAt).size
            } else {
                1
            }
        }

    fun alignedContent(width: Int, height: Int): List<String> {
        val contentWidth = width - (paddingLeft + paddingRight)
        val contentLines = content.wrap(contentWidth)

        for(i in 0 until contentLines.size) {
            var leftPad = "".padStart(paddingLeft)
            var rightPad = "".padStart(paddingRight)
            var currentLine = contentLines[i]
            var alignedContent = ""

            if(horizontalAlignment == HorizontalAlignment.LEFT) {
                alignedContent = currentLine.padEnd(contentWidth)
            }
            else if(horizontalAlignment == HorizontalAlignment.RIGHT) {
                alignedContent = currentLine.padStart(contentWidth)
            }
            else if(horizontalAlignment == HorizontalAlignment.CENTER) {
                alignedContent = currentLine.padCenter(contentWidth)
            }

            contentLines[i] = leftPad + alignedContent + rightPad
        }

        if(contentLines.size < height) {
            if (verticalAlignment == VerticalAlignment.TOP) {
                for(i in contentLines.size until height) {
                    contentLines.add("".padStart(width))
                }
            } else if (verticalAlignment == VerticalAlignment.BOTTOM) {
                for(i in contentLines.size until height) {
                    contentLines.add(0, "".padStart(width))
                }
            } else if (verticalAlignment == VerticalAlignment.CENTER) {
                for(i in contentLines.size until height) {
                    if(i < height/2) {
                        contentLines.add(0, "".padStart(width))
                    }
                    else {
                        contentLines.add("".padStart(width))
                    }
                }
            }
        }

        return contentLines
    }

}