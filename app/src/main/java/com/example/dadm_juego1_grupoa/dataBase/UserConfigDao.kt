package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserConfigDao {
    @Upsert
    fun insertarUserConfig(userConfig: UserConfig)

    @Query("SELECT * FROM userConfig LIMIT 1")
    fun obtenerUserConfig(): UserConfig?

    @Query("DELETE FROM userConfig")
    fun deleteLastConfiguration()
}