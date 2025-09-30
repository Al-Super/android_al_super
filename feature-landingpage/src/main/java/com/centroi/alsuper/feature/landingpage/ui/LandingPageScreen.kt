/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.centroi.alsuper.feature.landingpage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import com.centroi.alsuper.feature.landingpage.ui.LandingPageUiState.Success
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.centroi.alsuper.core.data.models.LandingPageData
import com.centroi.alsuper.core.data.models.LandingPageName
import com.centroi.alsuper.core.ui.AlSuperTheme
import com.centroi.alsuper.core.ui.LocalSpacing

@Composable
fun LandingPageScreen(
    modifier: Modifier = Modifier,
    viewModel: LandingPageViewModel = hiltViewModel(),
    onChangeAppTheme: MutableState<Boolean>
) {
    AlSuperTheme(
        isFakeApp = false,
        isOnboarding = onChangeAppTheme.value
    ) {
        val items by viewModel.uiState.collectAsStateWithLifecycle()
        if (items is Success) {
            LandingPageScreen(
                items = (items as Success).data,
                modifier = modifier,
                onChangeAppTheme = onChangeAppTheme,
                viewModel = viewModel
            )
        }
    }
}

@Composable
internal fun LandingPageScreen(
    items: List<LandingPageData>,
    modifier: Modifier = Modifier,
    onChangeAppTheme: MutableState<Boolean>,
    viewModel: LandingPageViewModel
) {
    val goToNextPage = remember { mutableStateOf(false) }
    val showPagDotsAndButton = remember { mutableStateOf(true) }
    val pagerState = rememberPagerState(pageCount = { items.size })
    val paginationState = remember  { mutableIntStateOf(0) }
    val isButtonEnabled = remember  { mutableStateOf(true) }
    val dimens = LocalSpacing.current
    showPagDotsAndButton.value = when (items[pagerState.currentPage].pageID) {
        LandingPageName.LANDING_PAGE_FIFTH, LandingPageName.LANDING_PAGE_THIRD ->  false
        else -> true
    }
    LaunchedEffect(goToNextPage.value) {
        if (goToNextPage.value) {
            if (pagerState.currentPage + 1 < pagerState.pageCount) {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
            goToNextPage.value = false
        }
    }
    Column(modifier) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            userScrollEnabled = false
        ) { item ->
            LandingPageTemplate(
                modifier = Modifier,
                data = items[item],
                onChangeAppTheme = onChangeAppTheme,
                showPagDotsAndButton = showPagDotsAndButton,
                goToNextPage = goToNextPage,
                isButtonEnabled = isButtonEnabled
            )
        }
        if(showPagDotsAndButton.value){
            PaginationDots(
                totalDots = 4,
                selectedIndex = paginationState.intValue,
                modifier = Modifier
                    .padding(top = dimens.space2x, bottom = dimens.space1x)
                    .align(Alignment.CenterHorizontally)
            )
            Button(
                modifier = Modifier
                    .padding(dimens.space3x)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
                enabled = isButtonEnabled.value,
                onClick = {
                    if(items[pagerState.currentPage].pageID != LandingPageName.LANDING_PAGE_SECOND ||
                        items[pagerState.currentPage].pageID != LandingPageName.LANDING_PAGE_FOURTH){
                        paginationState.intValue++
                    }
                    if(items[pagerState.currentPage].pageID == LandingPageName.LANDING_PAGE_SIXTH){
                        viewModel.doNotShowOnboardingAgain()
                    } else {
                        goToNextPage.value = true
                    }
                }
            ) {
                Text(text ="Siguiente")
            }
        }
    }
}

@Composable
private fun PaginationDots(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.onTertiary,
    unselectedColor: Color = Color.White
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .padding(1.dp)
                    .background(
                        color = if (index > selectedIndex) unselectedColor else selectedColor,
                        shape = CircleShape
                    )
            )
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        }
    }
}