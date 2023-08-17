package com.example.examlistapplication.ui.view
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.examlistapplication.R
import com.example.examlistapplication.module.FilterOption
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(showDialog:  MutableState<Boolean>,filterOption: (FilterOption) -> Unit) {
    val state = rememberDateRangePickerState()
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var startDate = ""
    var endDate = " "
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),

            confirmButton = {

                Button(
                    onClick = {
                        showDialog.value = false
                        coroutineScope.launch {
                            bottomSheetState.hide()
                        }
                        filterOption(FilterOption(startDate.toString()+" "+endDate.toString()))
                    }
                ) {
                    Text(text = "Done")
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
              ModalBottomSheetLayout(
                    sheetState = bottomSheetState,

                    sheetContent = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(600.dp)
                                .background(Color.White)
                        ) {
                            DateRangePickerSample(state)

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White),
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 16.dp)
                            ) {
                                Text("Done", color = Color.White)
                            }
                        }
                    },
                    content = {
                        Column {
                            Button(onClick = {
                                coroutineScope.launch {

                                    bottomSheetState.show()
                                }
                            }, modifier = Modifier.padding(0.dp)) {
                                Text(text = stringResource(id = R.string.txt_open_picker))
                            }
                            if(state.selectedStartDateMillis!=null && state.selectedEndDateMillis!=null) {
                                startDate =
                                    state.selectedStartDateMillis?.let { getFormattedDate(it) }.toString()
                                 endDate =
                                     state.selectedEndDateMillis?.let { getFormattedDate(it) }.toString()
                                Text(text = stringResource(id = R.string.txt_start_date) + " : " +
                                        if (state.selectedStartDateMillis != null) startDate else "")
                                Text(text = stringResource(id = R.string.txt_endDate) + " : " +
                                        if (state.selectedEndDateMillis != null) endDate else "")
                                Text(text = stringResource(id = R.string.txt_Conform_msg))
                               var filterOption = FilterOption(startDate.toString()+" "+endDate.toString())
                                Log.e("fetchItems startDate.toString()+\" \"+endDate.toString()", startDate.toString()+" "+endDate.toString())
                            }
                        }
                    },
                    scrimColor = Color.Black.copy(alpha = 0.5f),
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )

            }
        )
    }
}
fun getFormattedDate(timeInMillis: Long): String{
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(calender.timeInMillis)
}

fun dateValidator(): (Long) -> Boolean {
    return {
            timeInMillis ->
        val endCalenderDate = Calendar.getInstance()
        endCalenderDate.timeInMillis = timeInMillis
        endCalenderDate.set(Calendar.DATE, Calendar.DATE + 20)
        timeInMillis > Calendar.getInstance().timeInMillis && timeInMillis < endCalenderDate.timeInMillis
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerSample(state: DateRangePickerState){
    DateRangePicker(state,
        modifier = Modifier,
        dateFormatter = DatePickerFormatter("yy MM dd", "yy MM dd", "yy MM dd"),
        dateValidator = dateValidator(),
        title = {
            Text(text = stringResource(id = R.string.msg_select_date_range), modifier = Modifier
                .padding(5.dp))
        },
        headline = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                Box(Modifier.weight(1f)) {
                    (if(state.selectedStartDateMillis!=null) state.selectedStartDateMillis?.
                    let { getFormattedDate(it) } else stringResource(id = R.string.txt_start_date))?.let { Text(text = it) }
                }
                Box(Modifier.weight(1f)) {
                    (if(state.selectedEndDateMillis!=null) state.selectedEndDateMillis?.
                    let { getFormattedDate(it) } else stringResource(id = R.string.txt_endDate) )?.let { Text(text = it) }
                }
                Box(Modifier.weight(0.2f)) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Okk")
                }
            }
        },
        showModeToggle = true,
        colors = DatePickerDefaults.colors(
            containerColor = Color.Blue,
            titleContentColor = Color.Black,
            headlineContentColor = Color.Black,
            weekdayContentColor = Color.Black,
            subheadContentColor = Color.Black,
            yearContentColor = Color.Green,
            currentYearContentColor = Color.Red,
            selectedYearContainerColor = Color.Red,
            disabledDayContentColor = Color.Gray,
            todayDateBorderColor = Color.Blue,
            dayInSelectionRangeContainerColor = Color.LightGray,
            dayInSelectionRangeContentColor = Color.White,
            selectedDayContainerColor = Color.Black
        )
    )
}