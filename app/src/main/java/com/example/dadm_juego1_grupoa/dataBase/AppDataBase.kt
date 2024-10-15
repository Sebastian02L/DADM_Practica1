package com.example.dadm_juego1_grupoa.dataBase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Pregunta::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preguntaDao(): PreguntaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration() // Elimina la base de datos si hay un cambio en la versión
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}



