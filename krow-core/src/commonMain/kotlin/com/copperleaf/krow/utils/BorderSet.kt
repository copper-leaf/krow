package com.copperleaf.krow.utils

interface BorderSet {

    /** Top-Left Corner */
    val tl: Char

    /** Top Intersection */
    val ti: Char

    /** Top-Right Corner */
    val tr: Char

    /** Center Left-Side Intersection */
    val cl: Char

    /** Center Intersection */
    val ci: Char

    /** Center Right-Side Intersection */
    val cr: Char

    /** Bottom-Left Corner */
    val bl: Char

    /** Bottom Intersection */
    val bi: Char

    /** Bottom-Corner Corner */
    val br: Char

    /** Default horizontal border */
    val h: Char

    /** Default vertical border */
    val v: Char

    /** Top Horizontal Line */
    val th: Char get() = h

    /** Center Horizontal Line */
    val ch: Char get() = h

    /** Bottom Horizontal Line */
    val bh: Char get() = h

    /** Vertical Line on left edge */
    val vl: Char get() = v

    /** Vertical Line on right edge */
    val vr: Char get() = v

    /** Vertical Line in center */
    val vc: Char get() = v
}
