package com.copperleaf.krow.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class TestDrawing {

    @Test
    fun testCreateLineOf() {
        val expected = "+++++++"

        assertEquals(
            expected,
            createLineOf('+', 7)
        )
    }

    @Test
    fun testCreateLinesOf() {
        val inputLine = "+++++++"
        val expected = listOf(
            "+++++++",
            "+++++++",
            "+++++++",
        )

        assertEquals(
            expected,
            createLinesOf(inputLine, 3)
        )
    }

    @Test
    fun testCreateBlankLinesOf() {
        val expected = listOf(
            "+++++++",
            "+++++++",
            "+++++++",
        )

        assertEquals(
            expected,
            createBlankLinesOf('+', width = 7, height = 3)
        )
    }
}
