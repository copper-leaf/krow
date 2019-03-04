package com.copperleaf.krow.integration

import com.copperleaf.krow.HorizontalAlignment
import com.copperleaf.krow.KrowTable
import com.copperleaf.krow.VerticalAlignment
import com.copperleaf.krow.formatters.ascii.AsciiTableFormatter
import com.copperleaf.krow.formatters.ascii.CrossingBorder
import com.copperleaf.krow.formatters.ascii.DoubleBorder
import com.copperleaf.krow.formatters.ascii.SingleBorder
import com.copperleaf.krow.formatters.html.HtmlTableFormatter
import com.copperleaf.krow.krow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestKrow {

    lateinit var input: KrowTable

    @BeforeEach
    fun setup() {
        input = krow {
            cell("col1", "row1") { content = "1-1" }
            cell("col1", "row2") { content = "1-2" }

            cell("col2", "row1") { content = "2-1" }
            cell("col2", "row2") { content = "2-2" }

            cell("col3", "row1") { content = "3-1" }
            cell("col3", "row2") { content = "3-2" }

            table {
                wrapTextAt = 30
                horizontalAlignment = HorizontalAlignment.CENTER
                verticalAlignment = VerticalAlignment.TOP
            }
        }
    }

    @Test
    fun testSingleBorderKrow() {
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            ├──────┼──────┼──────┼──────┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────┼──────┼──────┼──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expectThat(output).isEqualTo(expected)
    }

    @Test
    fun testDoubleBorderKrow() {
        val underTest = AsciiTableFormatter(DoubleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            ╔══════╦══════╦══════╦══════╗
            ║      ║ col1 ║ col2 ║ col3 ║
            ╠══════╬══════╬══════╬══════╣
            ║ row1 ║ 1-1  ║ 2-1  ║ 3-1  ║
            ╠══════╬══════╬══════╬══════╣
            ║ row2 ║ 1-2  ║ 2-2  ║ 3-2  ║
            ╚══════╩══════╩══════╩══════╝
        """.trimIndent().trim()

        expectThat(output).isEqualTo(expected)
    }

    @Test
    fun testCrossingBorderKrow() {
        val underTest = AsciiTableFormatter(CrossingBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+------+------+------+
            |      | col1 | col2 | col3 |
            +------+------+------+------+
            | row1 | 1-1  | 2-1  | 3-1  |
            +------+------+------+------+
            | row2 | 1-2  | 2-2  | 3-2  |
            +------+------+------+------+
        """.trimIndent().trim()

        expectThat(output).isEqualTo(expected)
    }

    @Test
    fun testHtmlKrow() {
        val underTest = HtmlTableFormatter()
        val output = underTest.print(input).trim()

        val expected = """
            <table>
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

        expectThat(output).isEqualTo(expected)
    }

    @Test
    fun testKrowWhenContentIsLong_itShouldExpandColumnsToFitContent() {
        val underTest = krow {
            cell("col1", "row1") { content = "this column should grow" }
            cell("col2", "row1") { content = "this column should grow but then should not wrap at the next line even though it is it is so long" }
        }

        val output = AsciiTableFormatter(SingleBorder()).print(underTest).trim()

        val expected = """
            ┌──────┬─────────────────────────┬───────────────────────────────────────────────────────────────────────────────────────────────────┐
            │      │ col1                    │ col2                                                                                              │
            ├──────┼─────────────────────────┼───────────────────────────────────────────────────────────────────────────────────────────────────┤
            │ row1 │ this column should grow │ this column should grow but then should not wrap at the next line even though it is it is so long │
            └──────┴─────────────────────────┴───────────────────────────────────────────────────────────────────────────────────────────────────┘
        """.trimIndent().trim()

        expectThat(output).isEqualTo(expected)
    }

    @Test
    fun testKrowWhenContentIsLong_itShouldExpandColumnsToFitContentAndAlsoRespectSetColumnWidths() {
        val underTest = krow {
            cell("col1", "row1") { content = "this column should grow" }
            cell("col2", "row1") {
                content = "this column should grow but then should wrap at the next line because it is too long"
                wrapTextAt = 30
            }
        }

        val output = AsciiTableFormatter(SingleBorder()).print(underTest).trim()

        val expected = """
            ┌──────┬─────────────────────────┬────────────────────────────────┐
            │      │ col1                    │ col2                           │
            ├──────┼─────────────────────────┼────────────────────────────────┤
            │ row1 │ this column should grow │ this column should grow but    │
            │      │                         │ then should wrap at the next   │
            │      │                         │ line because it is too long    │
            └──────┴─────────────────────────┴────────────────────────────────┘
        """.trimIndent().trim()

        expectThat(output).isEqualTo(expected)
    }

    @Test
    fun testHidingHeader() {
        input.showHeaders = false
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬─────┬─────┬─────┐
            │ row1 │ 1-1 │ 2-1 │ 3-1 │
            ├──────┼─────┼─────┼─────┤
            │ row2 │ 1-2 │ 2-2 │ 3-2 │
            └──────┴─────┴─────┴─────┘
        """.trimIndent().trim()

        expectThat(output).isEqualTo(expected)
    }

    @Test
    fun testHidingLeader() {
        input.showLeaders = false
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┐
            │ col1 │ col2 │ col3 │
            ├──────┼──────┼──────┤
            │ 1-1  │ 2-1  │ 3-1  │
            ├──────┼──────┼──────┤
            │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┘
        """.trimIndent().trim()

        expectThat(output).isEqualTo(expected)
    }

}