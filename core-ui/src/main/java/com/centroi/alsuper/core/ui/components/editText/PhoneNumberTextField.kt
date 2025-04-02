package com.centroi.alsuper.core.ui.components.editText

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.centroi.alsuper.core.ui.R

@Composable
fun PhoneNumberTextField(
    placeholder : String = stringResource(R.string.registration_phone_placeholder),
    defaultValue: String = "",
    onValueChange: (String) -> Unit = {}
) {
    var phoneNumber by remember { mutableStateOf("") }
    val updatedPhone by rememberUpdatedState(defaultValue) // Keeps track of changes

    LaunchedEffect(updatedPhone) {
        phoneNumber = updatedPhone // Updates the field when defaultValue changes (e.g., editing mode)
    }

    OutlinedTextField(
        value = phoneNumber,
        onValueChange = { newValue ->
            phoneNumber = newValue.take(PHONE_LENGHT) // Limit input to 10 digits
            onValueChange(phoneNumber)
        },
        label = { Text(stringResource(R.string.registration_phone)) },
        placeholder = { Text(placeholder) },
        prefix = {
            Text(
                text = "+52 ",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone // Show phone keyboard
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPhoneNumberTextField() {
    PhoneNumberTextField()
}
