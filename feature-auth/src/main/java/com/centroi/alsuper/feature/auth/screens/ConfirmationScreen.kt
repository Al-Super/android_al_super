package com.centroi.alsuper.feature.auth.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R



@Composable
fun ConfirmationScreen(
    navigateToRegister: () -> Unit = {},
    loginCallback: (Boolean) -> Unit = {}
) {
    val spacing = LocalSpacing.current
    var smsCode by remember { mutableStateOf("") }
    Box {
        Image(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = spacing.space10x)
            ,
            painter = painterResource(R.drawable.ic_light_logo),
            contentDescription = ""
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.space3x),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.confirmation_screen_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = spacing.space3x),
                textAlign = TextAlign.Center,
                fontSize = 24.sp

            )
            SmsCodeTextField(
                value = smsCode,
                onValueChange = { smsCode = it },
                codeLength = 6
            )
            Button(
                onClick = { loginCallback(true) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.space6x),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            ) {
                Text(
                    text = stringResource(id= R.string.confirmation_screen_confirmation)
                )
            }
            Text(
                text = stringResource(id = R.string.confirmation_screen_retry).uppercase(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = spacing.space6x)
                    .clickable(
                        onClick = { navigateToRegister() }
                    ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall

            )
        }
    }
}

@Composable
fun SmsCodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    codeLength: Int = 6
) {
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(value, selection = TextRange(value.length)),
        onValueChange = {
            if (it.text.length <= codeLength && it.text.all { char -> char.isDigit() }) {
                onValueChange(it.text)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(codeLength) { index ->
                    val char = when {
                        index < value.length -> value[index].toString()
                        else -> ""
                    }
                    val isFocused = value.length == index

                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(56.dp)
                            .border(
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.3f
                                    )
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = char,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                    if (index < codeLength - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    )
}

@Composable
@Preview (showBackground = true)
fun PreviewConfirmationScreen() {
    ConfirmationScreen()
}