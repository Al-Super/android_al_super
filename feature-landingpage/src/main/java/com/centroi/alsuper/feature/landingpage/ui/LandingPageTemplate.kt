package com.centroi.alsuper.feature.landingpage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.centroi.alsuper.core.data.models.LandingPageData
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.components.button.TransparentWhiteButton
@Composable
fun LandingPageTemplate(
    modifier: Modifier = Modifier,
    data: LandingPageData? = null,
    onChangeAppTheme: MutableState<Boolean>,
    showPagDotsAndButton: MutableState<Boolean>,
    goToNextPage: MutableState<Boolean>,
    isButtonEnabled: MutableState<Boolean>
) {
    var isCheckedPrivacy by remember { mutableStateOf(false) }
    var isCheckedDataConsent by remember { mutableStateOf(false) }
    val dimens = LocalSpacing.current
    val logoResource = R.drawable.ic_dark_logo
    Box(
        modifier = modifier,
    ) {
        if (!onChangeAppTheme.value) {
            data?.buttonClose?.let {
                TransparentWhiteButton(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = dimens.space9x),
                    text = it,
                    icon = painterResource(id = R.drawable.ic_close),
                    onClick = {
                        onChangeAppTheme.value = false
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimens.space9x + dimens.space9x + dimens.space6x),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .zIndex(10f)
                    .fillMaxWidth()
                    .padding(horizontal = dimens.space4x)
                    .align(Alignment.CenterHorizontally)
                    .pointerInput(Unit) {
                        if (!showPagDotsAndButton.value) {
                            detectTapGestures(
                                onLongPress = {
                                    onChangeAppTheme.value = !onChangeAppTheme.value
                                    goToNextPage.value = true
                                }
                            )
                        }
                    },
                painter = painterResource(id = logoResource),
                contentDescription = "Logo",
                contentScale = ContentScale.FillWidth
            )

            data?.title?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 28.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                    modifier = Modifier.padding(
                        top = dimens.space6x,
                        start = dimens.space5x,
                        end = dimens.space5x,
                        bottom = dimens.space2x
                    )
                )
            }
            data?.description?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.W400,
                    modifier = Modifier.padding(
                        start = dimens.space2x,
                        end = dimens.space2x,
                        bottom = dimens.space2x
                    )
                )
            }
            data?.text?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.W400,
                    modifier = Modifier.padding(
                        start = dimens.space2x,
                        end = dimens.space2x,
                        bottom = dimens.space6x
                    )
                )
            }
            if (data?.consentNeeded == true) {
                isButtonEnabled.value = isCheckedPrivacy && isCheckedDataConsent
                Column {
                    data?.checkBoxTermsAndConditions?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isCheckedPrivacy,
                                onCheckedChange = { newCheckedState ->
                                    isCheckedPrivacy = newCheckedState
                                }
                            )
                            Text(text = it)
                        }
                    }
                    data?.checkBoxTermsAndConditions?.let {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isCheckedDataConsent,
                                onCheckedChange = { newCheckedState ->
                                    isCheckedDataConsent = newCheckedState
                                }
                            )
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
}