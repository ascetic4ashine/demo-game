package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LegacyCharacterDao {
    @Query("SELECT * FROM legacy_characters ORDER BY timeOfDeath DESC")
    fun getAllLegacyCharacters(): Flow<List<LegacyCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLegacyCharacter(character: LegacyCharacter)

    @Query("DELETE FROM legacy_characters WHERE id = :id")
    suspend fun deleteLegacyCharacterById(id: Int)
}
