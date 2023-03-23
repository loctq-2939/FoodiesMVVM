package com.codingtroops.foodies.utils

import androidx.annotation.FloatRange
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.Visibility

fun ConstrainScope.connectTo(
    start: ConstraintLayoutBaseScope.VerticalAnchor? = null,
    top: ConstraintLayoutBaseScope.HorizontalAnchor? = null,
    end: ConstraintLayoutBaseScope.VerticalAnchor? = null,
    bottom: ConstraintLayoutBaseScope.HorizontalAnchor? = null,
    startMargin: Dp = 0.dp,
    topMargin: Dp = 0.dp,
    endMargin: Dp = 0.dp,
    bottomMargin: Dp = 0.dp,
    startGoneMargin: Dp = 0.dp,
    topGoneMargin: Dp = 0.dp,
    endGoneMargin: Dp = 0.dp,
    bottomGoneMargin: Dp = 0.dp,
    @FloatRange(from = 0.0, to = 1.0) horizontalBias: Float = 0.5f,
    @FloatRange(from = 0.0, to = 1.0) verticalBias: Float = 0.5f
) {
    when {
        start != null && end != null && top != null && bottom != null -> {
            linkTo(
                start = start,
                top = top,
                end = end,
                bottom = bottom,
                startMargin = startMargin,
                topMargin = topMargin,
                endMargin = endMargin,
                bottomMargin = bottomMargin,
                startGoneMargin = startGoneMargin,
                topGoneMargin = topGoneMargin,
                endGoneMargin = endGoneMargin,
                bottomGoneMargin = bottomGoneMargin,
                horizontalBias = horizontalBias,
                verticalBias = verticalBias
            )
        }

        start != null && end != null -> {
            linkTo(
                start = start,
                end = end,
                startMargin = startMargin,
                endMargin = endMargin,
                startGoneMargin = startGoneMargin,
                endGoneMargin = endGoneMargin,
                bias = horizontalBias
            )
            top?.let { value ->
                this.top.linkTo(value, topMargin, topGoneMargin)
            }
            bottom?.let { value ->
                this.bottom.linkTo(value, bottomMargin, bottomGoneMargin)
            }
        }

        top != null && bottom != null -> {
            linkTo(
                top = top,
                bottom = bottom,
                topMargin = topMargin,
                bottomMargin = bottomMargin,
                topGoneMargin = topGoneMargin,
                bottomGoneMargin = bottomGoneMargin,
                bias = verticalBias
            )
            start?.let { value ->
                this.start.linkTo(value, startMargin, startGoneMargin)
            }
            end?.let { value ->
                this.end.linkTo(value, endMargin, endGoneMargin)
            }
        }

        else -> {
            start?.let { value ->
                this.start.linkTo(value, startMargin, startGoneMargin)
            }
            top?.let { value ->
                this.top.linkTo(value, topMargin, topGoneMargin)
            }
            end?.let { value ->
                this.end.linkTo(value, endMargin, endGoneMargin)
            }
            bottom?.let { value ->
                this.bottom.linkTo(value, bottomMargin, bottomGoneMargin)
            }
        }
    }
}

inline var ConstrainScope.isVisible: Boolean
    get() = visibility == Visibility.Visible
    set(value) {
        visibility = if (value) Visibility.Visible else Visibility.Gone
    }

inline var ConstrainScope.isInvisible: Boolean
    get() = visibility == Visibility.Invisible
    set(value) {
        visibility = if (value) Visibility.Invisible else Visibility.Gone
    }

inline var ConstrainScope.isGone: Boolean
    get() = visibility == Visibility.Gone
    set(value) {
        visibility = if (value) Visibility.Gone else Visibility.Visible
    }