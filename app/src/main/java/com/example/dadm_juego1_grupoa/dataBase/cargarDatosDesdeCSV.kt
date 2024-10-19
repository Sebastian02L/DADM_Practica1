package com.example.dadm_juego1_grupoa.dataBase

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

//Funcion para cargar datos desde un archivo CSV y agregarlos a la base de datos
//Recibe el contexto y la base de datos como parametros
fun cargarDatosDesdeCSV(context: Context, database: AppDatabase) {

    //Se crea una lista mutable para almacenar las preguntas que se leeran del archivo CSV
    val preguntas = mutableListOf<Pregunta>()
    //Abre el archivo "preguntas.csv" desde la carpeta de assets de la aplicación
    val inputStream = context.assets.open("preguntas.csv")
    //Crea un BufferedReader para leer el archivo linea por linea
    val reader = BufferedReader(InputStreamReader(inputStream))

    reader.readLine() // Leer y descartar la primera linea si es el encabezado

    //Itera sobre cada linea del archivo CSV
    reader.forEachLine { line ->

        //Divide la linea en tokens usando la coma como delimitador. Cada token representa un campo de la pregunta
        val tokens = line.split(",")
        //Verifica si la cantidad de tokens es correcta (en este caso 8 campos).
        if (tokens.size == 8) {

            //Crea una instancia de la entidad Pregunta con los datos obtenidos del CSV
            val pregunta = Pregunta(
                pregunta = tokens[0],       //Texto de la pregunta
                tipo = tokens[1],           //Tipo de pregunta (por ejemplo, opción múltiple)
                dificultad = tokens[2],     //Nivel de dificultad
                respuestaC = tokens[3],     //Respuesta correcta
                respuestaI1 = tokens[4],    //Primera respuesta incorrecta
                respuestaI2 = tokens[5],    //Segunda respuesta incorrecta
                respuestaI3 = tokens[6],    //Tercera respuesta incorrecta
                puntos = tokens[7].toInt()  //Puntos asignados a la pregunta
            )

            //Añade la pregunta a la lista de preguntas
            preguntas.add(pregunta)
        }
    }

    // nserta todas las preguntas leidas del CSV en la base de datos utilizando el DAO de Pregunta
    database.preguntaDao().insertarPreguntas(preguntas)
}