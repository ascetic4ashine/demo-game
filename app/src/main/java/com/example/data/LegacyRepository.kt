package com.example.data

import kotlinx.coroutines.flow.Flow

class LegacyRepository(private val legacyCharacterDao: LegacyCharacterDao) {
    val allCharacters: Flow<List<LegacyCharacter>> = legacyCharacterDao.getAllLegacyCharacters()

    suspend fun insert(character: LegacyCharacter) {
        legacyCharacterDao.insertLegacyCharacter(character)
    }

    suspend fun deleteById(id: Int) {
        legacyCharacterDao.deleteLegacyCharacterById(id)
    }
}
