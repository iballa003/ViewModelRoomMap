package org.iesharia.viewmodelroommap.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
interface MarkerDao {
    @Query("SELECT * FROM Marker")
    fun getAllMarkers(): LiveData<List<Marker>>

    @Query("SELECT * FROM MarkerType")
    fun getAllMarkersType(): List<MarkerType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkers(markers: List<Marker>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkerTypes(markerTypes: List<MarkerType>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkerType(markerType: MarkerType) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(marker: Marker) : Long
}
