package com.example.examlistapplication.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examlistapplication.module.FilterOption
import com.example.examlistapplication.viewmodel.ExamsViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListScreen(viewModel: ExamsViewModel = viewModel()) {

    //because of recomposition api call multiple time to handle this
    LaunchedEffect(key1 = viewModel) {
        viewModel.fetchExamsList()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val sortingOption = remember { mutableStateOf(SortingOption.DATE) }
        val filterOption = remember { mutableStateOf(FilterOption()) }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            TopBarWithFilterButton(sortingOption, filterOption)
            //get api call state
            val isLoading = viewModel.isLoading.collectAsState(false).value
            if (isLoading) {
                // Show loading indicator
                CenteredCircularProgressIndicator()
            } else {
                // Display the list of exams in date order
                ExamsListView(viewModel.getExamsList(sortingOption.value,filterOption.value),viewModel)
            }
        }
    }
}


enum class SortingOption {
    DATE, LOCATION, CANDIDATE ,CANDIDATENAME,CANDIDATELOCATION ,TIMEBETWEENTWOEXAM
}