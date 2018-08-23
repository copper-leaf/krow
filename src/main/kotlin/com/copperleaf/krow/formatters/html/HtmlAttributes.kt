package com.copperleaf.krow.formatters.html

interface HtmlAttributes {

    val tableId: String
    val tableClass: String
    val trClass: String
    val tdClass: String
    val thClass: String get() = tdClass
    val headerClass: String
    val leaderClass: String

    val tableAttrs: Map<String, String?> get() = HashMap()

}