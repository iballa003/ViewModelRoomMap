package org.iesharia.viewmodelroommap.ui

import androidx.lifecycle.LiveData
import org.iesharia.viewmodelroommap.data.Marker
import org.iesharia.viewmodelroommap.data.MarkerDao
import org.iesharia.viewmodelroommap.data.MarkerType

class MarkerRepository(private val markerDao: MarkerDao) {
    val allMarkers: LiveData<List<Marker>> = markerDao.getAllMarkers()

    suspend fun insertData(markerTypes: List<MarkerType>, markers: List<Marker>) {
        markerDao.insertMarkerTypes(markerTypes)
        markerDao.insertMarkers(markers)
    }
}