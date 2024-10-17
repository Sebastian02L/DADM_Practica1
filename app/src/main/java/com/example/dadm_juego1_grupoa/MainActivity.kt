package com.example.dadm_juego1_grupoa

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dadm_juego1_grupoa.Scenes.BodyContent
import com.example.dadm_juego1_grupoa.Scenes.Navigation
import com.example.dadm_juego1_grupoa.Scenes.Screen
import com.example.dadm_juego1_grupoa.dataBase.AppDatabase
import com.example.dadm_juego1_grupoa.dataBase.Pregunta
import com.example.dadm_juego1_grupoa.dataBase.cargarDatosDesdeCSV
import com.example.dadm_juego1_grupoa.ui.theme.DADM_juego1_GrupoATheme
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DADM_juego1_GrupoATheme {
                cargarBaseDatos()
                Navigation()
            }
        }
    }


    @Composable
    fun cargarBaseDatos() {
        val context = LocalContext.current
        val database = AppDatabase.getDatabase(context)
        val executor = Executors.newSingleThreadExecutor()

        DisposableEffect(Unit) {
            if (isFirstLaunch(context)) {
                executor.execute {
                    try {
                        Log.d("BodyContentPlaying", "Cargando datos desde el archivo CSV...")
                        cargarDatosDesdeCSV(context, database)
                        Log.d("BodyContentPlaying", "Datos cargados con éxito")

                        // Actualiza el estado de la primera ejecución
                        setFirstLaunchFlag(context)

                    } catch (e: Exception) {
                        Log.e("BodyContentPlaying", "Error al cargar preguntas: ${e.message}", e)
                        Toast.makeText(context, "Error al cargar las preguntas: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            // Limpiar el executor cuando el composable se descarte
            onDispose {
                executor.shutdown()
            }
        }
    }

    fun isFirstLaunch(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirstLaunch", true)
    }

    fun setFirstLaunchFlag(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isFirstLaunch", false)
            apply()
        }
    }

}


/*
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.MainMenu.route){
        composable(route = Screen.MainMenu.route){
            BodyContent(navController)
        }
    }
}*/
