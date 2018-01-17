# Krow
A small library for generating tables in ASCII or HTML formats, built with Kotlin's type-safe builders

### Example input
```kotlin
val table = krow {
    cell("col1", "row1") { content = "These are all some really long columns" }
    cell("col1", "row2") { content = "These are all some really long columns" }
    cell("col1", "row3") { content = "These are all some really long columns" }

    cell("col2", "row1") { content = "These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns" }
    cell("col2", "row2") { content = "These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns" }
    cell("col2", "row3") { content = "These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns" }

    cell("col3", "row1") { content = "These are all some really long columns" }
    cell("col3", "row2") { content = "These are all some really long columns" }
    cell("col3", "row3") { content = "These are all some really long columns" }

    table {
        wrapTextAt = 30
        horizontalAlignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.TOP
    }
}

println(table.print(AsciiTableFormatter(SingleBorder())))
println(table.print(AsciiTableFormatter(DoubleBorder())))
println(table.print(AsciiTableFormatter(CrossingBorder())))
println(table.print(HtmlTableFormatter()))
```

### Example Output

**SingleBorder AsciiTable**
```
┌──────┬────────────────────────────────┬────────────────────────────────┬────────────────────────────────┐
│      │              col1              │              col2              │              col3              │
├──────┼────────────────────────────────┼────────────────────────────────┼────────────────────────────────┤
│ row1 │ These are all some really long │ These are all some really long │ These are all some really long │
│      │            columns             │   columns These are all some   │            columns             │
│      │                                │ really long columns These are  │                                │
│      │                                │  all some really long columns  │                                │
│      │                                │ These are all some really long │                                │
│      │                                │   columns These are all some   │                                │
│      │                                │ really long columns These are  │                                │
│      │                                │  all some really long columns  │                                │
├──────┼────────────────────────────────┼────────────────────────────────┼────────────────────────────────┤
│ row2 │ These are all some really long │ These are all some really long │ These are all some really long │
│      │            columns             │   columns These are all some   │            columns             │
│      │                                │ really long columns These are  │                                │
│      │                                │  all some really long columns  │                                │
│      │                                │ These are all some really long │                                │
│      │                                │   columns These are all some   │                                │
│      │                                │ really long columns These are  │                                │
│      │                                │  all some really long columns  │                                │
├──────┼────────────────────────────────┼────────────────────────────────┼────────────────────────────────┤
│ row3 │ These are all some really long │ These are all some really long │ These are all some really long │
│      │            columns             │   columns These are all some   │            columns             │
│      │                                │ really long columns These are  │                                │
│      │                                │  all some really long columns  │                                │
│      │                                │ These are all some really long │                                │
│      │                                │   columns These are all some   │                                │
│      │                                │ really long columns These are  │                                │
│      │                                │  all some really long columns  │                                │
└──────┴────────────────────────────────┴────────────────────────────────┴────────────────────────────────┘
```

**DoubleBorder AsciiTable**
```
╔══════╦════════════════════════════════╦════════════════════════════════╦════════════════════════════════╗
║      ║              col1              ║              col2              ║              col3              ║
╠══════╬════════════════════════════════╬════════════════════════════════╬════════════════════════════════╣
║ row1 ║ These are all some really long ║ These are all some really long ║ These are all some really long ║
║      ║            columns             ║   columns These are all some   ║            columns             ║
║      ║                                ║ really long columns These are  ║                                ║
║      ║                                ║  all some really long columns  ║                                ║
║      ║                                ║ These are all some really long ║                                ║
║      ║                                ║   columns These are all some   ║                                ║
║      ║                                ║ really long columns These are  ║                                ║
║      ║                                ║  all some really long columns  ║                                ║
╠══════╬════════════════════════════════╬════════════════════════════════╬════════════════════════════════╣
║ row2 ║ These are all some really long ║ These are all some really long ║ These are all some really long ║
║      ║            columns             ║   columns These are all some   ║            columns             ║
║      ║                                ║ really long columns These are  ║                                ║
║      ║                                ║  all some really long columns  ║                                ║
║      ║                                ║ These are all some really long ║                                ║
║      ║                                ║   columns These are all some   ║                                ║
║      ║                                ║ really long columns These are  ║                                ║
║      ║                                ║  all some really long columns  ║                                ║
╠══════╬════════════════════════════════╬════════════════════════════════╬════════════════════════════════╣
║ row3 ║ These are all some really long ║ These are all some really long ║ These are all some really long ║
║      ║            columns             ║   columns These are all some   ║            columns             ║
║      ║                                ║ really long columns These are  ║                                ║
║      ║                                ║  all some really long columns  ║                                ║
║      ║                                ║ These are all some really long ║                                ║
║      ║                                ║   columns These are all some   ║                                ║
║      ║                                ║ really long columns These are  ║                                ║
║      ║                                ║  all some really long columns  ║                                ║
╚══════╩════════════════════════════════╩════════════════════════════════╩════════════════════════════════╝
```

**CrossingBorder AsciiTable**
```
+------+--------------------------------+--------------------------------+--------------------------------+
|      |              col1              |              col2              |              col3              |
+------+--------------------------------+--------------------------------+--------------------------------+
| row1 | These are all some really long | These are all some really long | These are all some really long |
|      |            columns             |   columns These are all some   |            columns             |
|      |                                | really long columns These are  |                                |
|      |                                |  all some really long columns  |                                |
|      |                                | These are all some really long |                                |
|      |                                |   columns These are all some   |                                |
|      |                                | really long columns These are  |                                |
|      |                                |  all some really long columns  |                                |
+------+--------------------------------+--------------------------------+--------------------------------+
| row2 | These are all some really long | These are all some really long | These are all some really long |
|      |            columns             |   columns These are all some   |            columns             |
|      |                                | really long columns These are  |                                |
|      |                                |  all some really long columns  |                                |
|      |                                | These are all some really long |                                |
|      |                                |   columns These are all some   |                                |
|      |                                | really long columns These are  |                                |
|      |                                |  all some really long columns  |                                |
+------+--------------------------------+--------------------------------+--------------------------------+
| row3 | These are all some really long | These are all some really long | These are all some really long |
|      |            columns             |   columns These are all some   |            columns             |
|      |                                | really long columns These are  |                                |
|      |                                |  all some really long columns  |                                |
|      |                                | These are all some really long |                                |
|      |                                |   columns These are all some   |                                |
|      |                                | really long columns These are  |                                |
|      |                                |  all some really long columns  |                                |
+------+--------------------------------+--------------------------------+--------------------------------+
```

**HTML Table**
```
<table>
  <thead>
  <tr>
    <th></th>
    <th>col1</th>
    <th>col2</th>
    <th>col3</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td>row1</td>
    <td>These are all some really long columns</td>
    <td>These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns</td>
    <td>These are all some really long columns</td>
  </tr>
  <tr>
    <td>row2</td>
    <td>These are all some really long columns</td>
    <td>These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns</td>
    <td>These are all some really long columns</td>
  </tr>
  <tr>
    <td>row3</td>
    <td>These are all some really long columns</td>
    <td>These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns These are all some really long columns</td>
    <td>These are all some really long columns</td>
  </tr>
  </tbody>
</table>
```
