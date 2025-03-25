package com.centroi.alsuper.core.ui.components.editText

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.centroi.alsuper.core.ui.R

@Composable
fun EmailTextField() {
    var email by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = !isValidEmail(it) // Simple email validation
            },
            label = { Text(stringResource(R.string.login_email)) },
            placeholder = { Text(stringResource(R.string.login_email_placeholder)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email // Email-specific keyboard
            ),
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )

        if (isError) {
            Text(
                text = stringResource(R.string.login_email_invalid),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun PreviewEmailTextField() {
    EmailTextField()
}