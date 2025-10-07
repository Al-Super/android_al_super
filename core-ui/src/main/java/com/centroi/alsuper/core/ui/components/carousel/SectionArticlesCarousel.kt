package com.centroi.alsuper.core.ui.components.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.Routes

@Composable
fun SectionArticlesCarousel(
    modifier: Modifier = Modifier,
    title: String,
    navController: NavController,
) {
    val dimens = LocalSpacing.current
    Column (
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(
                start = dimens.space4x,
                bottom = dimens.space2x
            ),
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = dimens.space4x),
            contentPadding = PaddingValues(horizontal = dimens.space4x),
            horizontalArrangement = Arrangement.spacedBy(dimens.space2x)
        ) {
            items(count = 5) {
                Column(
                    modifier = Modifier
                        .width(dimens.space24x)
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(26.dp))
                        .background(Color.White)
                        .padding(dimens.space2x)
                        .clickable(onClick = {navController.navigate(Routes.FakeArticleScreen.name)})
                ) {
                    Image(
                        modifier = Modifier
                            .height(dimens.space18x)
                            .padding(horizontal = dimens.space4x)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.ig_product_sell),
                        contentDescription = "Logo",
                        contentScale = ContentScale.FillHeight
                    )
                    Text(
                        text = "Hydrojug traveler - 40 oz Water bottle with handle & Flip Straw",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$3.29",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

        }
    }
}
