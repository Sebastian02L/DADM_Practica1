package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PreguntaDao {
    @Insert
    fun insertarPreguntas(preguntas: List<Pregunta>)

    @Query("SELECT * FROM preguntas")
    fun obtenerTodasLasPreguntas(): List<Pregunta>

    @Query("SELECT * FROM preguntas WHERE tipo = :tipo")
    fun obtenerPreguntasPorTipo(tipo: String): List<Pregunta>

    @Query("SELECT * FROM preguntas WHERE tipo = :tipo AND dificultad = :dificultad1 ORDER BY RANDOM() LIMIT :numPreguntas * 0.6 " +
            "UNION ALL " +
            "SELECT * FROM preguntas WHERE tipo = :tipo AND dificultad = :dificultad2 ORDER BY RANDOM() LIMIT :numPreguntas * 0.4")
    fun obtenerPreguntasPorTipoDificultadesNum(tipo: String, dificultad1: String, dificultad2: String, numPreguntas: Int): List<Pregunta>

    @Query("SELECT * FROM preguntas WHERE tipo = :tipo AND dificultad = :dificultad ORDER BY RANDOM() LIMIT :numPreguntas")
    fun obtenerPreguntasPorTipoFacilNum(tipo: String, dificultad : String, numPreguntas: Int): List<Pregunta>

}

