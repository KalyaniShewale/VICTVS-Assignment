package com.example.examlistapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examlistapplication.module.Exam
import com.example.examlistapplication.module.ExamApiClient
import com.example.examlistapplication.view.SortingOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
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
                Log.e("fetchItems Exception :-  ", e.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    //  initialize candidateExams list load from a data source.
    fun getExamsList(sortingOption: SortingOption): List<Exam> {
        return when (sortingOption) {
            SortingOption.DATE -> filterExamsByDate()
            SortingOption.LOCATION -> filterExamsByLocation()
            SortingOption.CANDIDATE -> filterExamsByCandidate()
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

     fun getListOfCandidateName(locationName:String): List<String>{
         // get all candidate of that location
        val listOfCandidateName = _exams.value
            .filter { it.locationname == locationName  }
            .map { it.candidatename }
        return listOfCandidateName
    }
}