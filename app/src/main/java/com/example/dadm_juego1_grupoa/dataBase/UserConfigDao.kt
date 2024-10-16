package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserConfigDao {
    @Insert
    fun insertarUserConfig(userConfig: List<UserConfig>)

    @Query("SELECT * FROM ranking")
    fun obtenerUserConfig(): List<UserConfig>
}