package com.eden.krow

import com.eden.krow.borders.CrossingBorder
import com.eden.krow.borders.DoubleBorder
import com.eden.krow.borders.SingleBorder
import com.eden.krow.formatters.AsciiTableFormatter
import com.eden.krow.formatters.HtmlTableFormatter
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

class KrowTableTest {

    lateinit var table: KrowTable

    @BeforeMethod
    fun testSetup() {
        table = KrowTable()
    }

    @Test
    @Throws(Throwable::class)
    fun testBinaryExtensions() {
        val table = krow {
            cell("col1", "row1") { content = "These are all some really long columns" }
            cell("col1", "row2") { content = "These are all some really long columns" }
            cell("col1", "row3") { content = "These are all some really long columns" }

            cell("col2", "row1") { content = "These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns" }
            cell("col2", "row2") { content = "These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns" }
            cell("col2", "row3") { content = "These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns" }

            cell("col3", "row1") { content = "These are all some really long columns" }
            cell("col3", "row2") { content = "These are all some really long columns" }
            cell("col3", "row3") { content = "These are all some really long columns" }

            table {
                wrapTextAt = 30
                horizontalAlignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.TOP
            }
        }

        println(table.print(AsciiTableFormatter(SingleBorder())))
        println(table.print(AsciiTableFormatter(DoubleBorder())))
        println(table.print(AsciiTableFormatter(CrossingBorder())))
        println(table.print(HtmlTableFormatter()))
    }

}
