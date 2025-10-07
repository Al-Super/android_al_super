package com.centroi.alsuper.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.YellowLight
import com.centroi.alsuper.core.ui.components.button.TransparentWhiteButton
import kotlin.math.roundToInt

@Composable
fun PanicScreen(
    shouldClose: MutableState<Boolean>
) {
    val dimens = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row (
            modifier = Modifier
                .padding(dimens.space4x)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Confirmación de SOS",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            TransparentWhiteButton(
                text = "Close",
                icon = painterResource(R.drawable.ic_close)
            ) {
                shouldClose.value = true
            }
        }
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.Red, Color.Transparent),
                        center = androidx.compose.ui.geometry.Offset.Unspecified,
                        radius = 700f
                    )
                )
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = dimens.space12x),
                text = "Cuando te sientas en peligro, desliza el botón de abajo para compartir tu ubicacion con tu lista de contactos.",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            SlideToUnlockButton { shouldClose.value = false }
        }
    }
}

@Composable
fun SlideToUnlockButton(
    modifier: Modifier = Modifier,
    width: Dp = 300.dp,
    height: Dp = 80.dp,
    onSlideComplete: () -> Unit = {}
) {
    val maxOffsetPx = with(LocalDensity.current) { (width - height).toPx() }
    var offsetX by remember { mutableStateOf(0f) }
    var isComplete by remember { mutableStateOf(false) }
    val dimens = LocalSpacing.current
    Box(
        modifier = modifier
            .padding(dimens.space6x)
            .width(width)
            .height(height)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(YellowLight, Color.Red),
                    startX = 1f,
                ),
                shape = RoundedCornerShape(28.dp),
                alpha = 0.5f
            )
    ) {
        Text(
            text = if (isComplete) "Unlocked" else "Desliza para mandar alerta",
            modifier = Modifier
                .padding(end = dimens.space2x)
                .fillMaxWidth()
                .align(Alignment.Center),
            color = Color.Black,
            textAlign = TextAlign.End
        )
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .size(height)
                .padding(2.dp)
                .background(Color.Red, RoundedCornerShape(28.dp))
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        offsetX = (offsetX + delta).coerceIn(0f, maxOffsetPx)
                    },
                    onDragStopped = {
                        if (offsetX > maxOffsetPx * 0.9f) {
                            offsetX = maxOffsetPx
                            isComplete = true
                            onSlideComplete()
                        } else {
                            offsetX = 0f
                        }
                    }
                )
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "SOS",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}