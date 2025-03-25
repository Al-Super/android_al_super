package com.centroi.alsuper.core.ui.components.editText

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    labelText: String = "Name",
    placeholderText: String = "Enter your name",
    onValueChange: (String) -> Unit = {},
    defaultValue: String = ""
) {
    var name by remember { mutableStateOf(defaultValue) }
    val updatedName by rememberUpdatedState(defaultValue) // Keeps track of changes

    LaunchedEffect(updatedName) {
        name = updatedName // Updates the field when defaultValue changes (e.g., editing mode)
    }

    Column {
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                onValueChange(it)
            },
            label = { Text(labelText) },
            placeholder = { Text(placeholderText) },
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words // Capitalizes each word
            ),
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNameTextField() {
    NameTextField()
}
