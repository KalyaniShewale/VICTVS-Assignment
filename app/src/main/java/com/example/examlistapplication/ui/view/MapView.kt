package com.example.examlistapplication.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.example.examlistapplication.module.Exam
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapViewWithPin(exam: Exam, list: List<String>) {

        AndroidView(factory = { context ->
            var marker: MarkerOptions
            MapView(context).apply {
                // Initialize map settings and markers
                onCreate(null)
                getMapAsync { googleMap ->
                    googleMap.uiSettings.isZoomGesturesEnabled = false
                    googleMap.uiSettings.isScrollGesturesEnabled = true
                    googleMap.uiSettings.isRotateGesturesEnabled = false
                    val location = LatLng(exam.latitude.toDouble(), exam.longitude.toDouble())
                     marker = MarkerOptions().position(location)
                        .title(exam.locationname)
                        .snippet(stringListText(list)
                            )
                    googleMap.addMarker(marker)
                    googleMap.setOnMarkerClickListener { marker ->
                        // Show a dialog or update a Composable with marker information
                        false // Return true to indicate you've handled the click event
                    }
                    googleMap.setOnCameraIdleListener {
                        // Update marker's position based on the new camera position
                        val newLatLng = googleMap.cameraPosition.target
                        marker.position(newLatLng)
                    }

                    // Set initial camera position and zoom level
                    val initialCameraPosition = CameraPosition.Builder()
                        .target(LatLng(exam.latitude.toDouble(), exam.longitude.toDouble()))
                        .zoom(14f)
                        .build()
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(initialCameraPosition))
                }
            }
        })
   }


fun stringListText(stringList: List<String>): String {
    val joinedText = stringList.joinToString(", ")
    return  joinedText
}