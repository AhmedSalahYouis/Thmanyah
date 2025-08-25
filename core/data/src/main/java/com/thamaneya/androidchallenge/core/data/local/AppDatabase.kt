package com.thamaneya.androidchallenge.core.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

/**
 * Room database for the app
 */
@Database(
    entities = [
        HomeSectionEntity::class,
        HomeItemEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun homeSectionDao(): HomeSectionDao
    abstract fun homeItemDao(): HomeItemDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "thmanyah_database"
                )
                .fallbackToDestructiveMigration(false)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}



