package com.codingtroops.foodies.navigation.profile

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileTab() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "") {

    }
}