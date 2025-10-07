package com.centroi.alsuper.core.ui.components.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.centroi.alsuper.core.ui.Brown
import com.centroi.alsuper.core.ui.LocalSpacing

@Composable
fun InformationSectionCard(
    modifier: Modifier = Modifier,
    title: String,
    @DrawableRes icon: Int
) {
    val dimens = LocalSpacing.current
    Column (
        modifier = modifier
            .padding(
                vertical = dimens.space2x,
            )
            .clip(RoundedCornerShape(dimens.space4x))
            .clickable{}
            .background(Brown)
            .padding(dimens.space4x)

    ) {
        Image(
            modifier = Modifier
                .size(dimens.space7x)
                .padding(bottom = dimens.space3x),
            painter = painterResource(icon),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.White),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            Image(
                painter = painterResource(com.centroi.alsuper.core.ui.R.drawable.ic_arrow_right),
                contentDescription = ""
            )
        }
    }
}