package com.copperleaf.krow.integration

import com.copperleaf.krow.builder.krow
import com.copperleaf.krow.formatters.ascii.AsciiTableFormatter
import com.copperleaf.krow.utils.SingleBorder
import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment
import kotlin.test.Test
import kotlin.test.assertEquals

/* ktlint-disable max-line-length */
class TestKrow {

    @Test
    fun testBuilderStyles() {
        // simple style
        krow {
            header {
                column("col1")
                column("col2")
                column("col3")
            }
            row("row1") {
                cell("1-1")
                cell("1-2")
                cell("1-3")
            }
            row("row1") {
                cell("2-1")
                cell("2-2")
                cell("2-3")
            }
            row("row3") {
                cell("3-1")
                cell("3-2")
                cell("3-3")
            }
        }
        // end simple style

        // verbose (HTML) style
        krow {
            header {
                row {
                    column("col1")
                    column("col2")
                    column("col3")
                }
            }
            body {
                row("row1") {
                    cell("1-1")
                    cell("1-2")
                    cell("1-3")
                }
                row("row2") {
                    cell("2-1")
                    cell("2-2")
                    cell("2-3")
                }
                row("row3") {
                    cell("3-1")
                    cell("3-2")
                    cell("3-3")
                }
            }
        }
        // end verbose (HTML) style

        // constraint style
//        krow {
//            cell("col1", "row1") { content = "1-1" }
//            cell("col1", "row2") { content = "1-2" }
//            cell("col1", "row3") { content = "1-3" }
//
//            cell("col2", "row1") { content = "2-1" }
//            cell("col2", "row2") { content = "2-2" }
//            cell("col2", "row3") { content = "2-3" }
//
//            cell("col3", "row1") { content = "3-1" }
//            cell("col3", "row2") { content = "3-2" }
//            cell("col3", "row3") { content = "3-3" }
//        }
        // end constraint style
    }

    @Test
    fun testDefaultKrowTable() {
        val input = krow {
            headerColumns("col1", "col2", "col3")
            rows(
                "row1" to listOf("1-1", "1-2", "1-3"),
                "row2" to listOf("2-1", "2-2", "2-3"),
                "row3" to listOf("3-1", "3-2", "3-3"),
            )
        }
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+------+------+------+
            |      | col1 | col2 | col3 |
            +------+------+------+------+
            | row1 | 1-1  | 1-2  | 1-3  |
            +------+------+------+------+
            | row2 | 2-1  | 2-2  | 2-3  |
            +------+------+------+------+
            | row3 | 3-1  | 3-2  | 3-3  |
            +------+------+------+------+
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testDefaultKrowTableWithoutLeadingColumn() {
        val input = krow {
            includeLeadingColumn = false

            headerColumns("col1", "col2", "col3")
            rows(
                "row1" to listOf("1-1", "1-2", "1-3"),
                "row2" to listOf("2-1", "2-2", "2-3"),
                "row3" to listOf("3-1", "3-2", "3-3"),
            )
        }
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+------+------+
            | col1 | col2 | col3 |
            +------+------+------+
            | 1-1  | 1-2  | 1-3  |
            +------+------+------+
            | 2-1  | 2-2  | 2-3  |
            +------+------+------+
            | 3-1  | 3-2  | 3-3  |
            +------+------+------+
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testDefaultKrowTableWithoutHeaderRow() {
        val input = krow {
            includeHeaderRow = false
            headerColumns("col1", "col2", "col3")
            rows(
                "row1" to listOf("1-1", "1-2", "1-3"),
                "row2" to listOf("2-1", "2-2", "2-3"),
                "row3" to listOf("3-1", "3-2", "3-3"),
            )
        }
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+------+------+------+
            | row1 | 1-1  | 1-2  | 1-3  |
            +------+------+------+------+
            | row2 | 2-1  | 2-2  | 2-3  |
            +------+------+------+------+
            | row3 | 3-1  | 3-2  | 3-3  |
            +------+------+------+------+
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testDefaultKrowTableWithoutLeadingColumnOrHeaderRow() {
        val input = krow {
            includeLeadingColumn = false
            includeHeaderRow = false
            headerColumns("col1", "col2", "col3")
            rows(
                "row1" to listOf("1-1", "1-2", "1-3"),
                "row2" to listOf("2-1", "2-2", "2-3"),
                "row3" to listOf("3-1", "3-2", "3-3"),
            )
        }
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+------+------+
            | 1-1  | 1-2  | 1-3  |
            +------+------+------+
            | 2-1  | 2-2  | 2-3  |
            +------+------+------+
            | 3-1  | 3-2  | 3-3  |
            +------+------+------+
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testDefaultKrowTableWithColSpans() {
        val input = krow {
            headerColumns("col1", "col2", "col3")
            row("row1") {
                cells("1-1 colspans all 3") { colSpan = 3 }
            }
            row("row2") {
                cells("2-1")
                cells("2-2 colspan") { colSpan = 2 }
            }
            row("row3") { cells("3-1", "3-2", "3-3") }
        }
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+------+------+------+
            |      | col1 | col2 | col3 |
            +------+------+------+------+
            | row1 | 1-1 colspans all 3 |
            +------+------+-------------+
            | row2 | 2-1  | 2-2 colspan |
            +------+------+------+------+
            | row3 | 3-1  | 3-2  | 3-3  |
            +------+------+------+------+
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

    @Test
    fun testDefaultKrowTableWithRowSpans() {
        val input = krow {
            headerColumns("col1", "col2", "col3")
            row("row1") {
                cells("1-1 colspans all 3") { colSpan = 3 }
            }
            row("row2") {
                cells("2-1")
                cells("2-2 colspan") { colSpan = 2; rowSpan = 2 }
            }
            row("row3") { cells("3-1") }
        }
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+------+------+------+
            |      | col1 | col2 | col3 |
            +------+------+------+------+
            | row1 | 1-1 colspans all 3 |
            +------+------+-------------+
            | row2 | 2-1  | 2-2 colspan |
            +------+------+             |
            | row3 | 3-1  |             |
            +------+------+-------------+
        """.trimIndent().trim()

        assertEquals(expected, output)
    }

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
                column("col1") {
                }
            }
            row("row1") {
                cells("1-1 colspans all 3") { colSpan = 3 }
            }
            row("row2") {
                cells("2-1")
                cells("2-2 colspan") {
                    colSpan = 2
                    rowSpan = 2
                    horizontalAlignment = HorizontalAlignment.CENTER
                }
            }
            row("row3") { cells("3-1") }
            row("row4") {
                cells("4-1") {
                    horizontalAlignment = HorizontalAlignment.LEFT
                    colSpan = 2
                }
                cells("4-3") {
                    horizontalAlignment = HorizontalAlignment.RIGHT
                }
            }
        }
        val underTest = AsciiTableFormatter(SingleBorder())
        val output = underTest.print(input).trim()

        val expected = """
            +------+--------------+-----------------+------+
            |      |         col1 | col2            | col1 |
            +------+--------------+-----------------+------+
            | row1 |                    1-1 colspans all 3 |
            +------+--------------+------------------------+
            | row2 |          2-1 |                        |
            +------+--------------+                        |
            | row3 |          3-1 |      2-2 colspan       |
            +------+--------------+-----------------+------+
            | row4 | 4-1                            |  4-3 |
            +------+--------------------------------+------+
        """.trimIndent().trim()

        assertEquals(expected, output)
    }
}
