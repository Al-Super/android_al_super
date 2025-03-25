package com.centroi.alsuper.core.ui

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val space0x : Dp = 0.dp,
    val space05x : Dp = 1.dp,
    val space1x : Dp = 4.dp,
    val space2x : Dp = 8.dp,
    val space3x : Dp = 16.dp,
    val space4x : Dp = 20.dp,
    val space5x : Dp = 24.dp,
    val space6x : Dp = 32.dp,
    val space7x : Dp = 40.dp,
    val space9x : Dp = 64.dp,

    val imageMedium : Dp = 100.dp,

    val radius50 : Int = 50,

    val half : Float = 0.5f

)

val LocalSpacing = compositionLocalOf { Dimens() }