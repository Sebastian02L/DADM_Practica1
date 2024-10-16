package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userConfig")
data class UserConfig (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val numPreguntas: Int,
    val dificultad: String
)