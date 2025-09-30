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
    val space10x: Dp = 80.dp,
    val space12x: Dp = 96.dp,
    val space14x: Dp = 112.dp,
    val space16x: Dp = 128.dp,
    val space18x: Dp = 144.dp,
    val space20x: Dp = 160.dp,
    val space24x: Dp = 192.dp,
    val space28x: Dp = 224.dp,
    val space32x: Dp = 256.dp,

    val imageMedium : Dp = 100.dp,

    val radius50 : Int = 50,

    val half : Float = 0.5f

)

data class FontWeight(
    val weight300x : Int = 300,
    val weight400x : Int = 400
)

val LocalSpacing = compositionLocalOf { Dimens() }
val LocalFontWeight = compositionLocalOf { FontWeight() }
