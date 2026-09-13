package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.example.ui.theme.AppTheme
import com.example.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                LobbyScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun LobbyScreen(viewModel: MainViewModel) {
    val isGameActive by viewModel.isGameActive.collectAsState()
    val spawnLocation by viewModel.spawnLocation.collectAsState()

    // Using a rich sunset gradient as the background fallback since image generation quota was hit
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF311B58), // Deep purple top
            Color(0xFF9C27B0), // Purple mid
            Color(0xFFFF5722), // Orange sunset
            Color(0xFF1E1E24)  // Dark ground
        )
    )

    Box(modifier = Modifier.fillMaxSize().background(backgroundBrush)) {
        
        // --- 3D EARTH VIEW SATELLITE (GOOGLE MAPS) ---
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(
                mapType = MapType.SATELLITE,
                isBuildingEnabled = true
            )
        )

        if (!isGameActive) {
            // --- TOP BAR ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.DarkGray)
                            .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Deepu_07", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Lv. 18", color = Color.LightGray, fontSize = 12.sp)
                    }
                }

                // Currency Section
                Row(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CurrencyItem(icon = Icons.Default.MonetizationOn, value = "52,680", color = Color(0xFFFFC107))
                    CurrencyItem(icon = Icons.Default.Diamond, value = "1,240", color = Color(0xFF00E5FF))
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(20.dp))
                }

                // System Icons
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SystemIcon(Icons.Default.People)
                    SystemIcon(Icons.Default.Mail)
                    SystemIcon(Icons.Default.Settings)
                    SystemIcon(Icons.Default.Wifi)
                    SystemIcon(Icons.Default.BatteryFull)
                }
            }

            // --- LEFT SIDEBAR ---
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp, top = 60.dp)
                    .width(180.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Event Banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Brush.horizontalGradient(listOf(Color(0xFFE91E63), Color(0xFF3F51B5))))
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Column {
                        Text("VICE CITY SEASON 3", color = Color.White, fontWeight = FontWeight.Black, fontSize = 14.sp)
                        Text("NEW MAP. NEW STORIES.", color = Color.White, fontSize = 10.sp)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Menu Items
                val menuItems = listOf(
                    "HOME" to Icons.Default.Home,
                    "STORE" to Icons.Default.ShoppingCart,
                    "LOADOUT" to Icons.Default.Build,
                    "CHARACTER" to Icons.Default.Person,
                    "VEHICLES" to Icons.Default.DirectionsCar,
                    "MAP" to Icons.Default.Map,
                    "MISSIONS" to Icons.Default.Assignment,
                    "EVENTS" to Icons.Default.Event
                )
                menuItems.forEachIndexed { index, (title, icon) ->
                    val isSelected = index == 0
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (isSelected) Color.Black.copy(alpha = 0.8f) else Color.Transparent)
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isSelected) {
                            Box(modifier = Modifier.width(4.dp).height(24.dp).background(Color(0xFFFFC107)))
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        Icon(icon, contentDescription = title, tint = if (isSelected) Color(0xFFFFC107) else Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(title, color = if (isSelected) Color(0xFFFFC107) else Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }

            // --- BOTTOM LEFT CHAT ---
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 24.dp)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ChatBubbleOutline, contentDescription = "Chat", tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("[World] Rohan_07: Anyone for squad?", color = Color.White, fontSize = 12.sp)
            }

            // --- RIGHT SIDEBAR WIDGETS ---
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 100.dp, end = 16.dp)
                    .width(220.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Mission Widget
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(12.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.BottomStart)) {
                        Text("THE NEXT CHAPTER", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Complete 5 missions to unlock", color = Color.LightGray, fontSize = 10.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                            Text("Vice City Map", color = Color(0xFFFFC107), fontSize = 10.sp)
                        }
                    }
                }

                // Daily Rewards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CardGiftcard, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("DAILY REWARDS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("23h 14m", color = Color.LightGray, fontSize = 10.sp)
                    }
                }

                // Battle Pass
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("BATTLE PASS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("Lv. 12", color = Color.LightGray, fontSize = 10.sp)
                    }
                }
            }

            // --- BOTTOM RIGHT PLAY SECTION ---
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 24.dp)
                    .width(260.dp),
                horizontalAlignment = Alignment.End
            ) {
                // Game Mode Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("BR - RANKED", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Random Map", color = Color.LightGray, fontSize = 12.sp)
                    }
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFE91E63), modifier = Modifier.size(32.dp))
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    // Squad selector
                    Row(
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.6f))
                            .padding(horizontal = 12.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("4", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.DarkGray)
                            .padding(12.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    
                    // Play Button
                    Button(
                        onClick = { viewModel.spawnCharacter() },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
                        shape = RoundedCornerShape(0.dp) // Square button to match style
                    ) {
                        Text("PLAY", color = Color.Black, fontWeight = FontWeight.Black, fontSize = 24.sp, modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }

        } else {
            // Active Game State Overlay (Spawned)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.85f))
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "UPLINK ESTABLISHED",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFC107)
                )
                Spacer(Modifier.height(32.dp))
                
                Text(
                    text = "YOU HAVE SPAWNED IN",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.LightGray
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = spawnLocation ?: "Unknown Sector",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                
                Spacer(Modifier.height(48.dp))
                Button(
                    onClick = { viewModel.simulatePermadeath() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                ) {
                    Text("SIMULATE PERMADEATH", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CurrencyItem(icon: ImageVector, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun SystemIcon(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(Color.Black.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
    }
}
