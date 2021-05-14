package com.copperleaf.krow.utils

open class CrossingBorder : BorderSet {

    override val tl: String get() = "+"
    override val ti: String get() = "+"
    override val tr: String get() = "+"

    override val cl: String get() = "+"
    override val ci: String get() = "+"
    override val cr: String get() = "+"

    override val bl: String get() = "+"
    override val bi: String get() = "+"
    override val br: String get() = "+"

    override val v: String get() = "|"
    override val h: String get() = "-"
}
