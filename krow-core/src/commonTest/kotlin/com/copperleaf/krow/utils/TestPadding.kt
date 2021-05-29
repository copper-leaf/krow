package com.copperleaf.krow.utils

import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment
import kotlin.test.Test
import kotlin.test.assertEquals

class TestPadding {

// Vertical Alignment
// ---------------------------------------------------------------------------------------------------------------------

    @Test
    fun testPadTop() {
        val underTest = "One"
        val expected = """
            |+++
            |+++
            |One
            """.trimMargin()

        assertEquals(
            expected,
            underTest.padTop(3, padChar = "+")
        )
        assertEquals(
            expected,
            underTest.padVertical(3, padChar = "+", alignment = VerticalAlignment.BOTTOM)
        )
    }

    @Test
    fun testPadBottom() {
        val underTest = "One"
        val expected = """
            |One
            |+++
            |+++
            """.trimMargin()

        assertEquals(
            expected,
            underTest.padBottom(3, padChar = "+")
        )
        assertEquals(
            expected,
            underTest.padVertical(3, padChar = "+", alignment = VerticalAlignment.TOP)
        )
    }

    @Test
    fun testPadCenterVertical() {
        val underTest = "One"
        val expected = """
            |+++
            |One
            |+++
            """.trimMargin()

        assertEquals(
            expected,
            underTest.padCenterVertical(3, padChar = "+")
        )
        assertEquals(
            expected,
            underTest.padVertical(3, padChar = "+", alignment = VerticalAlignment.CENTER)
        )
    }

// Horizontal Alignment
// ---------------------------------------------------------------------------------------------------------------------

    @Test
    fun testPadStart() {
        val underTest = "One"
        val expected = "+++One"

        assertEquals(
            expected,
            underTest.padStart(6, padChar = '+')
        )
        assertEquals(
            expected,
            underTest.padHorizontal(6, padChar = "+", alignment = HorizontalAlignment.RIGHT)
        )
    }

    @Test
    fun testPadEnd() {
        val underTest = "One"
        val expected = "One+++"

        assertEquals(
            expected,
            underTest.padEnd(6, padChar = '+')
        )
        assertEquals(
            expected,
            underTest.padHorizontal(6, padChar = "+", alignment = HorizontalAlignment.LEFT)
        )
    }

    @Test
    fun testPadCenterHorizontal() {
        val underTest = "One"
        val expected = "+One++"

        assertEquals(
            expected,
            underTest.padCenterHorizontal(6, padChar = "+")
        )
        assertEquals(
            expected,
            underTest.padHorizontal(6, padChar = "+", alignment = HorizontalAlignment.CENTER)
        )
    }
}
