package com.centroi.alsuper.feature.fakeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.White
import com.centroi.alsuper.core.ui.components.carousel.SectionArticlesCarousel


@Preview(
    showBackground = true,
    showSystemUi = true,
    device = PIXEL_7_PRO
)
@Composable
fun CartScreen() {
    val dimens = LocalSpacing.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .height(dimens.space18x)
                    .padding(horizontal = dimens.space4x)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_basket),
                contentDescription = "Logo",
                contentScale = ContentScale.FillHeight,
                colorFilter = ColorFilter.tint(color = Color.DarkGray)
            )
            Text("Oops, tu carrito esta vacio")
            Button(
                modifier = Modifier.padding(top = dimens.space2x),
                onClick = {}
            ) {
                Text("Seguir comprando")
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            SectionArticlesCarousel(
                modifier = Modifier
                    .background(White)
                    .padding(top = dimens.space3x)
                    .fillMaxWidth()
            )
        }
    }
}
