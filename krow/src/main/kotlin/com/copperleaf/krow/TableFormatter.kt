package com.copperleaf.krow

interface TableFormatter<T> {

    fun print(table: KrowTable): T

}