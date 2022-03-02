> This library has been archived, switch to [JakeWharton/picnic](https://github.com/JakeWharton/picnic) for a replacement library.

# Krow

> A small DSL for generating tables in ASCII or HTML formats

![GitHub release (latest by date)](https://img.shields.io/github/v/release/copper-leaf/krow)
![Maven Central](https://img.shields.io/maven-central/v/io.github.copper-leaf/krow-core)
![Kotlin Version](https://img.shields.io/badge/Kotlin-1.4.32-orange)

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

# Supported Platforms/Features

| Platform |
| -------- |
| Android  |
| JVM      |
| iOS      |
| JS       |

# Installation

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

# Documentation

See the [website](https://copper-leaf.github.io/krow/) for detailed documentation and usage instructions.

# License

Krow is licensed under the BSD 3-Clause License, see [LICENSE.md](https://github.com/copper-leaf/krow/tree/master/LICENSE.md).

# References

- [Parsec](https://github.com/haskell/parsec)
- [JParsec](https://github.com/jparsec/jparsec)
