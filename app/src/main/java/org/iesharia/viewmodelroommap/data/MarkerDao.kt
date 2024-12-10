package org.iesharia.viewmodelroommap.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MarkerDao {
    @Query("SELECT * FROM Marker")
    fun getAllMarkers(): LiveData<List<Marker>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkers(markers: List<Marker>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkerTypes(markerTypes: List<MarkerType>)
}
