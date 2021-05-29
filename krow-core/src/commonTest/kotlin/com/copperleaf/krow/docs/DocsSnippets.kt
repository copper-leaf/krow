package com.copperleaf.krow.docs

import com.copperleaf.krow.builder.bodyRow
import com.copperleaf.krow.builder.bodyRows
import com.copperleaf.krow.builder.cellAt
import com.copperleaf.krow.builder.cells
import com.copperleaf.krow.builder.column
import com.copperleaf.krow.builder.columns
import com.copperleaf.krow.builder.headerColumns
import com.copperleaf.krow.builder.krow
import com.copperleaf.krow.builder.rows
import com.copperleaf.krow.formatters.ascii.AsciiTableFormatter
import com.copperleaf.krow.formatters.html.HtmlTableFormatter
import com.copperleaf.krow.model.Krow
import com.copperleaf.krow.utils.BorderSet
import com.copperleaf.krow.utils.BordersOf
import com.copperleaf.krow.utils.CrossingBorder
import com.copperleaf.krow.utils.DoubleBorder
import com.copperleaf.krow.utils.SingleBorder
import com.copperleaf.krow.utils.SingleThickBorder
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalStdlibApi::class)
class DocsSnippets {

// Header DSL Functions
// ---------------------------------------------------------------------------------------------------------------------

    @Test
    fun docsHeaderDsl() {
        listOf<() -> Krow.Table>(
            {
                // snippet::table-header-columns1[dsl,header]
                krow {
                    header {
                        row {
                            column("column 1")
                            column("column 2")
                            column("column 3")
                        }
                    }
                    body {
                        row("row1") {
                            cell("1")
                            cell("2") { colSpan = 2 }
                        }
                    }
                }
                // end::table-header-columns1
            },
            {
                // snippet::table-header-columns2[dsl,header]
                krow {
                    header {
                        column("column 1")
                        column("column 2")
                        column("column 3")
                    }
                    body {
                        row("row1") {
                            cell("1")
                            cell("2") { colSpan = 2 }
                        }
                    }
                }
                // end::table-header-columns2
            },
            {
                // snippet::table-header-columns3[dsl,header]
                krow {
                    header {
                        row {
                            columns("column 1", "column 2", "column 3")
                        }
                    }
                    body {
                        row("row1") {
                            cell("1")
                            cell("2") { colSpan = 2 }
                        }
                    }
                }
                // end::table-header-columns3
            },
            {
                // snippet::table-header-columns4[dsl,header]
                krow {
                    header {
                        columns("column 1", "column 2", "column 3")
                    }
                    body {
                        row("row1") {
                            cell("1")
                            cell("2") { colSpan = 2 }
                        }
                    }
                }
                // end::table-header-columns4
            },
            {
                // snippet::table-header-columns5[dsl,header]
                krow {
                    headerColumns("column 1", "column 2", "column 3")
                    body {
                        row("row1") {
                            cell("1")
                            cell("2") { colSpan = 2 }
                        }
                    }
                }
                // end::table-header-columns5
            }
        ).forEach { table ->
            // snippet::table-header-columns-rendered[rendered,header]
            val expected = """
                ┌──────┬──────────┬──────────┬──────────┐
                │      │ column 1 │ column 2 │ column 3 │
                ├──────┼──────────┼──────────┴──────────┤
                │ row1 │ 1        │ 2                   │
                └──────┴──────────┴─────────────────────┘
            """.trimIndent()
            // end::table-header-columns-rendered

            assertEquals(
                expected,
                AsciiTableFormatter().print(table())
            )
        }
    }

// Body DSL Functions
// ---------------------------------------------------------------------------------------------------------------------

