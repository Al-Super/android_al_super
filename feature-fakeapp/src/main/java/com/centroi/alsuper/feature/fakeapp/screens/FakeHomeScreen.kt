package com.centroi.alsuper.feature.fakeapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.centroi.alsuper.core.ui.components.carousel.SectionArticlesCarousel


@Composable
fun FakeHomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
                state = rememberScrollState(),
            )
        ,
    ) {
        SectionArticlesCarousel(
            modifier = Modifier.fillMaxWidth(),
            title = "Mas vendidos",
            navController = navController
        )
        SectionArticlesCarousel(
            modifier = Modifier.fillMaxWidth(),
            title = "Arte y manualidades",
            navController = navController
        )
        SectionArticlesCarousel(
            modifier = Modifier.fillMaxWidth(),
            title = "Belleza y cuidado personal",
            navController = navController
        )
    }
}



