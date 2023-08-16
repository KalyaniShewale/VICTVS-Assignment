package com.example.examlistapplication.module

import kotlinx.serialization.Serializable

@Serializable
data class Exam(
    val id: Int,
    val title: String,
    val examdescription: String,
    val examdate: String,
    val candidatename: String,
    val candidateemail: String,
    val locationname: String,
    val latitude: String,
    val longitude: String

    )

