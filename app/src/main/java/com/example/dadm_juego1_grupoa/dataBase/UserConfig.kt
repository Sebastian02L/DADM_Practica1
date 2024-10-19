package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

//Define una entidad de base de datos llamada "userConfig"
//Esta clase representa la configuración del usuario en la aplicacion
@Entity(tableName = "userConfig")
data class UserConfig (
    //El identificador unico de cada configuracion del usuario
    //Se generara automáticamente al insertar una nueva entrada en la base de datos
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,    //El nombre del usuario
    val categoria: String, //La categoria de preguntas
    val numPreguntas: Int, //El numero de preguntas
    val dificultad: String //El nivel de diicultad
)