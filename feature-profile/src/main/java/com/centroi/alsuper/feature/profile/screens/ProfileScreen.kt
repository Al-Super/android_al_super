package com.centroi.alsuper.feature.profile.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.centroi.alsuper.core.ui.AlSuperTheme
import com.centroi.alsuper.core.ui.Brown
import com.centroi.alsuper.core.ui.Dimens
import com.centroi.alsuper.core.ui.LocalFontWeight
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import com.centroi.alsuper.core.ui.YellowStrong
import com.centroi.alsuper.core.ui.components.container.AlSuperCard
import com.centroi.alsuper.core.ui.components.container.AlSuperCircularContainer

@Composable
fun ProfileScreen() {
    val spacing = LocalSpacing.current
    val fontWeight = LocalFontWeight.current
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(spacing.space3x)

    ) {
        ProfileDataContainer(spacing, fontWeight)
        ProfileActions(spacing, fontWeight)
    }
}

@Composable
private fun ProfileActions(
    spacing: Dimens,
    fontWeight: com.centroi.alsuper.core.ui.FontWeight
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
        R.string.profile_change_password
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
        R.string.profile_log_out
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
    text: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
    fontWeight: com.centroi.alsuper.core.ui.FontWeight
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(stringResource(id = R.string.profile_edit_button))
            }

            UserName(spacing)

            Spacer(modifier = Modifier.height(spacing.space3x))

            UserData(
                spacing,
                fontWeight
            )
        }
    }
}

@Composable
private fun UserData(
    spacing: Dimens,
    fontWeight: com.centroi.alsuper.core.ui.FontWeight
) {
    Text(
        text = stringResource(id = R.string.profile_birthdate),
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight(fontWeight.weight300x)
        )
    )
    Text(
        text = "12/03/1998",
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
        text = "mariagarcia@gmail.com",
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
        text = "+52 1 55 1234 5678",
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight(fontWeight.weight400x)
        )
    )
}

@Composable
private fun UserName(spacing: Dimens) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AlSuperCircularContainer(
            initials = "MG",
            backgroundColor = MaterialTheme.colorScheme.surfaceTint,
            size = spacing.imageMedium
        )
        Spacer(modifier = Modifier.height(spacing.space3x))
        Text(
            text = "María García",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    AlSuperTheme {
        ProfileScreen()
    }
}
