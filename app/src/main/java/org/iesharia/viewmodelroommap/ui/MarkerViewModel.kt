package org.iesharia.viewmodelroommap.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

class MarkerViewModelFactory(private val repository: MarkerRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarkerViewModel::class.java)) {
            return MarkerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}