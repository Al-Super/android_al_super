package com.centroi.alsuper.core.ui.components.container

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.centroi.alsuper.core.ui.AlSuperTheme

@Composable
fun AlSuperCircularContainer(
    initials: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = Color.White,
    size: Dp = 48.dp // Default size
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            text = initials.uppercase(),
            fontSize = (size.value / 2.5).sp, // Dynamically adjust text size
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCircularContainer() {
    AlSuperTheme {
        AlSuperCircularContainer(initials = "MC")
    }
}