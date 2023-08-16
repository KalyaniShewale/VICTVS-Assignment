package com.example.examlistapplication.view


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.examlistapplication.R
import com.example.examlistapplication.module.Exam
import com.example.examlistapplication.ui.view.ShowMapViewDialog
import com.example.examlistapplication.viewmodel.ExamsViewModel


@Composable
fun ExamsListView(items: List<Exam>, viewModel: ExamsViewModel) {
    //if list is empty then show error massage msg
        if (items.isEmpty()) {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.txt_error_msg),
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn {
                items(items) { item ->
                    ExamListItem(exam = item, viewModel)
                }
            }
        }
}

@Composable
fun ExamListItem(exam: Exam, viewModel: ExamsViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            GetText(stringResource(id = R.string.txt_tital), exam.title)
            GetText(stringResource(id = R.string.txt_description), exam.examdescription)
            GetText(stringResource(id = R.string.txt_date), exam.examdate)
            GetText(stringResource(id = R.string.txt_candidate_name), exam.candidatename)
            GetText(stringResource(id = R.string.txt_email), exam.candidateemail)
            GetTextOfLocation(exam, viewModel)
        }
    }
}

@Composable
fun GetText(string1: String, string2: String) {
    val endIndex = string1.length
    Text(
        text = buildAnnotatedString {
            append(string1)
            addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = endIndex)
            append(" ")
            append(string2)
        },
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun GetTextOfLocation(exam: Exam, viewModel: ExamsViewModel) {

    val endIndex = stringResource(id = R.string.txt_location).length
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = R.string.txt_location))
            addStyle(style = SpanStyle(fontWeight = FontWeight.Bold), start = 0, end = endIndex)
            append(" ")
            append(exam.locationname )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                showDialog.value = true
            },
    )
    if (showDialog.value) {
        val list =  viewModel.getListOfCandidateName(exam.locationname)
        ShowMapViewDialog(showDialog,exam,list)
    }
    Spacer(modifier = Modifier.height(4.dp))
}



