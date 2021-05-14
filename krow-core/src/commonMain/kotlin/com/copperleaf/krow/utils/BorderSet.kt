package com.copperleaf.krow.utils

interface BorderSet {

    /** Whether to print the top border */
    val showT: Boolean get() = true

    /** Whether to print the horizontal non-header borders */
    val showH: Boolean get() = true

    /** Whether to print the horizontal non-header borders */
    val showHeader: Boolean get() = showH

    /** Whether to print the bottom border */
    val showB: Boolean get() = true

    /** Whether to print the left border */
    val showL: Boolean get() = true

    /** Whether to print the vertical non-leader borders */
    val showV: Boolean get() = true

    /** Whether to print the vertical leader border */
    val showLeader: Boolean get() = showV

    /** Whether to print the right border */
    val showR: Boolean get() = true

    /** Top-Left Corner */
    val tl: String

    /** Top Intersection */
    val ti: String

    /** Top-Right Corner */
    val tr: String

    /** Center Left-Side Intersection */
    val cl: String

    /** Center Intersection */
    val ci: String

    /** Center Right-Side Intersection */
    val cr: String

    /** Bottom-Left Corner */
    val bl: String

    /** Bottom Intersection */
    val bi: String

    /** Bottom-Corner Corner */
    val br: String

    /** Default horizontal border */
    val h: String

    /** Default vertical border */
    val v: String

    /** Top Horizontal Line */
    val th: String get() = h

    /** Header Left-Side Intersection */
    val hl: String get() = cl

    /** Header Intersection */
    val hi: String get() = ci

    /** Header Right-Side Intersection */
    val hr: String get() = cr

    /** Header Horizontal Line */
    val hh: String get() = ch

    /** Center Horizontal Line */
    val ch: String get() = h

    /** Bottom Horizontal Line */
    val bh: String get() = h

    /** Vertical Line on left edge */
    val vl: String get() = v

    /** Vertical Line on right edge */
    val vr: String get() = v

    /** Vertical Line in center */
    val vc: String get() = v

    /** Vertical Line on left edge */
    val tld: String get() = ti

    /** Vertical Line on right edge */
    val cld: String get() = ci

    /** Vertical Line in center */
    val bld: String get() = bi

    /** Vertical Line in center */
    val vld: String get() = vc

    /** Vertical Line in center */
    val hld: String get() = if (hi != ci) hi else if (cld != ci) cld else ci

    /** Newline */
    val nl: String get() = "\n"
}
