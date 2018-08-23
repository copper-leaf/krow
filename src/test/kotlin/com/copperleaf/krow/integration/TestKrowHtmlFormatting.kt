package com.copperleaf.krow.integration

import com.copperleaf.krow.HorizontalAlignment
import com.copperleaf.krow.KrowTable
import com.copperleaf.krow.VerticalAlignment
import com.copperleaf.krow.formatters.html.DefaultHtmlAttributes
import com.copperleaf.krow.formatters.html.HtmlTableFormatter
import com.copperleaf.krow.krow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class TestKrowHtmlFormatting {

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

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHtmlKrowTableClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val tableClass: String get() = "krow-class"
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

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHtmlKrowTrClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val trClass: String get() = "krow-class"
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

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHtmlKrowThClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val thClass: String get() = "krow-class"
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

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHtmlKrowTdClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val tdClass: String get() = "krow-class"
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

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHtmlKrowHeaderClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val headerClass: String get() = "krow-class"
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

        expect(output).isEqualTo(expected)
    }

    @Test
    fun testHtmlKrowLeaderClass() {
        val underTest = HtmlTableFormatter(object : DefaultHtmlAttributes() {
            override val leaderClass: String get() = "krow-class"
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

        expect(output).isEqualTo(expected)
    }

}