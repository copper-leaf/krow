package com.copperleaf.krow.utils

class SingleThickBorder :
    BorderSet,
    BorderSetCorners by SingleThickBorder.SquareCorners(),
    BorderSetEdges by SingleThickBorder.SolidEdges(),
    BorderSetIntersections by SingleThickBorder.Intersections() {
    class SquareCorners : BorderSetCorners {
        override val topLeftCorner: String = "┏"
        override val topRightCorner: String = "┓"
        override val bottomLeftCorner: String = "┗"
        override val bottomRightCorner: String = "┛"
    }
    class SolidEdges : BorderSetEdges {
        override val verticalEdge: String = "┃"
        override val horizontalEdge: String = "━"
    }
    class Dashed2Edges : BorderSetEdges {
        override val verticalEdge: String = "╏"
        override val horizontalEdge: String = "╍"
    }
    class Dashed3Edges : BorderSetEdges {
        override val verticalEdge: String = "┇"
        override val horizontalEdge: String = "┅"
    }
    class Dashed4Edges : BorderSetEdges {
        override val verticalEdge: String = "┋"
        override val horizontalEdge: String = "┉"
    }
    class Intersections : BorderSetIntersections {
        override val topIntersection: String = "┳"
        override val leftIntersection: String = "┣"
        override val insideIntersection: String = "╋"
        override val rightIntersection: String = "┫"
        override val bottomIntersection: String = "┻"
    }
}
