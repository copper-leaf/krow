package com.copperleaf.krow.formatters.html

import com.copperleaf.krow.formatters.TableFormatter
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.utils.createLineOf

/* ktlint-disable max-line-length */
@ExperimentalStdlibApi
class HtmlTableFormatter(attrs: HtmlAttributes = DefaultHtmlAttributes()) :
    TableFormatter<String>,
    HtmlAttributes by attrs {

    override fun print(table: Krow.Table): String {
        return KrowHtmlCanvas()
            .apply {
                tag("table", id = tableId, classes = tableClasses, otherAttrs = tableAttrs) {

                    val bodyRows = if (table.includeHeaderRow) {
                        tag("thead", indentTag = false) {
                            tag("tr", classes = trClasses) {
                                table.rows.first().cells.forEachIndexed { index, cell ->
                                    val classes = buildList {
                                        addAll(thClasses)
                                        addAll(headerClasses)
                                        if (table.includeLeadingColumn && index == 0) addAll(leaderClasses)
                                    }
                                    tag("th", classes = classes, indentTag = false, newlineBeforeCloseTag = false) {
                                        append(cell.data)
                                    }
                                }
                            }
                        }
                        table.rows.drop(1)
                    } else {
                        table.rows
                    }

                    tag("tbody", indentTag = false) {
                        bodyRows.forEach { row ->
                            tag("tr", classes = trClasses) {
                                row.cells.forEachIndexed { index, cell ->
                                    val classes = buildList {
                                        addAll(tdClasses)
                                        if (table.includeLeadingColumn && index == 0) addAll(leaderClasses)
                                    }
                                    val otherAttributes = buildMap<String, String> {
                                        if (cell.rowSpan > 1) this["rowspan"] = "${cell.rowSpan}"
                                        if (cell.colSpan > 1) this["colspan"] = "${cell.colSpan}"
                                    }

                                    tag("td", classes = classes, otherAttrs = otherAttributes, indentTag = false, newlineBeforeCloseTag = false) {
                                        append(cell.data)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            .toString()
    }
}

@DslMarker
annotation class KrowHtmlCanvasBuilder

@KrowHtmlCanvasBuilder
internal class KrowHtmlCanvas {
    private val buffer = StringBuilder()
    var indent = 0

    override fun toString(): String {
        return buffer.toString()
    }

    fun append(content: String) {
        buffer.append(content)
    }

    fun tag(
        tagName: String,
        id: String? = null,
        classes: List<String>? = null,
        otherAttrs: Map<String, String?> = emptyMap(),
        indentTag: Boolean = true,
        newlineBeforeCloseTag: Boolean = true,
        block: KrowHtmlCanvas.() -> Unit
    ) {
        val allAttributes: MutableMap<String, String?> = otherAttrs.toMutableMap()

        if (!id.isNullOrBlank()) {
            allAttributes["id"] = id
        }
        if (!classes.isNullOrEmpty()) {
            allAttributes["class"] = classes.joinToString()
        }

        buffer.appendLine()
        buffer.append("${createLineOf(' ', indent)}<$tagName")

        allAttributes.forEach { (key, value) ->
            if (value.isNullOrBlank()) {
                buffer.append(" $key")
            } else {
                buffer.append(" $key=\"$value\"")
            }
        }
        buffer.append(">")

        if (indentTag) this.indent += 2
        block()
        if (indentTag) this.indent -= 2

        if (newlineBeforeCloseTag) {
            buffer.appendLine()
            buffer.append("${createLineOf(' ', indent)}</$tagName>")
        } else {
            buffer.append("</$tagName>")
        }
    }
}
