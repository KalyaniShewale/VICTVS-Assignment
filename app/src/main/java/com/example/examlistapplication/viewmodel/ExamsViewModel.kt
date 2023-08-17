package com.example.examlistapplication.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examlistapplication.module.Exam
import com.example.examlistapplication.module.ExamApiClient
import com.example.examlistapplication.module.FilterOption
import com.example.examlistapplication.view.SortingOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ExamsViewModel : ViewModel() {

    private val _exams = MutableStateFlow<List<Exam>>(emptyList())
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading


    fun fetchExamsList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val examApi = ExamApiClient()
                val fetchedExams = examApi.getExams()
                _exams.value = fetchedExams
            } catch (e: Exception) {
                // Handle error
                _exams
              } finally {
                _isLoading.value = false
            }
        }
    }

    //  initialize candidateExams list load from a data source.
    @RequiresApi(Build.VERSION_CODES.O)
    fun getExamsList(sortingOption: SortingOption, filterOption: FilterOption): List<Exam> {
        return when (sortingOption) {
            SortingOption.DATE -> filterExamsByDate()
            SortingOption.LOCATION -> filterExamsByLocation()
            SortingOption.CANDIDATE -> filterExamsByCandidate()
            SortingOption.CANDIDATENAME -> getListExamSameCandidate(filterOption.candidateName)
            SortingOption.CANDIDATELOCATION -> getListExamSameLocation(filterOption.examLocation)
            SortingOption.TIMEBETWEENTWOEXAM ->getListExamDate(filterOption.examStartAndEndDate)
        }
    }

    private fun filterExamsByDate(): List<Exam> {
        // Filter candidateExams by date and update _candidateExams
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return _exams.value.sortedBy { item ->
            dateFormat.parse(item.examdate)
        }
    }

    private fun filterExamsByCandidate(): List<Exam> {
        // Filter candidateExams by candidate name and update _candidateExams
        return _exams.value.sortedBy { it.candidatename }
    }

    private fun filterExamsByLocation(): List<Exam> {
        // Filter candidateExams by location and update _candidateExams
        return _exams.value.sortedBy { it.locationname }
    }

    fun getListOfCandidateName(locationName: String): List<String> {
        // get all candidate of that location
        return _exams.value
            .filter { it.locationname == locationName }
            .map { it.candidatename }
    }

    fun getListExamSameCandidate(candidatename: String): List<Exam> {
        // get list of Exam for same Candidate name
        return _exams.value
            .filter { it.candidatename.equals(candidatename, ignoreCase = true)
            }
    }

    fun getListExamSameLocation(locationname: String): List<Exam> {
        // get list of Exam for same Candidate location
        return _exams.value
            .filter { it.locationname.equals(locationname, ignoreCase = true)  }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getListExamDate(examStartDate: String): List<Exam> {

        val dateStrings = examStartDate.split(" ")//12/09/2023

        //  for testing this function we are taking dummy data
        // because we have all old date data
        // to test that data we need old date so test this
        // so for that we are taking dummy data

       /* val startDate = dateStrings.getOrNull(0)?.replace(" ", "")
        val endDate =   dateStrings.getOrNull(1)?.replace(" ", "")   */

        val startDate = "2023/04/04"
        val endDate = "2023/06/06"
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

        Log.e("fetchItems startDate:",startDate.toString())
        Log.e("fetchItems endDate:",endDate.toString())


        val startCal = Calendar.getInstance()
        startCal.time = dateFormat.parse(startDate)

        val endCal = Calendar.getInstance()
        endCal.time = dateFormat.parse(endDate)

        val filteredExams = _exams.value.filter { exam ->
            val examCal = Calendar.getInstance()
            Log.e("fetchItems exam.examdate:",exam.examdate.toString())
            examCal.time =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(exam.examdate)

            // Compare the exam date with the start and end date
            examCal.timeInMillis in startCal.timeInMillis..endCal.timeInMillis

        }
            return filteredExams
    }
}