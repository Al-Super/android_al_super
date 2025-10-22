package com.centroi.alsuper.feature.fakeapp.screens

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Devices.PIXEL_7_PRO
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.centroi.alsuper.core.ui.Black
import com.centroi.alsuper.core.ui.Dimens
import com.centroi.alsuper.core.ui.FontWeight
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.White
import com.centroi.alsuper.core.ui.Yellow
import com.centroi.alsuper.core.ui.components.button.MainButton

@Preview(
    showBackground = false,
    device = PIXEL_7_PRO,
    showSystemUi = true,
    backgroundColor = 0x121212
)
@Composable
fun FakeArticleScreen(
    navController: NavController = rememberNavController()
) {
    Dialog(
        onDismissRequest = { navController.popBackStack() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            FakeArticleScreenContent(navController)
        }

    }
}

@Composable
private fun FakeArticleScreenContent(
    navController: NavController
) {
    val dimens = LocalSpacing.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                state = rememberScrollState(),
            ),
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(top = dimens.space3x)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimens.space6x),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .height(dimens.space6x)
                        .clickable(onClick = { navController.popBackStack() })
                    ,
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(R.drawable.ic_left_arrow),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Black),
                )
                Row {
                    Image(
                        modifier = Modifier
                            .padding(end = dimens.space4x)
                            .height(dimens.space5x),
                        contentScale = ContentScale.FillHeight,
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Black)
                    )
                    Image(
                        modifier = Modifier.height(dimens.space5x),
                        contentScale = ContentScale.FillHeight,
                        painter = painterResource(R.drawable.ic_heart_icon),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Black)
                    )
                }
            }
            Column(
                modifier = Modifier.padding(top = dimens.space7x)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimens.space2x,
                            horizontal = dimens.space2x
                        ),
                    contentScale = ContentScale.FillWidth,
                    painter = painterResource(R.drawable.ig_product_sell),
                    contentDescription = "",
                )

                Text(
                    modifier = Modifier.padding(
                        vertical = dimens.space2x,
                        horizontal = dimens.space3x
                    ),
                    text = "Coquimbo Sewing Kit Gifts for Mom Grandma Women Men Adults Kids Teens Beginner Kids Traveler, Portable Sewing Supplies Accessories Contains Thread, Needles, Scissors, Dorm Room Essentials (Black, M)",
                    fontSize = 16.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.W500
                )
                Text(
                    modifier = Modifier.padding(
                        bottom = dimens.space3x,
                        start = dimens.space3x,
                        end = dimens.space3x
                    ),
                    text = "$8.99",
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.W500
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimens.space3x,
                            horizontal = dimens.space3x
                        )
                        .clip(RoundedCornerShape(dimens.space6x))
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(
                            vertical = dimens.space3x,
                        )
                        .clickable(onClick = { })
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_basket),
                        contentDescription = "Icono de carrito",
                        colorFilter = ColorFilter.tint(color = androidx.compose.ui.graphics.Color.Black)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Añadir al carrito",
                        fontWeight = androidx.compose.ui.text.font.FontWeight.W500
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(
                    vertical = dimens.space2x,
                    horizontal = dimens.space3x
                )
                .clip(RoundedCornerShape(dimens.space5x))
                .background(White)
                .padding(
                    vertical = dimens.space2x,
                    horizontal = dimens.space3x
                )

        ) {
            Text(
                text = "Detalles del producto",
                fontWeight = androidx.compose.ui.text.font.FontWeight.W400,
                fontSize = 20.sp
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = dimens.space2x)
                    .fillMaxWidth()
            )
            Text(
                text = "Destacado",
                fontWeight = androidx.compose.ui.text.font.FontWeight.W400,
                fontSize = 18.sp
            )
            Details("Material", "Plastico")
            Details("Marca", "Stanley")
            Details("Información del empaque", "Bolsa")
            Details("Incluye por unidad", "1 Vaso, 1 Popote, 1 Tapa")
            Details("Contiene", "1 Unidad")
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = dimens.space2x)
                    .fillMaxWidth()
            )
        }

    }
}

@Composable
private fun Details(title: String, description: String) {
    val dimens = LocalSpacing.current
    Row(
        modifier = Modifier.padding(vertical = dimens.space2x)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            fontWeight = androidx.compose.ui.text.font.FontWeight.W500
        )
        Text(
            modifier = Modifier.weight(1f),
            text = description
        )
    }
}