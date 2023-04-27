package com.example.nca_final.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.nca_final.model.entities.Duck
import androidx.room.RoomDatabase
import com.example.nca_final.model.dao.DuckDAO

@Database(
    entities = [Duck::class],
    version = 4
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun DuckDAO(): DuckDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "ducks")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }

        /*fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "ducks")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }*/
    }
}