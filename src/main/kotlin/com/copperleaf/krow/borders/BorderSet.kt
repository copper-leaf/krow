package com.copperleaf.krow.borders

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

    /** Header Left-Side Intersection */
    val hl: Char get() = cl

    /** Header Intersection */
    val hi: Char get() = ci

    /** Header Right-Side Intersection */
    val hr: Char get() = cr

    /** Header Horizontal Line */
    val hh: Char get() = ch

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

    /** Vertical Line on left edge */
    val tld: Char get() = ti

    /** Vertical Line on right edge */
    val cld: Char get() = ci

    /** Vertical Line in center */
    val bld: Char get() = bi

    /** Vertical Line in center */
    val vld: Char get() = vc

    /** Vertical Line in center */
    val hld: Char get() = if(hi != ci) hi else if(cld != ci) cld else ci

    /** Newline */
    val nl: Char get() = '\n'

}