package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
//Se define la clase como una base de datos de Room, especificando las entidades que va a manejar y la versión de la base de datos
//Se utiliza 'exportSchema = false' para no exportar el esquema a un archivo JSON.
@Database(entities = [Pregunta::class, UserConfig::class, Ranking::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    //Metodo abstracto para obtener el DAO (Data Access Object) que maneja las operaciones CRUD para la entidad 'Pregunta'
    abstract fun preguntaDao(): PreguntaDao
    //Metodo abstracto para obtener el DAO que maneja las operaciones CRUD para la entidad 'UserConfig'
    abstract fun userConfigDao() : UserConfigDao
    //Metodo abstracto para obtener el DAO que maneja las operaciones CRUD para la entidad 'Ranking'
    abstract fun rankingDao(): RankingDao

    //Objeto companion para mantener una unica instancia de la base de datos en la aplicacion
    companion object {
        //Variable volatil para asegurar que los cambios en la instancia sean visibles entre hilos
        @Volatile
        private var INSTANCE: AppDatabase? = null
        //Funcion que proporciona la instancia de la base de datos
        fun getDatabase(context: Context): AppDatabase {
            //Si ya existe una instancia, se devuelve. De lo contrario, se inicializa de manera sincronizada
            return INSTANCE ?: synchronized(this) {
                //Se crea la base de datos utilizando Room.databaseBuilder, donde se especifica el contexto de la aplicacion,
                //la clase de la base de datos y el nombre del archivo de la base de datos
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"

                ).fallbackToDestructiveMigration() // Elimina la base de datos si hay un cambio en la versión
                    .build()
                //Se asigna la nueva instancia a la variable INSTANCE
                INSTANCE = instance
                instance
            }
        }
    }
}



