package com.example.dadm_juego1_grupoa.dataBase


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RankingDao {
    @Insert
    fun insertarRanking(ranking: Ranking)

    @Query("SELECT * FROM ranking  WHERE categoria = :category ORDER BY puntuacion DESC LIMIT 3")
    fun obtenerRanking(category: String): List<Ranking>
}