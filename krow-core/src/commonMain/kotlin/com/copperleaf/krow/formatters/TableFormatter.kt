package com.copperleaf.krow.formatters

import com.copperleaf.krow.model.Krow

interface TableFormatter<T> {
    fun print(table: Krow.Table): T
}
