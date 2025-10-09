package com.centroi.alsuper.feature.profile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.centroi.alsuper.core.ui.AlSuperTheme
import com.centroi.alsuper.core.ui.Dimens
import com.centroi.alsuper.core.ui.LocalFontWeight
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.components.UiState
import com.centroi.alsuper.core.ui.components.container.AlSuperCard
import com.centroi.alsuper.core.ui.components.container.AlSuperCircularContainer

@Composable
fun ProfileScreen(
    loginCallback : MutableState<Boolean>,
    viewModel: ProfileScreenViewModelAbstract = hiltViewModel<ProfileScreenViewModel>()
) {
    val spacing = LocalSpacing.current
    val fontWeight = LocalFontWeight.current
    val profileData = remember { mutableStateOf(ProfileData()) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState){
        viewModel.loadProfile()
        when(val state = uiState){
            is UiState.Success -> {
                profileData.value = ProfileData(
                    name = state.data?.payload?.user?.name,
                    lastName = state.data?.payload?.user?.lastName,
                    email = state.data?.payload?.user?.email,
                    birthdate = state.data?.payload?.user?.birthdate,
                    phoneNumber = state.data?.payload?.user?.phoneNumber,
                )
            }
            is UiState.Error -> {
                // Handle error state if needed
            }
            else -> {}
        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(spacing.space3x)

    ) {
        ProfileDataContainer(spacing, fontWeight, profileData)
        ProfileActions(
            spacing = spacing,
            fontWeight = fontWeight,
            onClickChangePassword = {

            },
            onClickCloseSession = {
                loginCallback.value = false
                viewModel.closeSession()
            }
        )
    }
}

@Composable
private fun ProfileActions(
    spacing: Dimens,
    fontWeight: com.centroi.alsuper.core.ui.FontWeight,
    onClickChangePassword: () -> Unit,
    onClickCloseSession: () -> Unit
) {
    Spacer(
        modifier = Modifier
            .height(spacing.space05x)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.outline)
            .padding(top = spacing.space3x)
    )

    ProfileActionOption(
        spacing,
        fontWeight,
        R.drawable.ic_key,
        R.string.profile_change_password,
        onClick = onClickChangePassword
    )

    Spacer(
        modifier = Modifier
            .height(spacing.space05x)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.outline)
            .padding(top = spacing.space3x)
    )

    ProfileActionOption(
        spacing,
        fontWeight,
        R.drawable.ic_logout,
        R.string.profile_log_out,
        onClick = onClickCloseSession
    )

    Spacer(
        modifier = Modifier
            .height(spacing.space05x)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.outline)
            .padding(top = spacing.space3x)
    )
}

@Composable
private fun ProfileActionOption(
    spacing: Dimens,
    fontWeight: com.centroi.alsuper.core.ui.FontWeight,
    icon: Int,
    text: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                top = spacing.space3x,
                bottom = spacing.space3x
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing.space3x)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = stringResource(id = text),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight(fontWeight.weight400x),
                color = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@Composable
private fun ProfileDataContainer(
    spacing: Dimens,
    fontWeight: com.centroi.alsuper.core.ui.FontWeight,
    profileData: MutableState<ProfileData>
) {
    AlSuperCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.space3x)

    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onTertiaryContainer)
                .padding(spacing.space3x)
        ) {
            UserName(spacing, profileData.value.name.orEmpty(), profileData.value.lastName.orEmpty())

            Spacer(modifier = Modifier.height(spacing.space3x))

            UserData(
                spacing,
                fontWeight,
                profileData.value.birthdate.orEmpty(),
                profileData.value.email.orEmpty(),
                profileData.value.phoneNumber.orEmpty(),
            )
        }
    }
}

@Composable
private fun UserData(
    spacing: Dimens,
    fontWeight: com.centroi.alsuper.core.ui.FontWeight,
    birthdate: String,
    email: String,
    phoneNumber: String,
) {
    Text(
        text = stringResource(id = R.string.profile_birthdate),
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight(fontWeight.weight300x)
        )
    )
    Text(
        text = birthdate,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight(fontWeight.weight400x)
        )
    )
    Spacer(modifier = Modifier.height(spacing.space3x))

    Text(
        text = stringResource(id = R.string.profile_email),
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight(fontWeight.weight300x)
        )
    )
    Text(
        text = email,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight(fontWeight.weight400x)
        )
    )
    Spacer(modifier = Modifier.height(spacing.space3x))

    Text(
        text = stringResource(id = R.string.profile_phone),
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight(fontWeight.weight300x)
        )
    )
    Text(
        text = "${phoneNumber}",
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight(fontWeight.weight400x)
        )
    )
}

@Composable
private fun UserName(
    spacing: Dimens,
    name: String,
    lastName: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlSuperCircularContainer(
            initials = (name.firstOrNull()?.toString() ?: "") + (lastName.firstOrNull()?.toString() ?: ""),
            backgroundColor = MaterialTheme.colorScheme.surfaceTint,
            size = spacing.imageMedium
        )
        Spacer(modifier = Modifier.height(spacing.space3x))
        Text(
            text = name + " " + lastName,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    AlSuperTheme {
        ProfileScreen(remember { mutableStateOf(false) })
    }
}
