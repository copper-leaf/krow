package com.copperleaf.krow.utils

import com.copperleaf.krow.model.HorizontalAlignment
import kotlin.test.Test
import kotlin.test.assertEquals

class TestWrapping {

    @Test
    fun testWrapNull() {
        val underTest: String? = null
        val expected = "+++++++"

        assertEquals(
            expected,
            underTest.wrap(7, padChar = "+")
        )
    }

    @Test
    fun testWrapLongerThanContent() {
        val underTest = "one"
        val expected = "one++++"

        assertEquals(
            expected,
            underTest.wrap(7, padChar = "+")
        )
    }

    @Test
    fun testWrapWithNewlines() {
        val underTest = "one\ntwo\nthree"
        val expected = """
            |one++++
            |two++++
            |three++
        """.trimMargin()

        assertEquals(
            expected,
            underTest.wrap(7, padChar = "+")
        )
    }

    @Test
    fun testWrapWithNewlinesAlignRight() {
        val underTest = "one\ntwo\nthree"
        val expected = """
            |++++one
            |++++two
            |++three
        """.trimMargin()

        assertEquals(
            expected,
            underTest.wrap(7, padChar = "+", alignment = HorizontalAlignment.RIGHT)
        )
    }

    @Test
    fun testWrapWithNewlinesAlignCenter() {
        val underTest = "one\ntwo\nthree"
        val expected = """
            |++one+++
            |++two+++
            |+three++
        """.trimMargin()

        assertEquals(
            expected,
            underTest.wrap(8, padChar = "+", alignment = HorizontalAlignment.CENTER)
        )
    }

    @Test
    fun testNormalWrapping() {
        val underTest = "one two three four five"
        val expected = """
            |one two
            |three++
            |four+++
            |five+++
        """.trimMargin()

        assertEquals(
            expected,
            underTest.wrap(7, padChar = "+")
        )
    }

    @Test
    fun testWordsChoppedDown() {
        assertEquals(
            """
            |one+++
            |two+++
            |three+
            |fourf-
            |ives++
            """.trimMargin(),
            "one two three fourfives".wrap(6, padChar = "+")
        )
        assertEquals(
            """
            |one two
            |three++
            |fourfi-
            |ves++++
            """.trimMargin(),
            "one two three fourfives".wrap(7, padChar = "+")
        )

        assertEquals(
            """
            |one two+
            |three f-
            |ourfives
            """.trimMargin(),
            "one two three fourfives".wrap(8, padChar = "+")
        )

        assertEquals(
            """
            |one two+
            |three f-
            |ourfive-
            |sixseve-
            |neightn-
            |ine+++++
            """.trimMargin(),
            "one two three fourfivesixseveneightnine".wrap(8, padChar = "+")
        )
    }

    @Test
    fun testGetHyphenatedSplits() {
        assertEquals(
            listOf(
                "fourf-",
                "ives"
            ),
            "fourfives".getHyphenatedSplits(6, 6)
        )
        assertEquals(
            listOf(
                "123"
            ),
            "123".getHyphenatedSplits(4, 0)
        )
        assertEquals(
            listOf(
                "1234",
            ),
            "1234".getHyphenatedSplits(4, 0)
        )

        assertEquals(
            listOf(
                "123-",
                "45"
            ),
            "12345".getHyphenatedSplits(4, 0)
        )
        assertEquals(
            listOf(
                "123-",
                "456"
            ),
            "123456".getHyphenatedSplits(4, 0)
        )
        assertEquals(
            listOf(
                "123-",
                "4567"
            ),
            "1234567".getHyphenatedSplits(4, 0)
        )
        assertEquals(
            listOf(
                "123-",
                "456-",
                "78"
            ),
            "12345678".getHyphenatedSplits(4, 0)
        )
        assertEquals(
            listOf(
                "123-",
                "456-",
                "789"
            ),
            "123456789".getHyphenatedSplits(4, 0)
        )
        assertEquals(
            listOf(
                "123-",
                "456-",
                "7890"
            ),
            "1234567890".getHyphenatedSplits(4, 0)
        )
        assertEquals(
            listOf(
                "12-",
                "345-",
                "678-",
                "90"
            ),
            "1234567890".getHyphenatedSplits(4, 1)
        )
    }
}
