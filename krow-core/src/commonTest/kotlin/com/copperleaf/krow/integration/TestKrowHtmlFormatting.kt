package com.copperleaf.krow.integration

import com.copperleaf.krow.builder.krow
import com.copperleaf.krow.formatters.html.DefaultHtmlAttributes
import com.copperleaf.krow.formatters.html.HtmlTableFormatter
import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalStdlibApi
class TestKrowHtmlFormatting {

    val input = krow {
        headerColumns("col1", "col2", "col3")
        rows(
            "row1" to listOf("1-1", "2-1", "3-1"),
            "row2" to listOf("1-2", "2-2", "3-2"),
        )
    }

    @Test
    fun testHtmlKrowTableId() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val tableId: String get() = "krow-id"
        })
        val output = underTest.print(input).trim()

        val expected = """
            <table id="krow-id">
              <thead>
              <tr>
                <th></th>
                <th>col1</th>
                <th>col2</th>
                <th>col3</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>row1</td>
                <td>1-1</td>
                <td>2-1</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td>row2</td>
                <td>1-2</td>
                <td>2-2</td>
                <td>3-2</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testHtmlKrowTableClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val tableClasses = listOf("krow-class")
        })
        val output = underTest.print(input).trim()

        val expected = """
            <table class="krow-class">
              <thead>
              <tr>
                <th></th>
                <th>col1</th>
                <th>col2</th>
                <th>col3</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>row1</td>
                <td>1-1</td>
                <td>2-1</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td>row2</td>
                <td>1-2</td>
                <td>2-2</td>
                <td>3-2</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testHtmlKrowTrClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val trClasses = listOf("krow-class")
        })
        val output = underTest.print(input).trim()

        val expected = """
            <table>
              <thead>
              <tr class="krow-class">
                <th></th>
                <th>col1</th>
                <th>col2</th>
                <th>col3</th>
              </tr>
              </thead>
              <tbody>
              <tr class="krow-class">
                <td>row1</td>
                <td>1-1</td>
                <td>2-1</td>
                <td>3-1</td>
              </tr>
              <tr class="krow-class">
                <td>row2</td>
                <td>1-2</td>
                <td>2-2</td>
                <td>3-2</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testHtmlKrowThClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val thClasses = listOf("krow-class")
        })
        val output = underTest.print(input).trim()

        val expected = """
            <table>
              <thead>
              <tr>
                <th class="krow-class"></th>
                <th class="krow-class">col1</th>
                <th class="krow-class">col2</th>
                <th class="krow-class">col3</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>row1</td>
                <td>1-1</td>
                <td>2-1</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td>row2</td>
                <td>1-2</td>
                <td>2-2</td>
                <td>3-2</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testHtmlKrowTdClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val tdClasses = listOf("krow-class")
        })
        val output = underTest.print(input).trim()

        val expected = """
            <table>
              <thead>
              <tr>
                <th class="krow-class"></th>
                <th class="krow-class">col1</th>
                <th class="krow-class">col2</th>
                <th class="krow-class">col3</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td class="krow-class">row1</td>
                <td class="krow-class">1-1</td>
                <td class="krow-class">2-1</td>
                <td class="krow-class">3-1</td>
              </tr>
              <tr>
                <td class="krow-class">row2</td>
                <td class="krow-class">1-2</td>
                <td class="krow-class">2-2</td>
                <td class="krow-class">3-2</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testHtmlKrowHeaderClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val headerClasses = listOf("krow-class")
        })
        val output = underTest.print(input).trim()

        val expected = """
            <table>
              <thead>
              <tr>
                <th class="krow-class"></th>
                <th class="krow-class">col1</th>
                <th class="krow-class">col2</th>
                <th class="krow-class">col3</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>row1</td>
                <td>1-1</td>
                <td>2-1</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td>row2</td>
                <td>1-2</td>
                <td>2-2</td>
                <td>3-2</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testHtmlKrowLeaderClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val leaderClasses = listOf("krow-class")
        })
        val output = underTest.print(input).trim()

        val expected = """
            <table>
              <thead>
              <tr>
                <th class="krow-class"></th>
                <th>col1</th>
                <th>col2</th>
                <th>col3</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td class="krow-class">row1</td>
                <td>1-1</td>
                <td>2-1</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td class="krow-class">row2</td>
                <td>1-2</td>
                <td>2-2</td>
                <td>3-2</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testHtmlKrowColspanRowpan() {
        val input = krow {
            header {
                column("col1") {
                    width = 12
                    horizontalAlignment = HorizontalAlignment.RIGHT
                }
                column("col2") {
                    width = 15
                    verticalAlignment = VerticalAlignment.BOTTOM
                }
                column("col3") {
                }
            }
            row("row1") {
                cell("1-1 colspans all 3") { colSpan = 3 }
            }
            row("row2") {
                cell("2-1")
                cell("2-2 colspan") {
                    colSpan = 2
                    rowSpan = 2
                    horizontalAlignment = HorizontalAlignment.CENTER
                }
            }
            row("row3") { cell("3-1") }
            row("row4") {
                cells("4-1") {
                    horizontalAlignment = HorizontalAlignment.LEFT
                    colSpan = 2
                }
                cell("4-3") {
                    horizontalAlignment = HorizontalAlignment.RIGHT
                }
            }
        }

        val output = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val leaderClasses = listOf("krow-class")
        }).print(input).trim()

//        assertEquals(
//            """
//            ┌──────┬──────────────┬─────────────────┬──────┐
//            │      │         col1 │ col2            │ col3 │
//            ├──────┼──────────────┴─────────────────┴──────┤
//            │ row1 │                    1-1 colspans all 3 │
//            ├──────┼──────────────┬────────────────────────┤
//            │ row2 │          2-1 │                        │
//            ├──────┼──────────────┤                        │
//            │ row3 │          3-1 │      2-2 colspan       │
//            ├──────┼──────────────┴─────────────────┬──────┤
//            │ row4 │ 4-1                            │  4-3 │
//            └──────┴────────────────────────────────┴──────┘
//            """.trimIndent(),
//            AsciiTableFormatter().print(input)
//        )

        val expected = """
            <table>
              <thead>
              <tr>
                <th class="krow-class"></th>
                <th>col1</th>
                <th>col2</th>
                <th>col3</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td class="krow-class">row1</td>
                <td colspan="3">1-1 colspans all 3</td>
              </tr>
              <tr>
                <td class="krow-class">row2</td>
                <td>2-1</td>
                <td rowspan="2" colspan="2">2-2 colspan</td>
              </tr>
              <tr>
                <td class="krow-class">row3</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td class="krow-class">row4</td>
                <td colspan="2">4-1</td>
                <td>4-3</td>
              </tr>
              </tbody>
            </table>
        """.trimIndent().trim()

        assertEquals(expected, output)
    }
}
