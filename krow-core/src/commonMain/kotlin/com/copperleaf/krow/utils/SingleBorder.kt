package com.copperleaf.krow.utils

open class SingleBorder : BorderSet {

    override val tl: Char get() = '┌'
    override val ti: Char get() = '┬'
    override val tr: Char get() = '┐'

    override val cl: Char get() = '├'
    override val ci: Char get() = '┼'
    override val cr: Char get() = '┤'

    override val bl: Char get() = '└'
    override val bi: Char get() = '┴'
    override val br: Char get() = '┘'

    override val v: Char get() = '│'
    override val h: Char get() = '─'
}
