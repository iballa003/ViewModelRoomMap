package org.iesharia.viewmodelroommap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.iesharia.viewmodelroommap.data.AppDatabase
import org.iesharia.viewmodelroommap.ui.MyMapView
import org.iesharia.viewmodelroommap.ui.theme.ViewModelRoomMapTheme


class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = AppDatabase.getDatabase(this)
        enableEdgeToEdge()
        setContent {
            ViewModelRoomMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyMapView(
                        modifier = Modifier.padding(innerPadding),
                        database
                    )
                }
            }
        }
    }
}

