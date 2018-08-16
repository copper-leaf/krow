package com.copperleaf.krow.borders

open class NoBorder : SingleBorder() {

    override val showT: Boolean get() = false
    override val showH: Boolean get() = false
    override val showHeader: Boolean get() = false
    override val showB: Boolean get() = false
    override val showL: Boolean get() = false
    override val showV: Boolean get() = false
    override val showLeader: Boolean get() = false
    override val showR: Boolean get() = false

}