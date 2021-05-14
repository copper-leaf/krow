package com.copperleaf.krow.formatters.html

open class DefaultHtmlAttributes : HtmlAttributes {

    override val tableId: String get() = ""
    override val tableClasses: List<String> get() = emptyList()
    override val trClasses: List<String> get() = emptyList()
    override val tdClasses: List<String> get() = emptyList()
    override val headerClasses: List<String> get() = emptyList()
    override val leaderClasses: List<String> get() = emptyList()
}
