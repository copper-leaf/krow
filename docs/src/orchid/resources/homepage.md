---
---

# Krow

> A small DSL for generating tables in ASCII or HTML formats

![GitHub release (latest by date)](https://img.shields.io/github/v/release/copper-leaf/krow)
![Maven Central](https://img.shields.io/maven-central/v/io.github.copper-leaf/krow-core)
![Kotlin Version](https://img.shields.io/badge/Kotlin-1.4.32-orange)

## Overview

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

## Installation

```kotlin
repositories {
    mavenCentral()
}

// for plain JVM or Android projects
dependencies {
    implementation("io.github.copper-leaf:krow-core:{{site.version}}")
}

// for multiplatform projects
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.copper-leaf:krow-core:{{site.version}}")
            }
        }
    }
}
```

## Targets

Krow renders both ASCII and HTML as plain Strings, with no platform-specific formatting. Krow publishes artifacts for 
the following Kotlin targets.

| Platform |
| -------- |
| Android  |
| JVM      |
| iOS      |
| JS       |

## Usage

Krow is a DSL for the layout and rendering of HTML-like tables. It supports table cells that can span both rows and 
columns, as well as several border styles to tweak the presentation of ASCII tables.

## DSL

### Header Columns

To build a table with 3 columns, such as:

```
{% snippet 'table-header-columns-rendered' -%}
```

all of the following styles are equivalent:

{% snippets snippetTags=['dsl', 'header'] template="code-snippets" %}

### Body Rows

To build a table with 2 columns, such as:

```
{% snippet 'table-body-rows-rendered' -%}
```

all of the following styles are equivalent:

{% snippets snippetTags=['dsl', 'body'] template="code-snippets" %}

### Dynamic Layout

The above snippets all assume a statically-defined layout of columns and rows, but the core layout engine of Krow allows
you to build tables much more dynamically as well. Rather than manually defining columns and the names of rows, you can
instead just define the content of the table, and it will expand and adapt to the content.

The following are some examples of dynamically-laid out tables, and how they get rendered.

#### Rows

By omitting a row name for each body row, the table will generate the row name as the row index.

```kotlin
{% snippet 'table-dynamic-layout-rows-dsl' -%}
```

```
{% snippet 'table-dynamic-layout-rows-rendered' -%}
```

#### Columns

Body cells align themselves to columns based on the space available in the row. If a cell is placed at an index that was
not specified as a column, that column will be added at the end of the table, with the index of the column used as its
column name.

```kotlin
{% snippet 'table-dynamic-layout-columns1-dsl' -%}
```

```
{% snippet 'table-dynamic-layout-columns1-rendered' -%}
```

This can be expanded out such that manually specifying columns is entirely optional.

```kotlin
{% snippet 'table-dynamic-layout-columns2-dsl' -%}
```

```
{% snippet 'table-dynamic-layout-columns2-rendered' -%}
```

#### Cells

If a previous cell in the row has a row span, a cell will be placed in the next available column within that row, adding 
columns as necessary.

```kotlin
{% snippet 'table-dynamic-layout-cells-dsl' -%}
```

```
{% snippet 'table-dynamic-layout-cells-rendered' -%}
```

#### Building with Coordinates

Krow also includes a `cellAt` function which places and configures a cell at the specified row/column coordinates, 
rather than building it in-order with the normal row/column builders. As normal, the rows/columns will expand 
as-necessary to fully accept each cell as it is specified, with row and column spans.

```kotlin
{% snippet 'table-dynamic-layout-coordinates1-dsl' -%}
```

```
{% snippet 'table-dynamic-layout-coordinates1-rendered' -%}
```

If a cell already exists at these coordinates, it will allow you to customize the attributes of that cell, rather than
attempting to create a new one. This makes it also useful for building the table structure with the row/column builders,
then tweaking some of the content or styling of individual cells afterward.

```kotlin
{% snippet 'table-dynamic-layout-coordinates2-dsl' -%}
```

```
{% snippet 'table-dynamic-layout-coordinates2-rendered' -%}
```

Be careful when using this layout DSL that the cell configurations do not overlap, which will throw an exception. It is
also an error to attempt to change the rowSpan or colSpan after the cell has been created.

## Rendering

Krow tables can be rendered either as ASCII tables, using a custom layout/rendering algorithm, or as HTML text to be 
displayed in a browser. So far, all examples have been shown using the ASCII renderer.

### ASCII

Tables are rendered to ASCII with the `AsciiTableFormatter`. You can customize row widths and choose whether to display
the header row or leading column.

```kotlin
{% snippet 'table-ascii-table-formatter' -%}
```

```kotlin
{% snippet 'table-ascii-options-width-dsl' -%}
```

```
{% snippet 'table-ascii-options-width-rendered' -%}
```

```kotlin
{% snippet 'table-ascii-options-header-row-dsl' -%}
```

```
{% snippet 'table-ascii-options-header-row-rendered' -%}
```

```kotlin
{% snippet 'table-ascii-options-leading-column-dsl' -%}
```

```
{% snippet 'table-ascii-options-leading-column-rendered' -%}
```

ASCII tables can be customized with several border styles. Currently, you can only apply a single border style to the 
entire table, but in the future I plan on adding support for styling the header row or leading column differently, or 
even applying custom border styles to individual cells.

{% snippets snippetTags=['rendered', 'ascii'] template="monospace-snippets" %}

### HTML

Tables are rendered to ASCII with the `HtmlTableFormatter`.

```kotlin
{% snippet 'table-html-table-formatter' -%}
```

Example output:

```html
{% snippet 'table-html-rendered' %}
```

{{ snippet('table-html-rendered').content | raw }}

