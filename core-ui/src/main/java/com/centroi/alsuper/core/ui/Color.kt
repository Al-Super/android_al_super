/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("MagicNumber")
package com.centroi.alsuper.core.ui

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val MainYellow = Color(0xFFF0CF37)
val Yellow = Color(0xFFF1CF37)
val YellowSurface = Color(0xFFF7EC90)
val YellowLight = Color(0xFFFBF6CB)
val YellowGray = Color(0xFF535144)
val YellowLightGray = Color(0xFF858072)
val YellowDarkGray = Color(0xFF241F0F)
val YellowBlack = Color(0xFF1D1B20)
val YellowBrown = Color(0xFF2F2914)
val Purple = Color(0xFF49454F)
val Brown = Color(0xFF433D2A)
val BrownLight = Color(0xFF9C988D)
val PurpleGray = Color(0xFF79747E)
val Orange = Color(0xFFF69800)
val YellowPastel = Color(0xFFF2DE56)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
