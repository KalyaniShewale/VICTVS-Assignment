package com.example.examlistapplication.module

import android.util.Log
import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ExamApiClient {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    suspend fun getExams(): List<Exam> {
        return try {
            httpClient.get(ApiRoutes.EXAM_URL) {
                contentType(ContentType.Application.Json)
            }
        } catch (e: Exception) {
            // Handle error, log, etc.
            Log.e("fetchItems getExams Exception :-  ", e.toString())
            emptyList()
        }
    }
}