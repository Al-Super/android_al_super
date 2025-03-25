package com.centroi.alsuper.core.ui.components.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.YellowBrown
import com.centroi.alsuper.core.ui.components.button.TransparentWhiteButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AlSuperTopNavigationBar(
    onMainNavigation: (Boolean) -> Unit = {}
) {
    var isToggled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    if(isToggled){
        FakeAppTopBar(coroutineScope) {
            isToggled = it
            onMainNavigation(it)
        }
    } else {
        RealAppTopBar(coroutineScope) {
            isToggled = it
            onMainNavigation(it)
        }
    }

}

@Composable
fun RealAppTopBar(
    coroutineScope: CoroutineScope,
    onMainNavigation: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
            }
        )
    }
}

@Composable
private fun FakeAppTopBar(
    coroutineScope: CoroutineScope,
    onMainNavigation: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onMainNavigation(false) // Send it via callback
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
    AlSuperTopNavigationBar()
}