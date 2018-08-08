package com.copperleaf.krow.borders

import com.copperleaf.krow.BorderSet

class DoubleBorder : BorderSet {

    override val tl: Char get() { return '╔' }
    override val t:  Char get() { return '╦' }
    override val tr: Char get() { return '╗' }
    override val cl: Char get() { return '╠' }
    override val c:  Char get() { return '╬' }
    override val cr: Char get() { return '╣' }
    override val bl: Char get() { return '╚' }
    override val b:  Char get() { return '╩' }
    override val br: Char get() { return '╝' }
    override val v:  Char get() { return '║' }
    override val h:  Char get() { return '═' }

}