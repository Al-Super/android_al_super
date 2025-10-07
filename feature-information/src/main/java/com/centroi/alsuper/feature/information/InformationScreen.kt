package com.centroi.alsuper.feature.information

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.centroi.alsuper.core.ui.Brown
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.components.cards.InformationSectionCard

@Composable
fun InformationScreen() {
    val dimens = LocalSpacing.current
    Column (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(dimens.space4x)
    ) {
        InformationSectionCard(
            title = "Recomendaciones",
            icon = com.centroi.alsuper.core.ui.R.drawable.ic_recommendation
        )
        InformationSectionCard(
            title = "Donde acudir",
            icon = com.centroi.alsuper.core.ui.R.drawable.ic_location,
        )
        InformationSectionCard(
            title = "Donde obtener mas informacion",
            icon = com.centroi.alsuper.core.ui.R.drawable.ic_question_mark
        )
        InformationSectionCard(
            title = "Lista de organizaciones",
            icon = com.centroi.alsuper.core.ui.R.drawable.ic_organization
        )
    }
}