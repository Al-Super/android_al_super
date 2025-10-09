package com.centroi.alsuper.core.ui.components.editText

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DateSelectionTextField(
    date: MutableState<String>
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Open Date Picker Dialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Update date with the selected value
            calendar.set(year, month, dayOfMonth)
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            date.value = format.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column {
        OutlinedTextField(
            value = date.value,
            onValueChange = {},
            label = { Text("Date") },
            placeholder = { Text("Select a date") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true, // Make the TextField read-only
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateSelectionTextField() {
    DateSelectionTextField(remember { mutableStateOf("") })
}
