package com.centroi.alsuper.core.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.centroi.alsuper.core.ui.R

@Composable
fun TransparentWhiteButton(
    modifier: Modifier = Modifier,
    text: String = "",
    icon: Painter? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.Transparent,
        ),
        border = BorderStroke(2.dp, Color.White)
    ) {
        Text(
            text = text
        )
        icon?.let {
            Icon(painter = it, contentDescription = stringResource(id= R.string.top_bar_close_mode))
        }
    }
}
