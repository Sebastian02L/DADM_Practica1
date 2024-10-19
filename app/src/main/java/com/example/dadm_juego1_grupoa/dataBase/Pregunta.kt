package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

//Define una entidad para la tabla "preguntas" en la base de datos
//Cada instancia de esta clase representa una fila en la tabla.
@Entity(tableName = "preguntas")
data class Pregunta(
    //Define la clave primaria de la entidad
    //Se genera automáticamente un valor para 'id' cuando se inserta una nueva fila.
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pregunta: String,    //Campo que almacena el texto de la pregunta
    val tipo: String,        //Campo que indica el tipo de pregunta
    val dificultad: String,  //Campo que almacena la dificultad de la pregunta
    val respuestaC: String,  //Campo que almacena la respuesta correcta a la pregunta
    val respuestaI1: String, //Campo para la primera respuesta incorrecta
    val respuestaI2: String, //Campo para la segunda respuesta incorrecta
    val respuestaI3: String, //Campo para la tercera respuesta incorrecta
    val puntos: Int          //Campo que almacena la cantidad de puntos que vale la pregunta
)

