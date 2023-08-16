package com.example.examlistapplication.view

import android.widget.Toast
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.examlistapplication.R

@Composable
fun TopBarWithFilterButton(sortingOption: MutableState<SortingOption>) {
    val contextForToast = LocalContext.current.applicationContext
    var dropDownMenuExpanded by remember {
        mutableStateOf(false)
    }
    TopAppBar(
        // give the title for Screen
        title = {
            Text(
                text = stringResource(id = R.string.txt_exam_list),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            )
        },
        // add button for filter option
        actions = {
            IconButton(onClick = {
                // show the drop down menu
                dropDownMenuExpanded = true
            }) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(id = R.string.txt_Options)
                )
            }
            // drop down menu
            DropdownMenu(
                expanded = dropDownMenuExpanded,
                onDismissRequest = {
                    dropDownMenuExpanded = false
                },
            ) {
                // this is a column scope items are added vertically
                DropdownMenuItem(onClick = {
                    sortingOption.value = SortingOption.DATE
                    Toast.makeText(contextForToast, R.string.txt_filter_by_date, Toast.LENGTH_SHORT)
                        .show()
                    dropDownMenuExpanded = false
                }) {
                    Text(stringResource(id = R.string.txt_filter_by_date))
                }
                DropdownMenuItem(onClick = {
                    sortingOption.value = SortingOption.CANDIDATE
                    Toast.makeText(
                        contextForToast,
                        R.string.txt_filter_by_candidate,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    dropDownMenuExpanded = false
                }) {
                    Text(stringResource(id = R.string.txt_filter_by_candidate))
                }
                DropdownMenuItem(onClick = {
                    sortingOption.value = SortingOption.LOCATION
                    Toast.makeText(
                        contextForToast,
                        R.string.txt_filter_by_location,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    dropDownMenuExpanded = false
                }) {
                    Text(stringResource(id = R.string.txt_filter_by_location))
                }
            }
        },
        backgroundColor = MaterialTheme.colorScheme.primary
    )
}
