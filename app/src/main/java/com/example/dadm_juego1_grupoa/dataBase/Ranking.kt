package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ranking")
data class Ranking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val puntuacion: Int,
    val categoria: String
)