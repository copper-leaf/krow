package com.copperleaf.krow.builder

import com.copperleaf.krow.model.HorizontalAlignment
import com.copperleaf.krow.model.VerticalAlignment

@TableMarker
class KrowTableBuilderCell {
    var wrapTextAt: Int? = null
    var content: String = ""
    var verticalAlignment: VerticalAlignment = VerticalAlignment.TOP
    var horizontalAlignment: HorizontalAlignment = HorizontalAlignment.LEFT

    var padding: Int = 1
}
