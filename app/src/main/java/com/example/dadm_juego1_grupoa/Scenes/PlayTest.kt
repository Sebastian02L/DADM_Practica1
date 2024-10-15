package com.example.dadm_juego1_grupoa.Scenes

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dadm_juego1_grupoa.dataBase.AppDatabase
import com.example.dadm_juego1_grupoa.dataBase.Pregunta
import com.example.dadm_juego1_grupoa.dataBase.cargarDatosDesdeCSV
import java.util.concurrent.Executors

@Composable
fun BodyContentPlaying(navController: NavController) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val executor = Executors.newSingleThreadExecutor()
    var listaPreguntas by remember { mutableStateOf<List<Pregunta>>(emptyList()) }

    DisposableEffect(Unit) {
        executor.execute {
            try {
                Log.d("BodyContentPlaying", "Cargando datos desde el archivo CSV...")
                cargarDatosDesdeCSV(context, database)
                val todasLasPreguntas = database.preguntaDao().obtenerTodasLasPreguntas()
                listaPreguntas = todasLasPreguntas
                Log.d("BodyContentPlaying", "Datos cargados con éxito, número de preguntas: ${todasLasPreguntas.size}")
            } catch (e: Exception) {
                Log.e("BodyContentPlaying", "Error al cargar preguntas: ${e.message}", e)
                Toast.makeText(context, "Error al cargar las preguntas: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        // Limpiar el executor cuando el composable se descarte
        onDispose {
            executor.shutdown()
        }
    }

    LazyColumn {
        items(listaPreguntas) { pregunta ->
            Text(
                text = "Pregunta: ${pregunta.pregunta} - Tipo: ${pregunta.tipo} - Dificultad: ${pregunta.dificultad}",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
