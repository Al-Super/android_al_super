package com.centroi.alsuper.core.ui.components.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.YellowBrown
import com.centroi.alsuper.core.ui.components.button.TransparentWhiteButton

@Composable
fun AlSuperTopNavigationBar(
    navController: NavController,
    onMainNavigation: (Boolean) -> Unit = {}
) {
    var isToggled by remember { mutableStateOf(true) }

    if(isToggled){
        FakeAppTopBar(navController) {
            isToggled = it
            onMainNavigation(it)
        }
    } else {
        RealAppTopBar(navController) {
            isToggled = it
            onMainNavigation(it)
        }
    }

}

@Composable
fun RealAppTopBar(
    navController: NavController,
    onMainNavigation: (Boolean) -> Unit
) {
    val dimens = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimens.space4x)
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(YellowBrown),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier,
            painter = painterResource(id = R.drawable.ic_dark_logo),
            contentDescription = "Logo"
        )

        TransparentWhiteButton(
            text = stringResource(id = R.string.top_bar_close_mode),
            icon = painterResource(id = R.drawable.ic_close),
            onClick = {
                onMainNavigation(true)
                navController.navigate(Routes.FakeHomeScreen.name)
            }
        )
    }
}

@Composable
private fun FakeAppTopBar(
    navController: NavController,
    onMainNavigation: (Boolean) -> Unit
) {
    val dimens = LocalSpacing.current
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = dimens.space4x)
            .windowInsetsPadding(WindowInsets.statusBars),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onMainNavigation(false) // Send it via callback
                            navController.navigate(Routes.InformationScreen.name)
                        }
                    )
                },
            painter = painterResource(id = R.drawable.ic_light_logo),
            contentDescription = "Logo"
        )
    }
}

@Preview
@Composable
fun AlSuperTopNavigationBarPreview() {
    AlSuperTopNavigationBar(rememberNavController())
}
