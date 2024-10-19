package com.example.dadm_juego1_grupoa.dataBase


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

//Define una interfaz DAO (Data Access Object) para acceder a la tabla "ranking" en la base de datos
//Esta interfaz contiene metodos para interactuar con los datos relacionados con el ranking de los jugadores
@Dao
interface RankingDao {
    //Metodo para insertar una nueva entrada de ranking en la base de datos
    //Se espera que se pase un objeto 'Ranking' que contenga los detalles a insertar
    @Insert
    fun insertarRanking(ranking: Ranking)

    //Metodo para obtener el ranking de los jugadores seguin la categoria especificada
    //Se seleccionan las tres mejores puntuaciones en esa categoria, ordenadas de mayor a menor
    @Query("SELECT * FROM ranking  WHERE categoria = :category ORDER BY puntuacion DESC LIMIT 3")
    fun obtenerRanking(category: String): List<Ranking>
}