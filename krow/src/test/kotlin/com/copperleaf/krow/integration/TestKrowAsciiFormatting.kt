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
import strikt.api.expect
import strikt.assertions.isEqualTo

class TestKrowAsciiFormatting {

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
    fun testOuterIntersections() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val tl: Char = '1'
            override val tr: Char = '2'
            override val bl: Char = '3'
            override val br: Char = '4'

            override val ti: Char = '5'
            override val bi: Char = '6'

            override val cl: Char = '7'
            override val cr: Char = '8'
        })
        val output = underTest.print(input).trim()

        val expected = """
            1──────5──────5──────5──────2
            │      │ col1 │ col2 │ col3 │
            7──────┼──────┼──────┼──────8
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            7──────┼──────┼──────┼──────8
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            3──────6──────6──────6──────4
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testOuterEdges() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val th: Char = '1'
            override val bh: Char = '2'

            override val vl: Char = '3'
            override val vr: Char = '4'

        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌111111┬111111┬111111┬111111┐
            3      │ col1 │ col2 │ col3 4
            ├──────┼──────┼──────┼──────┤
            3 row1 │ 1-1  │ 2-1  │ 3-1  4
            ├──────┼──────┼──────┼──────┤
            3 row2 │ 1-2  │ 2-2  │ 3-2  4
            └222222┴222222┴222222┴222222┘
        """.trimIndent().trim()

        expect("\n\n$output\n\n").isEqualTo("\n\n$expected\n\n")
    }

    @Test
    fun testInnerIntersections() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val ci: Char = '1'

        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            ├──────1──────1──────1──────┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────1──────1──────1──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testInnerEdges() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val ch: Char = '1'
            override val vc: Char = '2'
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      2 col1 2 col2 2 col3 │
            ├111111┼111111┼111111┼111111┤
            │ row1 2 1-1  2 2-1  2 3-1  │
            ├111111┼111111┼111111┼111111┤
            │ row2 2 1-2  2 2-2  2 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHeaderIntersections() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val hl: Char = '1'
            override val hi: Char = '2'
            override val hr: Char = '3'
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            1──────2──────2──────2──────3
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────┼──────┼──────┼──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHeaderEdges() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val hh: Char = '1'
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            ├111111┼111111┼111111┼111111┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────┼──────┼──────┼──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testLeaderIntersections() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val tld: Char = '1'
            override val cld: Char = '2'
            override val bld: Char = '3'
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────1──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            ├──────2──────┼──────┼──────┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────2──────┼──────┼──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────3──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testLeaderEdges() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val vld: Char = '1'
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      1 col1 │ col2 │ col3 │
            ├──────┼──────┼──────┼──────┤
            │ row1 1 1-1  │ 2-1  │ 3-1  │
            ├──────┼──────┼──────┼──────┤
            │ row2 1 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHeaderLeaderIntersection() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val hi: Char = '1'
            override val cld: Char = '2'
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            ├──────1──────1──────1──────┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────2──────┼──────┼──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

// Test showing/hiding dividers
//----------------------------------------------------------------------------------------------------------------------

    @Test
    fun testHideTop() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showT: Boolean get() = false
        })
        val output = underTest.print(input).trim()

        val expected = """
            │      │ col1 │ col2 │ col3 │
            ├──────┼──────┼──────┼──────┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────┼──────┼──────┼──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHideHorizontals() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showH: Boolean get() = false
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHideBottom() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showB: Boolean get() = false
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            ├──────┼──────┼──────┼──────┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            ├──────┼──────┼──────┼──────┤
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHideHorizontalsShowHeader() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showH: Boolean get() = false
            override val showHeader: Boolean get() = true
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────┬──────┬──────┐
            │      │ col1 │ col2 │ col3 │
            ├──────┼──────┼──────┼──────┤
            │ row1 │ 1-1  │ 2-1  │ 3-1  │
            │ row2 │ 1-2  │ 2-2  │ 3-2  │
            └──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHideLeft() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showL: Boolean get() = false
        })
        val output = underTest.print(input).trim()

        val expected = """
            ──────┬──────┬──────┬──────┐
                  │ col1 │ col2 │ col3 │
            ──────┼──────┼──────┼──────┤
             row1 │ 1-1  │ 2-1  │ 3-1  │
            ──────┼──────┼──────┼──────┤
             row2 │ 1-2  │ 2-2  │ 3-2  │
            ──────┴──────┴──────┴──────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHideVerticals() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showV: Boolean get() = false
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌────────────────────────┐
            │       col1  col2  col3 │
            ├────────────────────────┤
            │ row1  1-1   2-1   3-1  │
            ├────────────────────────┤
            │ row2  1-2   2-2   3-2  │
            └────────────────────────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHideRight() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showR: Boolean get() = false
        })
        val output = underTest.print(input)
                .lines()
                .map { it.trim() }
                .joinToString(separator = "\n")
                .trimIndent().trim()

        val expected = """
            ┌──────┬──────┬──────┬──────
            │      │ col1 │ col2 │ col3
            ├──────┼──────┼──────┼──────
            │ row1 │ 1-1  │ 2-1  │ 3-1
            ├──────┼──────┼──────┼──────
            │ row2 │ 1-2  │ 2-2  │ 3-2
            └──────┴──────┴──────┴──────
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHideVerticalsShowLeader() {
        val underTest = AsciiTableFormatter(object : SingleBorder() {
            override val showV: Boolean get() = false
            override val showLeader: Boolean get() = true
        })
        val output = underTest.print(input).trim()

        val expected = """
            ┌──────┬──────────────────┐
            │      │ col1  col2  col3 │
            ├──────┼──────────────────┤
            │ row1 │ 1-1   2-1   3-1  │
            ├──────┼──────────────────┤
            │ row2 │ 1-2   2-2   3-2  │
            └──────┴──────────────────┘
        """.trimIndent().trim()

        expect(output).isEqualTo(expected)
    }

}