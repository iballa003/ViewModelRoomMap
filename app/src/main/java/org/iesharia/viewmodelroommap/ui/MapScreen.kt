package org.iesharia.viewmodelroommap.ui

import android.graphics.drawable.Drawable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
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
import org.iesharia.viewmodelroommap.R
import org.iesharia.viewmodelroommap.data.AppDatabase
import org.iesharia.viewmodelroommap.data.MarkerType
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
fun MyMapView(modifier: Modifier = Modifier, database: AppDatabase, markerViewModel: MarkerViewModel) {
    // Obtener el LifecycleOwner dentro del Composable
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    // Estado para almacenar los marcadores observados
    var markers by remember { mutableStateOf(emptyList<org.iesharia.viewmodelroommap.data.Marker>()) }

    markerViewModel.allMarkers.observe(lifecycleOwner) { markerList ->
        markers = markerList
    }
    val initialMarkerTypes = listOf(
        MarkerType(typeName = "Hotel"),
        MarkerType(typeName = "Restaurante"),
        MarkerType(typeName = "Supermercado"),
        MarkerType(typeName = "Tienda")
    )
    val initialMarkers = listOf(
        org.iesharia.viewmodelroommap.data.Marker(
            title = "La Lupe Cantina",
            latitude = 28.958842410104822,
            longitude = -13.552473697796856,
            typeId = 2
        ),
        org.iesharia.viewmodelroommap.data.Marker(
            title = "Apartamentos Islamar",
            latitude = 28.95809131371148,
            longitude = -13.55317511918529,
            typeId = 1
        ),
        org.iesharia.viewmodelroommap.data.Marker(
            title = "SuperDino Atlántida",
            latitude = 28.961011277027847,
            longitude = -13.549155830467473,
            typeId = 3
        ),
        org.iesharia.viewmodelroommap.data.Marker(
            title = "Tienda Movistar",
            latitude = 28.96098888371057,
            longitude = -13.556464833212232,
            typeId = 4
        ),
        org.iesharia.viewmodelroommap.data.Marker(
            title = "Hamburguesería Cuco Triana",
            latitude = 28.960912549603332,
            longitude = -13.554697194527124,
            typeId = 2
        ),
    )
//        LaunchedEffect(Unit) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val tipoTarea = MarkerType(0,"Centro comercial")
//            database.markDao().insertMarkerType(tipoTarea)
//            Log.i("DAM2", "Insertado")
//        }catch (e: Exception){
//            Log.i("DAM2", e.toString())
//        }
//    }
//    }
//    LaunchedEffect(Unit) {
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val marker = org.iesharia.viewmodelroommap.data.Marker(id = 0, title = "Hostal San Ginés",
//                latitude = 28.96281644139363, longitude = -13.549590967037258, typeId = 1
//            )
//            database.markDao().insertMarker(marker)
//            Log.i("DAM2", "Insertado")
//        }catch (e: Exception){
//            Log.i("DAM2", e.toString())
//        }
//    }
//    }
    var marks = database.markDao().getAllMarkers()
    Log.i("DAM2", marks.toString())
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
        // Añadir marcadores observados al mapa
        markers.forEach { marker ->
            val customIcon: Drawable? by remember {
                mutableStateOf(context.getDrawable(R.drawable.restaurante))
            }
            Marker(
                state = rememberMarkerState(
                    geoPoint = GeoPoint(marker.latitude, marker.longitude)
                ),
                title = marker.title,
                snippet = "Haga clic aquí",
                icon = customIcon
            ) {
                Column(
                    modifier = Modifier
                        .size(100.dp)
                        .background(color = Color.Gray, shape = RoundedCornerShape(4.dp))
                ) {
                    Text(text = it.title)
                    Text(text = it.snippet, fontSize = 10.sp)
                }
            }
        }

    }
}