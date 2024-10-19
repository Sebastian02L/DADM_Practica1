package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Se define una interfaz de acceso a datos (DAO) para interactuar con la tabla "preguntas" en la base de datos
@Dao
interface PreguntaDao {
    //Inserta una lista de objetos 'Pregunta' en la tabla 'preguntas'
    //Esta función permite agregar multiples preguntas en una sola operacion
    @Insert
    fun insertarPreguntas(preguntas: List<Pregunta>)

    //Consulta todas las preguntas de la tabla 'preguntas' y devuelve una lista de objetos 'Pregunta'
    //Utilizado para obtener todas las preguntas almacenadas en la base de datos
    @Query("SELECT * FROM preguntas")
    fun obtenerTodasLasPreguntas(): List<Pregunta>

    //Consulta un conjunto limitado de preguntas basadas en el tipo y la dificultad
    //La función selecciona preguntas al azar ('ORDER BY RANDOM()') y devuelve el numero especificado en 'numPreguntas'
    //Utilizado para obtener preguntas filtradas por tipo y dificultad de manera aleatoria
    @Query("SELECT * FROM preguntas WHERE tipo = :tipo AND dificultad = :dificultad ORDER BY RANDOM() LIMIT :numPreguntas")
    fun obtenerPreguntasPorDificultad(tipo: String, dificultad : String, numPreguntas: Int): List<Pregunta>

}

