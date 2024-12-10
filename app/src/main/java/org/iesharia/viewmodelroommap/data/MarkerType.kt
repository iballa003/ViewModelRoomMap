package org.iesharia.viewmodelroommap.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class MarkerType(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val typeName: String
)
