package com.centroi.alsuper.feature.fakeapp.screens

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import com.centroi.alsuper.core.ui.R

@Preview(
    showBackground = true,
    device = PIXEL_7_PRO,
    showSystemUi = true,
    backgroundColor = 0x121212
)
@Composable
fun FakeArticleScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Row {
                Image(
                    painter = painterResource(R.drawable.ic_left_arrow),
                    contentDescription = ""
                )
                Row {
                    Image(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = ""
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_left_arrow),
                        contentDescription = ""
                    )
                }
            }


        }

    }
}