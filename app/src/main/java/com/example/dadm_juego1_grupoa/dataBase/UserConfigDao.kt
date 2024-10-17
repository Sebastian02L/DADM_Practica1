package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserConfigDao {
    @Insert
    fun insertarUserConfig(userConfig: List<UserConfig>)

    @Query("SELECT * FROM userConfig")
    fun obtenerUserConfig(): List<UserConfig>

    @Query("UPDATE userConfig SET nombre = :nombre, categoria = :categoria, numPreguntas = :numPreguntas, dificultad = :dificultad WHERE id = 0")
    fun actualizarPregunta(id: Int, nombre: String, categoria: String, numPreguntas: Int, dificultad: String)
}