    @Test
    fun docsBodyDsl() {
        listOf<() -> Krow.Table>(
            {
                // snippet::table-body-rows1[dsl,body]
                krow {
                    headerColumns("column 1", "column 2", "column 3")
                    body {
                        row("row1") {
                            cell("1")
                            cell("2")
                            cell("3")
                        }
                        row("row2") {
                            cell("a")
                            cell("b")
                            cell("c")
                        }
                    }
                }
                // end::table-body-rows1
            },
            {
                // snippet::table-body-rows2[dsl,body]
                krow {
                    headerColumns("column 1", "column 2", "column 3")
                    body {
                        row("row1") {
                            cells("1", "2", "3")
                        }
                        row("row2") {
                            cells("a", "b", "c")
                        }
                    }
                }
                // end::table-body-rows2
            },
            {
                // snippet::table-body-rows3[dsl,body]
                krow {
                    headerColumns("column 1", "column 2", "column 3")
                    body {
                        rows(
                            "row1" to listOf("1", "2", "3"),
                            "row2" to listOf("a", "b", "c")
                        )
                    }
                }
                // end::table-body-rows3
            },
            {
                // snippet::table-body-rows4[dsl,body]
                krow {
                    headerColumns("column 1", "column 2", "column 3")
                    bodyRow("row1") {
                        cell("1")
                        cell("2")
                        cell("3")
                    }
                    bodyRow("row2") {
                        cell("a")
                        cell("b")
                        cell("c")
                    }
                }
                // end::table-body-rows4
            },
            {
                // snippet::table-body-rows5[dsl,body]
                krow {
                    headerColumns("column 1", "column 2", "column 3")
                    bodyRow("row1") {
                        cells("1", "2", "3")
                    }
                    bodyRow("row2") {
                        cells("a", "b", "c")
                    }
                }
                // end::table-body-rows5
            },
            {
                // snippet::table-body-rows6[dsl,body]
                krow {
                    headerColumns("column 1", "column 2", "column 3")
                    bodyRows(
                        "row1" to listOf("1", "2", "3"),
                        "row2" to listOf("a", "b", "c")
                    )
                }
                // end::table-body-rows6
            },
        ).forEach { table ->
            // snippet::table-body-rows-rendered[rendered,body]
            val expected = """
                ┌──────┬──────────┬──────────┬──────────┐
                │      │ column 1 │ column 2 │ column 3 │
                ├──────┼──────────┼──────────┼──────────┤
                │ row1 │ 1        │ 2        │ 3        │
                ├──────┼──────────┼──────────┼──────────┤
                │ row2 │ a        │ b        │ c        │
                └──────┴──────────┴──────────┴──────────┘
            """.trimIndent()
            // end::table-body-rows-rendered

            assertEquals(
                expected,
                AsciiTableFormatter().print(table())
            )
        }
    }

// Dynamic Layout
// ---------------------------------------------------------------------------------------------------------------------

    @Test
    fun dynamicLayoutRows() {
        // snippet::table-dynamic-layout-rows-rendered[rendered,dynamic,rows]
        val expected = """
                ┌───┬──────────┬──────────┬──────────┐
                │   │ column 1 │ column 2 │ column 3 │
                ├───┼──────────┼──────────┼──────────┤
                │ 1 │ 1        │ 2        │ 3        │
                ├───┼──────────┼──────────┼──────────┤
                │ 2 │ a        │ b        │ c        │
                └───┴──────────┴──────────┴──────────┘
        """.trimIndent()
        // end::table-dynamic-layout-rows-rendered
        // snippet::table-dynamic-layout-rows-dsl[rendered,dsl,rows]
        val table = krow {
            headerColumns("column 1", "column 2", "column 3")
            bodyRow {
                cells("1", "2", "3")
            }
            bodyRow {
                cells("a", "b", "c")
            }
        }
        // end::table-dynamic-layout-rows-dsl

        assertEquals(
            expected,
            AsciiTableFormatter().print(table)
        )
    }

