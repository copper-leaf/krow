package com.copperleaf.krow.utils

interface BorderSet : BorderSetCorners, BorderSetEdges, BorderSetIntersections

open class BordersOf(
    cornersDelegate: BorderSetCorners,
    edgesDelegate: BorderSetEdges,
    intersectionsDelegate: BorderSetIntersections,
) : BorderSet,
    BorderSetCorners by cornersDelegate,
    BorderSetEdges by edgesDelegate,
    BorderSetIntersections by intersectionsDelegate

interface BorderSetCorners {
    /** Top-Left Corner */
    val topLeftCorner: String

    /** Top-Right Corner */
    val topRightCorner: String

    /** Bottom-Left Corner */
    val bottomLeftCorner: String

    /** Bottom-Corner Corner */
    val bottomRightCorner: String
}

interface BorderSetEdges : BorderSetHorizontalEdges, BorderSetVerticalEdges

interface BorderSetHorizontalEdges {
    /** Default horizontal border */
    val horizontalEdge: String

    /** Top Horizontal Line */
    val horizontalTopEdge: String get() = horizontalEdge

    /** Center Horizontal Line */
    val horizontalInsideEdge: String get() = horizontalEdge

    /** Bottom Horizontal Line */
    val horizontalBottomEdge: String get() = horizontalEdge
}

interface BorderSetVerticalEdges {
    /** Default vertical border */
    val verticalEdge: String

    /** Vertical Line on left edge */
    val verticalLeftEdge: String get() = verticalEdge

    /** Vertical Line on right edge */
    val verticalRightEdge: String get() = verticalEdge

    /** Vertical Line in center */
    val verticalInsideEdge: String get() = verticalEdge
}

interface BorderSetIntersections {
    /** Top Intersection */
    val topIntersection: String

    /** Center Left-Side Intersection */
    val leftIntersection: String

    /** Center Intersection */
    val insideIntersection: String

    /** Center Right-Side Intersection */
    val rightIntersection: String

    /** Bottom Intersection */
    val bottomIntersection: String
}
