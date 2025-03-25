package com.centroi.alsuper.core.ui.components.navigation.bottomNavigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.centroi.alsuper.core.ui.BottomNavItem
import com.centroi.alsuper.core.ui.fakeBottomNavigation
import com.centroi.alsuper.core.ui.mainBottomNavigation

@Composable
fun AlSuperBottomNavigationBar(
    navController: NavController,
    selectedRoute: String,
    onFakeApp: Boolean = true
) {
    val items = if(onFakeApp) fakeBottomNavigation else mainBottomNavigation
    val bottomBarTheme = getBottomBarTheme(onFakeApp)

    NavigationBar(
        containerColor = bottomBarTheme.containerColor
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = stringResource(item.labelResId)
                    )
                },
                label = { Text(stringResource(item.labelResId)) },
                selected = selectedRoute == item.route,
                onClick = { navController.navigate(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = bottomBarTheme.selectedIconColor,
                    selectedTextColor = bottomBarTheme.selectedTextColor,
                    unselectedIconColor = bottomBarTheme.unselectedIconColor,
                    unselectedTextColor = bottomBarTheme.unselectedTextColor,
                    indicatorColor = bottomBarTheme.indicatorColor
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    val navController = rememberNavController()
    val selectedRoute = BottomNavItem.FakeHome.route // Mock a selected tab for preview

    AlSuperBottomNavigationBar(navController = navController, selectedRoute = selectedRoute)
}