    @Test
    fun dynamicLayoutColumns1() {
        // snippet::table-dynamic-layout-columns1-rendered[rendered,dynamic,columns]
        val expected = """
            ┌───┬──────────┬──────────┬──────────┬───┬───┐
            │   │ column 1 │ column 2 │ column 3 │ 4 │ 5 │
            ├───┼──────────┼──────────┼──────────┼───┼───┤
            │ 1 │ 1        │ 2        │ 3        │ 4 │ 5 │
            ├───┼──────────┼──────────┼──────────┼───┼───┤
            │ 2 │ a        │ b        │ c        │ d │ e │
            └───┴──────────┴──────────┴──────────┴───┴───┘
        """.trimIndent()
        // end::table-dynamic-layout-columns1-rendered
        // snippet::table-dynamic-layout-columns1-dsl[dsl,dynamic,columns]
        val table = krow {
            headerColumns("column 1", "column 2", "column 3")
            bodyRow {
                cells("1", "2", "3", "4", "5")
            }
            bodyRow {
                cells("a", "b", "c", "d", "e")
            }
        }
        // end::table-dynamic-layout-columns1-dsl

        assertEquals(
            expected,
            AsciiTableFormatter().print(table)
        )
    }

    @Test
    fun dynamicLayoutColumns2() {
        // snippet::table-dynamic-layout-columns2-rendered[rendered,dynamic,columns]
        val expected = """
                ┌───┬───┬───┬───┐
                │   │ 1 │ 2 │ 3 │
                ├───┼───┼───┼───┤
                │ 1 │ 1 │ 2 │ 3 │
                ├───┼───┼───┼───┤
                │ 2 │ a │ b │ c │
                └───┴───┴───┴───┘
        """.trimIndent()
        // end::table-dynamic-layout-columns2-rendered
        // snippet::table-dynamic-layout-columns2-dsl[dsl,dynamic,columns]
        val table = krow {
            bodyRow {
                cells("1", "2", "3")
            }
            bodyRow {
                cells("a", "b", "c")
            }
        }
        // end::table-dynamic-layout-columns2-dsl

        assertEquals(
            expected,
            AsciiTableFormatter().print(table)
        )
    }

    @Test
    fun dynamicLayoutCells() {
        // snippet::table-dynamic-layout-cells-rendered[rendered,dynamic,cells]
        val expected = """
            ┌───┬──────────┬──────────┬──────────┬───┬───┐
            │   │ column 1 │ column 2 │ column 3 │ 4 │ 5 │
            ├───┼──────────┴──────────┼──────────┼───┴───┤
            │ 1 │ 1                   │ 2        │ 3     │
            ├───┼──────────┬──────────┼──────────┼───┬───┤
            │ 2 │ a        │ b        │ c        │ d │ e │
            └───┴──────────┴──────────┴──────────┴───┴───┘
        """.trimIndent()
        // end::table-dynamic-layout-cells-rendered
        // snippet::table-dynamic-layout-cells-dsl[rendered,dsl,cells]
        val table = krow {
            headerColumns("column 1", "column 2", "column 3")
            bodyRow {
                cell("1") { colSpan = 2 }
                cell("2")
                cell("3") { colSpan = 2 }
            }
            bodyRow {
                cells("a", "b", "c", "d", "e")
            }
        }
        // end::table-dynamic-layout-cells-dsl

        assertEquals(
            expected,
            AsciiTableFormatter().print(table)
        )
    }

    @Test
    fun dynamicLayoutCoordinates1() {
        // snippet::table-dynamic-layout-coordinates1-rendered[rendered,dynamic,coordinates]
        val expected = """
            ┌──────┬──────────┬──────────┬──────────┬──────────┬───┐
            │      │ column 1 │ column 2 │ column 3 │ column 4 │ 5 │
            ├──────┼──────────┴──────────┼──────────┼──────────┴───┤
            │ row1 │ 1                   │ 2        │ 3            │
            ├──────┼──────────┬──────────┼──────────┼──────────┬───┤
            │ row2 │ a        │ b        │ c        │ d        │ e │
            └──────┴──────────┴──────────┴──────────┴──────────┴───┘
        """.trimIndent()
        // end::table-dynamic-layout-coordinates1-rendered
        // snippet::table-dynamic-layout-coordinates1-dsl[rendered,dsl,coordinates]
        val table = krow {
            headerColumns("column 1", "column 2", "column 3")
            cellAt("row1", "column 1") {
                colSpan = 2
                content = "1"
            }
            cellAt("row1", "column 3") {
                content = "2"
            }
            cellAt("row1", "column 4") {
                colSpan = 2
                content = "3"
            }

            cellAt("row2", "column 1") {
                content = "a"
            }
            cellAt("row2", "column 2") {
                content = "b"
            }
            cellAt("row2", "column 3") {
                content = "c"
            }
            cellAt("row2", "column 4") {
                content = "d"
            }
            cellAt("row2", "5") {
                content = "e"
            }
        }
        // end::table-dynamic-layout-coordinates1-dsl

        assertEquals(
            expected,
            AsciiTableFormatter().print(table)
        )
    }

