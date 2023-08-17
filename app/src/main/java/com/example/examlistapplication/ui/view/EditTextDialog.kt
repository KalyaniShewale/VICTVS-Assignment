package com.example.examlistapplication.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.examlistapplication.R


@Composable
fun CandidateNameDialog(showDialog:  MutableState<Boolean>,onNameEntered: (String) -> Unit) {
    var candidateName by remember { mutableStateOf(TextFieldValue()) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = stringResource(id = R.string.txt_enter_name))},
            modifier =  Modifier.fillMaxWidth()
                .padding(16.dp),

            confirmButton = {
                Button(
                    onClick = {
                        onNameEntered(candidateName.text)
                        showDialog.value = false
                    }
                ) {
                    Text(text = stringResource(id = R.string.txt_save))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text =stringResource(id = R.string.txt_cancel))
                }
            },
            text = {
                TextField(
                    value = candidateName,
                    onValueChange = { candidateName = it },
                    placeholder = { Text(text = "Candidate Name") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }

                )
            }
        )
    }
}
