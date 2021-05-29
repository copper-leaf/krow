package com.copperleaf.krow.utils

class CrossingBorder :
    BorderSet,
    BorderSetCorners by CrossingBorder.Corners(),
    BorderSetEdges by CrossingBorder.Edges(),
    BorderSetIntersections by CrossingBorder.Intersections() {
    class Corners : BorderSetCorners {
        override val topLeftCorner: String = "+"
        override val topRightCorner: String = "+"
        override val bottomLeftCorner: String = "+"
        override val bottomRightCorner: String = "+"
    }
    class Edges : BorderSetEdges {
        override val verticalEdge: String = "|"
        override val horizontalEdge: String = "-"
    }
    class Intersections : BorderSetIntersections {
        override val topIntersection: String = "+"
        override val leftIntersection: String = "+"
        override val insideIntersection: String = "+"
        override val rightIntersection: String = "+"
        override val bottomIntersection: String = "+"
    }
}