    @Test
    fun dynamicLayoutCoordinates2() {
        // snippet::table-dynamic-layout-coordinates2-rendered[rendered,dynamic,coordinates]
        val expected = """
            ┌───┬──────────┬────────────────────────┬──────────┐
            │   │ column 1 │ column 2               │ column 3 │
            ├───┼──────────┼────────────────────────┼──────────┤
            │ 1 │ 1        │ 2                      │ 3        │
            ├───┼──────────┼────────────────────────┼──────────┤
            │ 2 │ a        │ overridden with cellAt │ c        │
            └───┴──────────┴────────────────────────┴──────────┘
        """.trimIndent()
        // end::table-dynamic-layout-coordinates2-rendered
        // snippet::table-dynamic-layout-coordinates2-dsl[rendered,dsl,coordinates]
        val table = krow {
            headerColumns("column 1", "column 2", "column 3")
            bodyRow {
                cells("1", "2", "3")
            }
            bodyRow {
                cells("a", "b", "c")
            }

            cellAt("2", "column 2") { content = "overridden with cellAt" }
        }
        // end::table-dynamic-layout-coordinates2-dsl

        assertEquals(
            expected,
            AsciiTableFormatter().print(table)
        )
    }

// Border Styles
// ---------------------------------------------------------------------------------------------------------------------

    @Test
    fun docsAsciiTableFormatter() {
        // snippet::table-ascii-table-formatter[ascii]
        val borders = DoubleBorder()
        val table = krow {
            // ...
        }
        AsciiTableFormatter(borders).print(table)
        // end::table-ascii-table-formatter
    }

    @Test
    fun docsAsciiRenderer() {
        listOf<Pair<() -> BorderSet, () -> String>>(
            {
                SingleBorder()
            } to {
                """
                // snippet::table-ascii-single[rendered,ascii]
                ┌──────┬──────────┬──────────┬──────────┐
                │      │ column 1 │ column 2 │ column 3 │
                ├──────┼──────────┼──────────┴──────────┤
                │ row1 │ 1        │ 2                   │
                ├──────┼──────────┤                     │
                │ row2 │ a        │                     │
                └──────┴──────────┴─────────────────────┘
                // end::table-ascii-single
                """
            },
            {
                SingleThickBorder()
            } to {
                """
                // snippet::table-ascii-single-thick[rendered,ascii]
                ┏━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┳━━━━━━━━━━┓
                ┃      ┃ column 1 ┃ column 2 ┃ column 3 ┃
                ┣━━━━━━╋━━━━━━━━━━╋━━━━━━━━━━┻━━━━━━━━━━┫
                ┃ row1 ┃ 1        ┃ 2                   ┃
                ┣━━━━━━╋━━━━━━━━━━┫                     ┃
                ┃ row2 ┃ a        ┃                     ┃
                ┗━━━━━━┻━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━┛
                // end::table-ascii-single-thick
                """
            },
            {
                DoubleBorder()
            } to {
                """
                // snippet::table-ascii-double[rendered,ascii]
                ╔══════╦══════════╦══════════╦══════════╗
                ║      ║ column 1 ║ column 2 ║ column 3 ║
                ╠══════╬══════════╬══════════╩══════════╣
                ║ row1 ║ 1        ║ 2                   ║
                ╠══════╬══════════╣                     ║
                ║ row2 ║ a        ║                     ║
                ╚══════╩══════════╩═════════════════════╝
                // end::table-ascii-double
                """
            },
            {
                CrossingBorder()
            } to {
                """
                // snippet::table-ascii-crossing[rendered,ascii]
                +------+----------+----------+----------+
                |      | column 1 | column 2 | column 3 |
                +------+----------+----------+----------+
                | row1 | 1        | 2                   |
                +------+----------+                     |
                | row2 | a        |                     |
                +------+----------+---------------------+
                // end::table-ascii-crossing
                """
            },
            {
                BordersOf(
                    SingleBorder.RoundedCorners(),
                    SingleBorder.Dashed2Edges(),
                    SingleBorder.Intersections()
                )
            } to {
                """
                // snippet::table-ascii-rounded-dashed[rendered,ascii]
                ╭╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌┬╌╌╌╌╌╌╌╌╌╌╮
                ╎      ╎ column 1 ╎ column 2 ╎ column 3 ╎
                ├╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌┤
                ╎ row1 ╎ 1        ╎ 2                   ╎
                ├╌╌╌╌╌╌┼╌╌╌╌╌╌╌╌╌╌┤                     ╎
                ╎ row2 ╎ a        ╎                     ╎
                ╰╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌┴╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╯
                // end::table-ascii-rounded-dashed
                """
            },
        ).forEach { (borderSet, expected) ->
            // snippet::table-ascii-dsl[dsl,ascii]
            val table = krow {
                header {
                    row {
                        column("column 1")
                        column("column 2") { width = 10 }
                        column("column 3")
                    }
                }
                body {
                    row("row1") {
                        cell("1")
                        cell("2") { colSpan = 2; rowSpan = 2 }
                    }
                    row("row2") {
                        cell("a")
                    }
                }
            }
            // end::table-ascii-dsl

            assertEquals(
                expected()
                    .trimIndent()
                    .lines()
                    .drop(1)
                    .dropLast(1)
                    .joinToString(separator = "\n"),
                AsciiTableFormatter(borderSet()).print(table)
            )
        }
    }

