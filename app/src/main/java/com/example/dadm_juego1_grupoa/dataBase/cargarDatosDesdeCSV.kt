package com.example.dadm_juego1_grupoa.dataBase

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

fun cargarDatosDesdeCSV(context: Context, database: AppDatabase) {
    val preguntas = mutableListOf<Pregunta>()
    val inputStream = context.assets.open("preguntas.csv")
    val reader = BufferedReader(InputStreamReader(inputStream))
    reader.readLine() // Leer y descartar la primera línea si es el encabezado

    reader.forEachLine { line ->
        val tokens = line.split(",")
        if (tokens.size == 7) {
            val pregunta = Pregunta(
                pregunta = tokens[0],
                tipo = tokens[1],
                dificultad = tokens[2],
                respuestaC = tokens[3],
                respuestaI1 = tokens[4],
                respuestaI2 = tokens[5],
                respuestaI3 = tokens[6]
            )
            preguntas.add(pregunta)
        }
    }
    database.preguntaDao().insertarPreguntas(preguntas)
}