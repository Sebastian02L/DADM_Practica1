package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

//Define una interfaz DAO (Data Access Object) para acceder a la tabla "UserConfig" en la base de datos
//Esta interfaz contiene metodos para interactuar con los datos relacionados con la configuracion los jugadores
@Dao
interface UserConfigDao {
    //Metodo para insertar una nueva configuracion de usuario en la base de datos
    @Insert
    fun insertarUserConfig(userConfig: UserConfig)

    //Metodo para obtener la configuracion mas reciente
    //Retorna un objeto UserConfig o null si no hay configuraciones guardadas
    @Query("SELECT * FROM userConfig LIMIT 1")
    fun obtenerUserConfig(): UserConfig?

    //Metodo para eliminar todas las configuraciones del usuario de la base de datos
    @Query("DELETE FROM userConfig")
    fun deleteLastConfiguration()
}