    @Test
    fun docsAsciiRendererTableOptions() {
        listOf<Pair<() -> Krow.Table, () -> String>>(
            {
                // snippet::table-ascii-options-width-dsl[ascii,options]
                krow {
                    header {
                        row {
                            column("column 1")
                            column("column 2") { width = 16 }
                            column("column 3")
                        }
                    }
                    bodyRow("row1") { cells("1", "2", "3") }
                    bodyRow("row2") { cells("a", "b", "c") }

                    cellAt("row1", "column 2") {
                        content = "This is some long string which should wrap at the " +
                            "appropriate width, which will also chop down reallyreallyreallyreallyreallylong " +
                            "words if needed"
                    }
                }
                // end::table-ascii-options-width-dsl
            } to {
                """
                // snippet::table-ascii-options-width-rendered[ascii,options]
                ┌──────┬──────────┬────────────────┬──────────┐
                │      │ column 1 │ column 2       │ column 3 │
                ├──────┼──────────┼────────────────┼──────────┤
                │ row1 │ 1        │ This is some   │ 3        │
                │      │          │ long string    │          │
                │      │          │ which should   │          │
                │      │          │ wrap at the    │          │
                │      │          │ appropriate    │          │
                │      │          │ width, which   │          │
                │      │          │ will also chop │          │
                │      │          │ down reallyre- │          │
                │      │          │ allyreallyrea- │          │
                │      │          │ llyreallylong  │          │
                │      │          │ words if       │          │
                │      │          │ needed         │          │
                ├──────┼──────────┼────────────────┼──────────┤
                │ row2 │ a        │ b              │ c        │
                └──────┴──────────┴────────────────┴──────────┘
                // end::table-ascii-options-width-rendered
                """
            },
            {
                // snippet::table-ascii-options-header-row-dsl[ascii,options]
                krow {
                    includeHeaderRow = false
                    header {
                        row {
                            column("column 1")
                            column("column 2") { width = 16 }
                            column("column 3")
                        }
                    }
                    bodyRow("row1") { cells("1", "2", "3") }
                    bodyRow("row2") { cells("a", "b", "c") }

                    cellAt("row1", "column 2") {
                        content = "This is some long string which should wrap at the " +
                            "appropriate width, which will also chop down reallyreallyreallyreallyreallylong " +
                            "words if needed"
                    }
                }
                // end::table-ascii-options-header-row-dsl
            } to {
                """
                // snippet::table-ascii-options-header-row-rendered[ascii,options]
                ┌──────┬───┬────────────────┬───┐
                │ row1 │ 1 │ This is some   │ 3 │
                │      │   │ long string    │   │
                │      │   │ which should   │   │
                │      │   │ wrap at the    │   │
                │      │   │ appropriate    │   │
                │      │   │ width, which   │   │
                │      │   │ will also chop │   │
                │      │   │ down reallyre- │   │
                │      │   │ allyreallyrea- │   │
                │      │   │ llyreallylong  │   │
                │      │   │ words if       │   │
                │      │   │ needed         │   │
                ├──────┼───┼────────────────┼───┤
                │ row2 │ a │ b              │ c │
                └──────┴───┴────────────────┴───┘
                // end::table-ascii-options-header-row-rendered
                """
            },
            {
                // snippet::table-ascii-options-leading-column-dsl[ascii,options]
                krow {
                    includeLeadingColumn = false
                    header {
                        row {
                            column("column 1")
                            column("column 2") { width = 16 }
                            column("column 3")
                        }
                    }
                    bodyRow("row1") { cells("1", "2", "3") }
                    bodyRow("row2") { cells("a", "b", "c") }

                    cellAt("row1", "column 2") {
                        content = "This is some long string which should wrap at the " +
                            "appropriate width, which will also chop down reallyreallyreallyreallyreallylong " +
                            "words if needed"
                    }
                }
                // end::table-ascii-options-leading-column-dsl
            } to {
                """
                // snippet::table-ascii-options-leading-column-rendered[ascii,options]
                ┌──────────┬────────────────┬──────────┐
                │ column 1 │ column 2       │ column 3 │
                ├──────────┼────────────────┼──────────┤
                │ 1        │ This is some   │ 3        │
                │          │ long string    │          │
                │          │ which should   │          │
                │          │ wrap at the    │          │
                │          │ appropriate    │          │
                │          │ width, which   │          │
                │          │ will also chop │          │
                │          │ down reallyre- │          │
                │          │ allyreallyrea- │          │
                │          │ llyreallylong  │          │
                │          │ words if       │          │
                │          │ needed         │          │
                ├──────────┼────────────────┼──────────┤
                │ a        │ b              │ c        │
                └──────────┴────────────────┴──────────┘
                // end::table-ascii-options-leading-column-rendered
                """
            }
        ).forEach { (table, expected) ->
            assertEquals(
                expected()
                    .trimIndent()
                    .lines()
                    .drop(1)
                    .dropLast(1)
                    .joinToString(separator = "\n"),
                AsciiTableFormatter().print(table())
            )
        }
    }

