---
components:
  - type: pageContent
---

# Formatting Tables

Once a Table has been created like so:

```kotlin
val table = krow {
    ...
}
```

You can then print the Table in any format you wish. Krow ships with Formatters that render the table as an HTML table, 
or as an ASCII table for the command-line. Examples are shown below:

**SingleBorder AsciiTable**
```
┌──────┬──────┬──────┬──────┐
│      │ col1 │ col2 │ col3 │
├──────┼──────┼──────┼──────┤
│ row1 │ 1-1  │ 2-1  │ 3-1  │
├──────┼──────┼──────┼──────┤
│ row2 │ 1-2  │ 2-2  │ 3-2  │
└──────┴──────┴──────┴──────┘
```


**DoubleBorder AsciiTable**
```
╔══════╦══════╦══════╦══════╗
║      ║ col1 ║ col2 ║ col3 ║
╠══════╬══════╬══════╬══════╣
║ row1 ║ 1-1  ║ 2-1  ║ 3-1  ║
╠══════╬══════╬══════╬══════╣
║ row2 ║ 1-2  ║ 2-2  ║ 3-2  ║
╚══════╩══════╩══════╩══════╝
```

**Crossing AsciiTable**
```
+------+------+------+------+
|      | col1 | col2 | col3 |
+------+------+------+------+
| row1 | 1-1  | 2-1  | 3-1  |
+------+------+------+------+
| row2 | 1-2  | 2-2  | 3-2  |
+------+------+------+------+
```

**HtmlTable**
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
    <td>1-1</td>
    <td>2-1</td>
    <td>3-1</td>
  </tr>
  <tr>
    <td>row2</td>
    <td>1-2</td>
    <td>2-2</td>
    <td>3-2</td>
  </tr>
  </tbody>
</table>
```
