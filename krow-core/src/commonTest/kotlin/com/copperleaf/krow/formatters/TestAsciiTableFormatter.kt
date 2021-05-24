package com.copperleaf.krow.formatters

/* ktlint-disable max-line-length */
class TestAsciiTableFormatter {

//    val complexTable = Krow.Table(
//        colSpec = listOf(24, 12, 10, 10),
//        rowSpec = listOf(2, 1, 1, 1, 1),
//        rows = listOf(
//            Krow.Row(
//                Krow.Cell("Header row, column 1\n(header rows optional)"),
//                Krow.Cell("Header 2", width = 10),
//                Krow.Cell("Header 3"),
//                Krow.Cell("Header 4"),
//            ),
//            Krow.Row(
//                Krow.Cell("body row 1, column 1"),
//                Krow.Cell("column 2"),
//                Krow.Cell("column 3"),
//                Krow.Cell("column 4"),
//            ),
//            Krow.Row(
//                Krow.Cell("body row 2"),
//                Krow.Cell("Cells may span columns.", colSpan = 3, horizontalAlignment = HorizontalAlignment.RIGHT),
//            ),
//            Krow.Row(
//                Krow.Cell("body row 3"),
//                Krow.Cell("Cells may span rows.", rowSpan = 2, verticalAlignment = VerticalAlignment.BOTTOM),
//                Krow.Cell("Cells may span both rows and columns.", rowSpan = 2, colSpan = 2),
//            ),
//            Row(
//                Cell("body row 4"),
//            ),
//        ),
//        includeHeaderRow = false,
//        includeLeadingColumn = false,
//    )
//
//    @Test
//    fun test1() {
//        assertEquals(
//            """
//            ┌────────────────────────┬────────────┬──────────┬──────────┐
//            │ Header row, column 1   │ Header 2   │ Header 3 │ Header 4 │
//            │ (header rows optional) │            │          │          │
//            ├────────────────────────┼────────────┼──────────┼──────────┤
//            │ body row 1, column 1   │ column 2   │ column 3 │ column 4 │
//            ├────────────────────────┼────────────┴──────────┴──────────┤
//            │ body row 2             │          Cells may span columns. │
//            ├────────────────────────┼────────────┬─────────────────────┤
//            │ body row 3             │            │ Cells may span both │
//            ├────────────────────────┤ Cells may  │ rows and columns.   │
//            │ body row 4             │ span rows. │                     │
//            └────────────────────────┴────────────┴─────────────────────┘
//            """.trimIndent(),
//            AsciiTableFormatter().print(complexTable)
//        )
//    }
//
//    @Test
//    fun test2() {
//        val input = krow {
//            headerColumns("col1", "col2")
//            row("row1") {
//                cells("1-1") { colSpan = 2 }
//            }
//            row("row2") {
//                cells("2-1")
//                cells("2-2")
//            }
//        }
//
//        assertEquals(
//            """
//            ┌──────┬──────┬──────┐
//            │      │ col1 │ col2 │
//            ├──────┼──────┴──────┤
//            │ row1 │ 1-1         │
//            ├──────┼──────┬──────┤
//            │ row2 │ 2-1  │ 2-2  │
//            └──────┴──────┴──────┘
//            """.trimIndent(),
//            AsciiTableFormatter().print(input)
//        )
//    }
//
//    @Test
//    fun testCell() {
//        Cell("Line 1").apply {
//            // default values
//            assertEquals(1, colSpan)
//            assertEquals(1, rowSpan)
//            assertEquals(1, padding)
//            assertEquals(emptyList(), children)
//
//            // computed
//            assertEquals(8, intrinsicWidthWithPadding)
//            assertEquals(1, intrinsicHeight)
//
//            // content
//            assertEquals(
//                """
//                | Line 1
//                """.trimMargin(),
//                printContentWithPadding(intrinsicWidthWithPadding, 1)
//            )
//        }
//
//        Cell("Line 1\nSecond Line").apply {
//            // default values
//            assertEquals(1, colSpan)
//            assertEquals(1, rowSpan)
//            assertEquals(1, padding)
//            assertEquals(emptyList(), children)
//
//            // computed
//            assertEquals(13, intrinsicWidthWithPadding)
//            assertEquals(2, intrinsicHeight)
//
//            // content
//            assertEquals(
//                """
//                | Line 1
//                | Second Line
//                """.trimMargin(),
//                printContentWithPadding(intrinsicWidthWithPadding, 2)
//            )
//        }
//    }
//
//    @Test
//    fun testRow() {
//        Row(
//            Cell("Header row, column 1\n(header rows optional)"),
//            Cell("Header 2"),
//            Cell("Header 3"),
//            Cell("Header 4"),
//        ).apply {
//            assertEquals(
//                listOf(24, 10, 10, 10),
//                colSpec
//            )
//            assertEquals(
//                2,
//                height
//            )
//        }
//    }
//
//    @Test
//    fun testTable() {
//        complexTable.apply {
//            assertEquals(
//                listOf(24, 12, 10, 10),
//                colSpec
//            )
//            assertEquals(
//                listOf(2, 1, 1, 1, 1),
//                rowSpec
//            )
//        }
//    }
}