    @Test
    fun docsHtmlTableFormatter() {
        // snippet::table-html-table-formatter[ascii]
        val table = krow {
            // ...
        }
        HtmlTableFormatter().print(table)
        // end::table-html-table-formatter
    }

    @Test
    fun docsHtmlTableFormatterRendered() {
        listOf<() -> String>(
            {
                """
                // snippet::table-html-rendered[rendered,html]
                <table>
                  <thead>
                  <tr>
                    <th></th>
                    <th>column 1</th>
                    <th>column 2</th>
                    <th>column 3</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td>row1</td>
                    <td>1</td>
                    <td rowspan="2" colspan="2">2</td>
                  </tr>
                  <tr>
                    <td>row2</td>
                    <td>a</td>
                  </tr>
                  </tbody>
                </table>
                // end::table-html-rendered
                """
            }
        ).forEach { expected ->
            val table = krow {
                header {
                    row {
                        column("column 1")
                        column("column 2") { width = 10 }
                        column("column 3")
                    }
                }
                body {
                    row("row1") {
                        cell("1")
                        cell("2") { colSpan = 2; rowSpan = 2 }
                    }
                    row("row2") {
                        cell("a")
                    }
                }
            }

            assertEquals(
                expected()
                    .trimIndent()
                    .lines()
                    .drop(1)
                    .dropLast(1)
                    .joinToString(separator = "\n").trim(),
                HtmlTableFormatter().print(table).trim()
            )
        }
    }
}
