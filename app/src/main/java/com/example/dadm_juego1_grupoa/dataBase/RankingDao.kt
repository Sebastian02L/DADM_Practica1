package com.example.dadm_juego1_grupoa.dataBase


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RankingDao {
    @Insert
    fun insertarRanking(ranking: List<Ranking>)

    @Query("SELECT * FROM ranking")
    fun obtenerRanking(): List<Ranking>
}