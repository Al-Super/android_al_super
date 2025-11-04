package com.centroi.alsuper.feature.fakeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.centroi.alsuper.core.data.models.fakearticle.CartItemData
import com.centroi.alsuper.core.ui.Black
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.MainYellow
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes
import com.centroi.alsuper.core.ui.White
import com.centroi.alsuper.core.ui.YellowBrown
import com.centroi.alsuper.core.ui.YellowLightGray
import com.centroi.alsuper.core.ui.components.button.MainButton
import com.centroi.alsuper.core.ui.components.carousel.SectionArticlesCarousel

@Composable
fun CartScreen(
    navController: NavController? = rememberNavController(),
    viewModel: CartScreenViewModelAbstract = hiltViewModel<CartScreenViewModel>()
) {
    val dimens = LocalSpacing.current
    val cartItems by viewModel.cartItems.collectAsState()

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
            if (cartItems.isEmpty()) {
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
                    onClick = {
                        navController?.navigate(Routes.FakeHomeScreen.name)
                    }
                ) {
                    Text("Seguir comprando")
                }
            } else {
                CartScreenItems(cartItems, viewModel)
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            SectionArticlesCarousel(
                modifier = Modifier
                    .background(White)
                    .padding(top = dimens.space3x)
                    .fillMaxWidth(),
                title = "Vistos recientemente",
                navController = navController ?: rememberNavController()
            )
        }
    }
}


@Composable
fun CartScreenItems(
    items: List<CartItemData>,
    viewModel: CartScreenViewModelAbstract,
) {
    val dimens = LocalSpacing.current
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(dimens.space3x)
        ) {
            items(items) { item ->
                var quantity by remember { mutableIntStateOf(item.quantity) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .padding(vertical = dimens.space2x)
                            .height(dimens.space9x)
                        ,
                        contentScale = ContentScale.FillHeight,
                        painter = painterResource(R.drawable.ig_product_sell),
                        contentDescription = "",
                    )
                    Column {
                        Text(
                            modifier = Modifier.padding(
                                vertical = dimens.space2x,
                                horizontal = dimens.space3x
                            ),
                            text = item.name,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.W500
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(2.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                modifier = Modifier
                                    .padding(end = dimens.space2x),
                                onClick = {viewModel.deleteProduct(item.productId)},
                                colors = ButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = Color.Black,
                                    disabledContentColor = Color.White,
                                    disabledContainerColor = YellowLightGray,
                                ),
                            ) {
                                Image(
                                    modifier = Modifier
                                        .height(dimens.space4x)
                                    ,
                                    contentScale = ContentScale.FillHeight,

                                    painter = painterResource(R.drawable.ic_delete),
                                    contentDescription = "Icono borrar",
                                    colorFilter = ColorFilter.tint(color = Black)
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            MainButton (
                                onClick = { if (quantity > 1) quantity-- },
                                enabled = quantity > 1,
                                text = "-"
                            )

                            Text(
                                text = quantity.toString(),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            MainButton(
                                onClick = { quantity++ },
                                text = "-"
                            )
                        }

                    }
                }
            }
        }
    }
}
