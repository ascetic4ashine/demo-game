package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val apiKey = BuildConfig.GEMINI_API_KEY

    // --- Game Engine / Spawn State ---
    private val spawnLocations = listOf(
        "London, UK", "Beijing, China", "Tokyo, Japan", "New York, USA",
        "Miami, USA", "Berlin, Germany", "Rio de Janeiro, Brazil",
        "Moscow, Russia", "Dubai, UAE", "Paris, France", "Cape Town, South Africa",
        "Sydney, Australia", "Mumbai, India", "Toronto, Canada"
    )

    private val _spawnLocation = MutableStateFlow<String?>(null)
    val spawnLocation: StateFlow<String?> = _spawnLocation.asStateFlow()

    private val _isGameActive = MutableStateFlow(false)
    val isGameActive: StateFlow<Boolean> = _isGameActive.asStateFlow()

    private val _inventory = MutableStateFlow<List<String>>(emptyList())
    val inventory: StateFlow<List<String>> = _inventory.asStateFlow()

    fun spawnCharacter() {
        _spawnLocation.value = spawnLocations.random()
        _isGameActive.value = true
        _inventory.value = emptyList() // Start with empty inventory
    }

    fun scavengeWeapon() {
        val newWeapon = com.example.data.WeaponData.allWeapons.random()
        _inventory.value = _inventory.value + newWeapon
    }

    fun simulatePermadeath() {
        _isGameActive.value = false
        _spawnLocation.value = null
        _inventory.value = emptyList()
    }
}
