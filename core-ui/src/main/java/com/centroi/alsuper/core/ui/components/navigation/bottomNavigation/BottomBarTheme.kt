package com.centroi.alsuper.core.ui.components.navigation.bottomNavigation

import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import com.centroi.alsuper.core.ui.Brown
import com.centroi.alsuper.core.ui.MainYellow
import com.centroi.alsuper.core.ui.Purple
import com.centroi.alsuper.core.ui.YellowBlack
import com.centroi.alsuper.core.ui.YellowDarkGray
import com.centroi.alsuper.core.ui.YellowLight
import com.centroi.alsuper.core.ui.YellowLightGray
import com.centroi.alsuper.core.ui.YellowSurface

data class BottomBarTheme(
    val containerColor: Color,
    val selectedIconColor: Color,
    val selectedTextColor: Color,
    val unselectedIconColor: Color,
    val unselectedTextColor: Color,
    val indicatorColor: Color
)

@Composable
fun getBottomBarTheme(onFakeApp: Boolean): BottomBarTheme {
    return if (onFakeApp) {
        BottomBarTheme(
            containerColor = YellowSurface,
            selectedIconColor = YellowBlack,
            selectedTextColor = YellowBlack,
            unselectedIconColor = Purple,
            unselectedTextColor = Purple,
            indicatorColor = MainYellow
        )
    } else {
        BottomBarTheme(
            containerColor = YellowDarkGray,
            selectedIconColor = Brown,
            selectedTextColor = YellowLight,
            unselectedIconColor = YellowLightGray,
            unselectedTextColor = YellowLightGray,
            indicatorColor = YellowLight
        )
    }
}
