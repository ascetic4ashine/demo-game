package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LegacyCharacter::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OmniEarthDatabase : RoomDatabase() {
    abstract fun legacyCharacterDao(): LegacyCharacterDao

    companion object {
        @Volatile
        private var INSTANCE: OmniEarthDatabase? = null

        fun getDatabase(context: Context): OmniEarthDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OmniEarthDatabase::class.java,
                    "omniearth_legacy_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
