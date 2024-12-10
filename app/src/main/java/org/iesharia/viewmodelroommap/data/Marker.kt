package org.iesharia.viewmodelroommap.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    foreignKeys = [ForeignKey(
        entity = MarkerType::class,
        parentColumns = ["id"],
        childColumns = ["typeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Marker(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val latitude: Double,
    val longitude: Double,
    val typeId: Int
)
