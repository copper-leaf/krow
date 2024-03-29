package com.copperleaf.krow.integration

import com.copperleaf.krow.builder.bodyRow
import com.copperleaf.krow.builder.bodyRows
import com.copperleaf.krow.builder.cellAt
import com.copperleaf.krow.builder.cells
import com.copperleaf.krow.builder.column
import com.copperleaf.krow.builder.headerColumns
import com.copperleaf.krow.builder.krow
import com.copperleaf.krow.formatters.ascii.AsciiTableFormatter
import com.copperleaf.krow.formatters.html.HtmlTableFormatter
import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment
import com.copperleaf.krow.utils.CrossingBorder
import com.copperleaf.krow.utils.DoubleBorder
import com.copperleaf.krow.utils.SingleBorder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalStdlibApi
class TestKrow {

    @Test
    fun testKrowTableAlignment() {
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
            bodyRow("row1") {
                cells(
                    "1-1 colspans all 3 and has much more content than would normally fit within this single cell " +
                        "so it must wrap several rows in order to fit but is still constrained by the available cell " +
                        "widths"
                ) { colSpan = 3 }
                cells("create column and span rows") { rowSpan = 4 }
            }
            bodyRow("row2") {
                cells("2-1")
                cells("2-2 colspan") {
                    colSpan = 2
                    rowSpan = 2
                    horizontalAlignment = HorizontalAlignment.CENTER
                }
            }
            bodyRow("row3") { cells("3-1") }
            bodyRow("row4") {
                cell("4-1") {
                    horizontalAlignment = HorizontalAlignment.LEFT
                    colSpan = 2
                }
                cell("4-3") {
                    horizontalAlignment = HorizontalAlignment.RIGHT
                }
            }
        }

        assertEquals(
            """
            ┌──────┬────────────┬───────────────┬──────┬─────────────────────────────┐
            │      │       col1 │ col2          │ col3 │ 4                           │
            ├──────┼────────────┴───────────────┴──────┼─────────────────────────────┤
            │ row1 │   1-1 colspans all 3 and has much │ create column and span rows │
            │      │  more content than would normally │                             │
            │      │ fit within this single cell so it │                             │
            │      │   must wrap several rows in order │                             │
            │      │   to fit but is still constrained │                             │
            │      │      by the available cell widths │                             │
            ├──────┼────────────┬──────────────────────┤                             │
            │ row2 │        2-1 │                      │                             │
            ├──────┼────────────┤                      │                             │
            │ row3 │        3-1 │     2-2 colspan      │                             │
            ├──────┼────────────┴───────────────┬──────┤                             │
            │ row4 │ 4-1                        │  4-3 │                             │
            └──────┴────────────────────────────┴──────┴─────────────────────────────┘
            """.trimIndent(),
            AsciiTableFormatter(SingleBorder()).print(input).trim()
        )

        assertEquals(
            """
            ╔══════╦════════════╦═══════════════╦══════╦═════════════════════════════╗
            ║      ║       col1 ║ col2          ║ col3 ║ 4                           ║
            ╠══════╬════════════╩═══════════════╩══════╬═════════════════════════════╣
            ║ row1 ║   1-1 colspans all 3 and has much ║ create column and span rows ║
            ║      ║  more content than would normally ║                             ║
            ║      ║ fit within this single cell so it ║                             ║
            ║      ║   must wrap several rows in order ║                             ║
            ║      ║   to fit but is still constrained ║                             ║
            ║      ║      by the available cell widths ║                             ║
            ╠══════╬════════════╦══════════════════════╣                             ║
            ║ row2 ║        2-1 ║                      ║                             ║
            ╠══════╬════════════╣                      ║                             ║
            ║ row3 ║        3-1 ║     2-2 colspan      ║                             ║
            ╠══════╬════════════╩═══════════════╦══════╣                             ║
            ║ row4 ║ 4-1                        ║  4-3 ║                             ║
            ╚══════╩════════════════════════════╩══════╩═════════════════════════════╝
            """.trimIndent(),
            AsciiTableFormatter(DoubleBorder()).print(input).trim()
        )

