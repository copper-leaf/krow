---

---

# Building Tables

Tables are built with Kotlin's type-safe builder syntax. You open a `krow` closure, and are then able to call the 
various methods and set properties that build your table. A complete example is shown below: 

```kotlin
val table = krow {
    cell("col1", "row1") { content = "1-1" }
    cell("col1", "row2") { content = "1-2" }

    cell("col2", "row1") { content = "2-1" }
    cell("col2", "row2") { content = "2-2" }

    cell("col3", "row1") { content = "3-1" }
    cell("col3", "row2") { content = "3-2" }

    table {
        wrapTextAt = 30
        horizontalAlignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.TOP
    }
}
```

A Table is build of Cells arranged in a 2D grid.

The `cell()` method accepts a pair of keys for `column, row` index. If either of these two keys do not exist, then the 
table will add a row or column accordingly, and then set the properties of that cell with the closure opened at that 
cell.

The `row()` method takes a key for a row, and applies the closure to all cells in that row. If the row does not exist, 
the table will add the row before applying the closure to all its cells. In the same way, you can use the `column()` to
setup an entire column of cells.

the `table()` method applies the closure to every cell in the entire table.

You can also set the ordering of rows and columns with the `columns()`, and `rows()` methods, which each take a list of
Strings as the names of the columns or rows to add. Other rows or columns may be added later with the above methods, but
they will be added at the end or added in place if the column or row already exists. At this time, it is not possible to 
reorder columns or rows once they have been created, so if you need a specific order, set it before configuring any 
individual cells, columns, or rows.
