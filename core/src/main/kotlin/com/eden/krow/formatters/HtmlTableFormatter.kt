package com.eden.krow.formatters

import com.eden.krow.KrowTable
import com.eden.krow.TableFormatter

class HtmlTableFormatter : TableFormatter {

    override fun print(table: KrowTable): String {
        val allCells = table.tableCells
        var output = ""
        output += "<table>\n"

        if(table.showHeaders) {
            output += "  <thead>\n"
            output += "  <tr>\n"
            allCells[0].forEach {
                output += "    <th>" + it.content + "</th>\n"
            }
            output += "  </tr>\n"
            output += "  </thead>\n"

            output += "  <tbody>\n"

            for(i in 1 until allCells.size) {
                output += "  <tr>\n"
                allCells[i].forEach {
                    output += "    <td>" + it.content + "</td>\n"
                }
                output += "  </tr>\n"
            }

            output += "  </tbody>\n"
        }
        else {
            output += "  <tbody>\n"

            allCells.forEach { cells ->
                output += "  <tr>\n"
                cells.forEach {
                    output += "    <td>" + it.content + "</td>\n"
                }
                output += "  </tr>\n"
            }

            output += "  </tbody>\n"
        }

        output += "</table>"

        return output
    }





}