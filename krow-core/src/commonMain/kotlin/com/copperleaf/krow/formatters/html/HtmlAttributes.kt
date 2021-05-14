package com.copperleaf.krow.formatters.html

interface HtmlAttributes {

    val tableId: String
    val tableClasses: List<String>
    val trClasses: List<String>
    val tdClasses: List<String>
    val thClasses: List<String> get() = tdClasses
    val headerClasses: List<String>
    val leaderClasses: List<String>

    val tableAttrs: Map<String, String?> get() = HashMap()
}
