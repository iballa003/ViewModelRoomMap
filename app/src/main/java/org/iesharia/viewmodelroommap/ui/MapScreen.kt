package org.iesharia.viewmodelroommap.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.iesharia.viewmodelroommap.data.AppDatabase
import org.iesharia.viewmodelroommap.data.Marker
import org.iesharia.viewmodelroommap.data.MarkerType
import org.iesharia.viewmodelroommap.ui.theme.ViewModelRoomMapTheme
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex

val GoogleSat: OnlineTileSourceBase = object : XYTileSource(
    "Google-Sat",
    0, 19, 256, ".png", arrayOf<String>(
        "https://mt0.google.com",
        "https://mt1.google.com",
        "https://mt2.google.com",
        "https://mt3.google.com",

        )
) {
    override fun getTileURLString(pTileIndex: Long): String {
        return baseUrl + "/vt/lyrs=s&x=" + MapTileIndex.getX(pTileIndex) + "&y=" + MapTileIndex.getY(
            pTileIndex
        ) + "&z=" + MapTileIndex.getZoom(pTileIndex)
    }
}
@Composable
fun MyMapView(modifier: Modifier = Modifier, database: AppDatabase) {
    MarkerViewModel.allMarkers.observe(this) { markers ->
        addMarkersToMap(markers)
    }

//    LaunchedEffect(Unit) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val tipoTarea = MarkerType(0,"Restaurante")
//            database.markDao().insertMarkerType(tipoTarea)
//            Log.i("DAM2", "Insertado")
//        }catch (e: Exception){
//            Log.i("DAM2", e.toString())
//        }
//    }
//    }
//    var marks = database.markDao().getAllMarkers()
//    Log.i("DAM2", marks.toString())
    // define camera state
    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(28.957473, -13.554514)
        zoom = 17.0 // optional, default is 5.0
    }

    // define properties with remember with default value
    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    // setup mapProperties in side effect
    SideEffect {
        mapProperties = mapProperties
            .copy(tileSources = GoogleSat)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.NEVER)
    }

    // define marker state
    val depokMarkerState = rememberMarkerState(
        geoPoint = GeoPoint(28.957473, -13.554514)
    )


    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties // add properties
    ){
        // add marker here
        Marker(
            state = depokMarkerState, // add marker state
            title = "Arrecife Gran Hotel",
            snippet = "click"
        ){
            // create info window node
            Column(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ) {
                // setup content of info window
                Text(text = it.title)
                Text(text = it.snippet, fontSize = 10.sp)
            }
        }
        Marker(
            state = rememberMarkerState(
                geoPoint = GeoPoint(28.958871, -13.553784)
            ), // add marker state
            title = "Carretera",
            snippet = "click",
        ){
            // create info window node
            Column(
                modifier = Modifier
                    .size(100.dp)
                    .background(color = Color.Gray, shape = RoundedCornerShape(4.dp))
            ) {
                // setup content of info window
                Text(text = it.title)
                Text(text = it.snippet, fontSize = 10.sp)
            }
        }
    }
}