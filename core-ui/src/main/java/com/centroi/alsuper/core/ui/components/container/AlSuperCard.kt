package com.centroi.alsuper.core.ui.components.container

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.centroi.alsuper.core.ui.AlSuperTheme

@Composable
fun AlSuperCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit // Slot for custom content inside the card
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFakeCustomCard() {
    AlSuperTheme { // Use the theme to preview the card
        AlSuperCard(elevation = 4.dp) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                Text(text = "Title", style = MaterialTheme.typography.titleMedium)

                Text(text = "Subtitle", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRealCustomCard() {
    AlSuperTheme(isFakeApp = false) { // Use the theme to preview the card
        AlSuperCard(elevation = 4.dp) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                Text(text = "Title", style = MaterialTheme.typography.titleMedium)

                Text(text = "Subtitle", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}