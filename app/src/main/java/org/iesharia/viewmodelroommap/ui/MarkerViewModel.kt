package org.iesharia.viewmodelroommap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.iesharia.viewmodelroommap.data.Marker
import org.iesharia.viewmodelroommap.data.MarkerType

class MarkerViewModel(private val repository: MarkerRepository) : ViewModel() {

    val allMarkers: LiveData<List<Marker>> = repository.allMarkers
    fun insertData(markerTypes: List<MarkerType>, markers: List<Marker>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(markerTypes, markers)
        }
    }

}
