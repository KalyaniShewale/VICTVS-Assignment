package com.example.examlistapplication.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.examlistapplication.R
import com.example.examlistapplication.module.Exam
import com.example.examlistapplication.view.MapViewWithPin

@Composable
fun ShowMapViewDialog(showDialog: MutableState<Boolean>, exam: Exam, list: List<String>) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            text = {
                MapViewWithPin(exam,list)

            },
            confirmButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text(stringResource(R.string.txt_close))
                }
            },
            modifier = Modifier
                .fillMaxSize(0.95f)
            // Adjust the size as needed
        )
    }
}