        assertEquals(
            """
            +------+------------+---------------+------+-----------------------------+
            |      |       col1 | col2          | col3 | 4                           |
            +------+------------+---------------+------+-----------------------------+
            | row1 |   1-1 colspans all 3 and has much | create column and span rows |
            |      |  more content than would normally |                             |
            |      | fit within this single cell so it |                             |
            |      |   must wrap several rows in order |                             |
            |      |   to fit but is still constrained |                             |
            |      |      by the available cell widths |                             |
            +------+------------+----------------------+                             |
            | row2 |        2-1 |                      |                             |
            +------+------------+                      |                             |
            | row3 |        3-1 |     2-2 colspan      |                             |
            +------+------------+---------------+------+                             |
            | row4 | 4-1                        |  4-3 |                             |
            +------+----------------------------+------+-----------------------------+
            """.trimIndent(),
            AsciiTableFormatter(CrossingBorder()).print(input).trim()
        )

        assertEquals(
            """
            <table>
              <thead>
              <tr>
                <th></th>
                <th>col1</th>
                <th>col2</th>
                <th>col3</th>
                <th>4</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>row1</td>
                <td colspan="3">1-1 colspans all 3 and has much more content than would normally fit within this single cell so it must wrap several rows in order to fit but is still constrained by the available cell widths</td>
                <td rowspan="4">create column and span rows</td>
              </tr>
              <tr>
                <td>row2</td>
                <td>2-1</td>
                <td rowspan="2" colspan="2">2-2 colspan</td>
              </tr>
              <tr>
                <td>row3</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td>row4</td>
                <td colspan="2">4-1</td>
                <td>4-3</td>
              </tr>
              </tbody>
            </table>
            """.trimIndent(),
            HtmlTableFormatter().print(input).trim()
        )
    }

    @Test
    fun testKrowTableConstraintPattern() {
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

            cellAt("row1", "col1") {
                content = "1-1 colspans all 3 and has much more content than would normally fit within this single " +
                    "cell so it must wrap several rows in order to fit but is still constrained by the available" +
                    " cell widths"
                colSpan = 3
            }
            cellAt("row1", "col4") {
                content = "create column and span rows"
                rowSpan = 4
            }

            cellAt("row2", "col1") {
                content = "2-1"
            }
            cellAt("row2", "col2") {
                content = "2-2 colspan"
                colSpan = 2
                rowSpan = 2
                horizontalAlignment = HorizontalAlignment.CENTER
            }

            cellAt("row3", "col1") {
                content = "3-1"
            }

            cellAt("row4", "col1") {
                content = "4-1"
                horizontalAlignment = HorizontalAlignment.LEFT
                colSpan = 2
            }
            cellAt("row4", "col3") {
                content = "4-3"
                horizontalAlignment = HorizontalAlignment.RIGHT
            }
        }

        assertEquals(
            """
            ┌──────┬────────────┬───────────────┬──────┬─────────────────────────────┐
            │      │       col1 │ col2          │ col3 │ col4                        │
            ├──────┼────────────┴───────────────┴──────┼─────────────────────────────┤
            │ row1 │   1-1 colspans all 3 and has much │ create column and span rows │
            │      │  more content than would normally │                             │
            │      │ fit within this single cell so it │                             │
            │      │   must wrap several rows in order │                             │
            │      │   to fit but is still constrained │                             │
            │      │      by the available cell widths │                             │
            ├──────┼────────────┬──────────────────────┤                             │
            │ row2 │        2-1 │                      │                             │
            ├──────┼────────────┤                      │                             │
            │ row3 │        3-1 │     2-2 colspan      │                             │
            ├──────┼────────────┴───────────────┬──────┤                             │
            │ row4 │ 4-1                        │  4-3 │                             │
            └──────┴────────────────────────────┴──────┴─────────────────────────────┘
            """.trimIndent(),
            AsciiTableFormatter(SingleBorder()).print(input).trim()
        )

        assertEquals(
            """
            ╔══════╦════════════╦═══════════════╦══════╦═════════════════════════════╗
            ║      ║       col1 ║ col2          ║ col3 ║ col4                        ║
            ╠══════╬════════════╩═══════════════╩══════╬═════════════════════════════╣
            ║ row1 ║   1-1 colspans all 3 and has much ║ create column and span rows ║
            ║      ║  more content than would normally ║                             ║
            ║      ║ fit within this single cell so it ║                             ║
            ║      ║   must wrap several rows in order ║                             ║
            ║      ║   to fit but is still constrained ║                             ║
            ║      ║      by the available cell widths ║                             ║
            ╠══════╬════════════╦══════════════════════╣                             ║
            ║ row2 ║        2-1 ║                      ║                             ║
            ╠══════╬════════════╣                      ║                             ║
            ║ row3 ║        3-1 ║     2-2 colspan      ║                             ║
            ╠══════╬════════════╩═══════════════╦══════╣                             ║
            ║ row4 ║ 4-1                        ║  4-3 ║                             ║
            ╚══════╩════════════════════════════╩══════╩═════════════════════════════╝
            """.trimIndent(),
            AsciiTableFormatter(DoubleBorder()).print(input).trim()
        )

        assertEquals(
            """
            +------+------------+---------------+------+-----------------------------+
            |      |       col1 | col2          | col3 | col4                        |
            +------+------------+---------------+------+-----------------------------+
            | row1 |   1-1 colspans all 3 and has much | create column and span rows |
            |      |  more content than would normally |                             |
            |      | fit within this single cell so it |                             |
            |      |   must wrap several rows in order |                             |
            |      |   to fit but is still constrained |                             |
            |      |      by the available cell widths |                             |
            +------+------------+----------------------+                             |
            | row2 |        2-1 |                      |                             |
            +------+------------+                      |                             |
            | row3 |        3-1 |     2-2 colspan      |                             |
            +------+------------+---------------+------+                             |
            | row4 | 4-1                        |  4-3 |                             |
            +------+----------------------------+------+-----------------------------+
            """.trimIndent(),
            AsciiTableFormatter(CrossingBorder()).print(input).trim()
        )

        assertEquals(
            """
            <table>
              <thead>
              <tr>
                <th></th>
                <th>col1</th>
                <th>col2</th>
                <th>col3</th>
                <th>col4</th>
              </tr>
              </thead>
              <tbody>
              <tr>
                <td>row1</td>
                <td colspan="3">1-1 colspans all 3 and has much more content than would normally fit within this single cell so it must wrap several rows in order to fit but is still constrained by the available cell widths</td>
                <td rowspan="4">create column and span rows</td>
              </tr>
              <tr>
                <td>row2</td>
                <td>2-1</td>
                <td rowspan="2" colspan="2">2-2 colspan</td>
              </tr>
              <tr>
                <td>row3</td>
                <td>3-1</td>
              </tr>
              <tr>
                <td>row4</td>
                <td colspan="2">4-1</td>
                <td>4-3</td>
              </tr>
              </tbody>
            </table>
            """.trimIndent(),
            HtmlTableFormatter().print(input).trim()
        )
    }

    @Test
    fun testKrowTableFailsWhenSpansOverlap() {
        assertFailsWith<IllegalStateException>(
            message = "Cell at position (row3:3, col3:2)! is already occupied!"
        ) {
            krow {
                headerColumns("col1", "col2", "col3", "col4")
                bodyRows("row1", "row2", "row3", "row4")

                cellAt("row1", "col1") {
                    rowSpan = 3
                    colSpan = 3
                }

                cellAt("row3", "col3") {
                    rowSpan = 2
                    colSpan = 2
                }
            }
        }
    }
}
