package com.copperleaf.krow.formatters.html

import com.copperleaf.krow.Cell
import com.copperleaf.krow.KrowTable
import com.copperleaf.krow.TableFormatter

class HtmlTableFormatter(attrs: HtmlAttributes = DefaultHtmlAttributes()) : TableFormatter<String>, HtmlAttributes by attrs {

    override fun print(table: KrowTable): String {
        val allCells = table.tableCells
        var output = ""
        output += "<table${renderTableId()}${renderTableClass()}${renderTableAttrs()}>\n"

        if(table.showHeaders) {
            output += "  <thead>\n"
            output += "  <tr${renderTrClass()}>\n"
            allCells[0].forEach {
                output += "    <th${renderThClass(it)}>" + it.content + "</th>\n"
            }
            output += "  </tr>\n"
            output += "  </thead>\n"

            output += "  <tbody>\n"

            for(i in 1 until allCells.size) {
                output += "  <tr${renderTrClass()}>\n"
                allCells[i].forEach {
                    output += "    <td${renderTdClass(it)}>" + it.content + "</td>\n"
                }
                output += "  </tr>\n"
            }

            output += "  </tbody>\n"
        }
        else {
            output += "  <tbody>\n"

            allCells.forEach { cells ->
                output += "  <tr${renderTrClass()}>\n"
                cells.forEach {
                    output += "    <td${renderTdClass(it)}>" + it.content + "</td>\n"
                }
                output += "  </tr>\n"
            }

            output += "  </tbody>\n"
        }

        output += "</table>"

        return output
    }

    private fun renderTableId() : String {
        return if(tableId.isNotBlank()) " id=\"$tableId\"" else ""
    }

    private fun renderTableClass() : String {
        return if(tableClass.isNotBlank()) " class=\"$tableClass\"" else ""
    }

    private fun renderTableAttrs() : String {
        var attrString = ""

        tableAttrs.forEach { key, value ->
            if(key.isNotBlank()) {
                if(value != null && value.isNotBlank()) {
                    attrString += " $key=\"$value\""
                }
                else {
                    attrString += " $key"
                }
            }
        }

        return attrString
    }

    private fun renderTrClass() : String {
        return if(trClass.isNotBlank()) " class=\"$trClass\"" else ""
    }

    private fun renderTdClass(cell: Cell) : String {
        var classStr = ""

        if(tdClass.isNotBlank()) classStr += "$tdClass "
        if(cell.header && headerClass.isNotBlank()) classStr += "$headerClass "
        if(cell.leader && leaderClass.isNotBlank()) classStr += "$leaderClass "

        return if(classStr.isNotBlank()) " class=\"${classStr.trim()}\"" else ""
    }

    private fun renderThClass(cell: Cell) : String {
        var classStr = ""

        if(thClass.isNotBlank()) classStr += "$thClass "
        if(cell.header && headerClass.isNotBlank()) classStr += "$headerClass "
        if(cell.leader && leaderClass.isNotBlank()) classStr += "$leaderClass "

        return if(classStr.isNotBlank()) " class=\"${classStr.trim()}\"" else ""
    }

}