package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

//Define una entidad de base de datos que representa la tabla "ranking"
//Cada instancia de 'Ranking' corresponde a una fila en la tabla
@Entity(tableName = "ranking")
data class Ranking(
    //La clave primaria de la tabla, se generara automaticamente
    //Este campo identifica de manera unica cada entrada en la tabla
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,    //El nombre del jugador
    val puntuacion: Int,   //La puntuación obtenida por el jugador
    val categoria: String  //La categoría de juego
)