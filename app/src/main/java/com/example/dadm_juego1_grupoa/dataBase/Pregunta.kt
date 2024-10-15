package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preguntas")
data class Pregunta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pregunta: String,
    val tipo: String,
    val dificultad: String,
    val respuestaC: String,
    val respuestaI1: String,
    val respuestaI2: String,
    val respuestaI3: String
)

