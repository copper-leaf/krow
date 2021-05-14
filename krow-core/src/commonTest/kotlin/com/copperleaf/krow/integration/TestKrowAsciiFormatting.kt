package com.copperleaf.krow.integration

class TestKrowAsciiFormatting {

//    lateinit var input: KrowTable
//
//    @BeforeTest
//    fun setup() {
//        input = krow {
//            cell("col1", "row1") { content = "1-1" }
//            cell("col1", "row2") { content = "1-2" }
//
//            cell("col2", "row1") { content = "2-1" }
//            cell("col2", "row2") { content = "2-2" }
//
//            cell("col3", "row1") { content = "3-1" }
//            cell("col3", "row2") { content = "3-2" }
//
//            table {
//                wrapTextAt = 30
//                horizontalAlignment = HorizontalAlignment.CENTER
//                verticalAlignment = VerticalAlignment.TOP
//            }
//        }
//    }
//
//    @Test
//    fun testOuterIntersections() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val tl: String = "1"
//            override val tr: String = "2"
//            override val bl: String = "3"
//            override val br: String = "4"
//
//            override val ti: String = "5"
//            override val bi: String = "6"
//
//            override val cl: String = "7"
//            override val cr: String = "8"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            1──────5──────5──────5──────2
//            │      │ col1 │ col2 │ col3 │
//            7──────┼──────┼──────┼──────8
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            7──────┼──────┼──────┼──────8
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            3──────6──────6──────6──────4
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testOuterEdges() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val th: String = "1"
//            override val bh: String = "2"
//
//            override val vl: String = "3"
//            override val vr: String = "4"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌111111┬111111┬111111┬111111┐
//            3      │ col1 │ col2 │ col3 4
//            ├──────┼──────┼──────┼──────┤
//            3 row1 │ 1-1  │ 2-1  │ 3-1  4
//            ├──────┼──────┼──────┼──────┤
//            3 row2 │ 1-2  │ 2-2  │ 3-2  4
//            └222222┴222222┴222222┴222222┘
//        """.trimIndent().trim()
//
//        assertEquals("\n\n$expected\n\n", "\n\n$output\n\n")
//    }
//
//    @Test
//    fun testInnerIntersections() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val ci: String = "1"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            ├──────1──────1──────1──────┤
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            ├──────1──────1──────1──────┤
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testInnerEdges() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val ch: String = "1"
//            override val vc: String = "2"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      2 col1 2 col2 2 col3 │
//            ├111111┼111111┼111111┼111111┤
//            │ row1 2 1-1  2 2-1  2 3-1  │
//            ├111111┼111111┼111111┼111111┤
//            │ row2 2 1-2  2 2-2  2 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHeaderIntersections() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val hl: String = "1"
//            override val hi: String = "2"
//            override val hr: String = "3"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            1──────2──────2──────2──────3
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            ├──────┼──────┼──────┼──────┤
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHeaderEdges() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val hh: String = "1"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            ├111111┼111111┼111111┼111111┤
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            ├──────┼──────┼──────┼──────┤
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testLeaderIntersections() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val tld: String = "1"
//            override val cld: String = "2"
//            override val bld: String = "3"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────1──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            ├──────2──────┼──────┼──────┤
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            ├──────2──────┼──────┼──────┤
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────3──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testLeaderEdges() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val vld: String = "1"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      1 col1 │ col2 │ col3 │
//            ├──────┼──────┼──────┼──────┤
//            │ row1 1 1-1  │ 2-1  │ 3-1  │
//            ├──────┼──────┼──────┼──────┤
//            │ row2 1 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHeaderLeaderIntersection() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val hi: String = "1"
//            override val cld: String = "2"
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            ├──────1──────1──────1──────┤
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            ├──────2──────┼──────┼──────┤
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
// // Test showing/hiding dividers
// // ----------------------------------------------------------------------------------------------------------------------
//
//    @Test
//    fun testHideTop() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showT: Boolean get() = false
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            │      │ col1 │ col2 │ col3 │
//            ├──────┼──────┼──────┼──────┤
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            ├──────┼──────┼──────┼──────┤
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHideHorizontals() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showH: Boolean get() = false
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHideBottom() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showB: Boolean get() = false
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            ├──────┼──────┼──────┼──────┤
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            ├──────┼──────┼──────┼──────┤
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHideHorizontalsShowHeader() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showH: Boolean get() = false
//            override val showHeader: Boolean get() = true
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────┐
//            │      │ col1 │ col2 │ col3 │
//            ├──────┼──────┼──────┼──────┤
//            │ row1 │ 1-1  │ 2-1  │ 3-1  │
//            │ row2 │ 1-2  │ 2-2  │ 3-2  │
//            └──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHideLeft() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showL: Boolean get() = false
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ──────┬──────┬──────┬──────┐
//                  │ col1 │ col2 │ col3 │
//            ──────┼──────┼──────┼──────┤
//             row1 │ 1-1  │ 2-1  │ 3-1  │
//            ──────┼──────┼──────┼──────┤
//             row2 │ 1-2  │ 2-2  │ 3-2  │
//            ──────┴──────┴──────┴──────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHideVerticals() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showV: Boolean get() = false
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌────────────────────────┐
//            │       col1  col2  col3 │
//            ├────────────────────────┤
//            │ row1  1-1   2-1   3-1  │
//            ├────────────────────────┤
//            │ row2  1-2   2-2   3-2  │
//            └────────────────────────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHideRight() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showR: Boolean get() = false
//        })
//        val output = underTest.print(input)
//            .lines()
//            .map { it.trim() }
//            .joinToString(separator = "\n")
//            .trimIndent().trim()
//
//        val expected = """
//            ┌──────┬──────┬──────┬──────
//            │      │ col1 │ col2 │ col3
//            ├──────┼──────┼──────┼──────
//            │ row1 │ 1-1  │ 2-1  │ 3-1
//            ├──────┼──────┼──────┼──────
//            │ row2 │ 1-2  │ 2-2  │ 3-2
//            └──────┴──────┴──────┴──────
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
//
//    @Test
//    fun testHideVerticalsShowLeader() {
//        val underTest = AsciiTableFormatter(object : SingleBorder() {
//            override val showV: Boolean get() = false
//            override val showLeader: Boolean get() = true
//        })
//        val output = underTest.print(input).trim()
//
//        val expected = """
//            ┌──────┬──────────────────┐
//            │      │ col1  col2  col3 │
//            ├──────┼──────────────────┤
//            │ row1 │ 1-1   2-1   3-1  │
//            ├──────┼──────────────────┤
//            │ row2 │ 1-2   2-2   3-2  │
//            └──────┴──────────────────┘
//        """.trimIndent().trim()
//
//        assertEquals(expected, output)
//    }
}
