package com.eden.krow

interface TableFormatter<T> {

    fun print(table: KrowTable): T

}