package com.codingtroops.foodies.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codingtroops.foodies.navigation.home.HomeTab
import com.codingtroops.foodies.navigation.NavigationItem
import com.codingtroops.foodies.ui.screen.profile.ProfileTab
import com.codingtroops.foodies.ui.theme.Black200
import com.codingtroops.foodies.ui.theme.Orange

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                Navigation(navController = navController)
            }
        }
    )
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(route = NavigationItem.Home.route) {
            HomeTab()
        }
        composable(
            route = NavigationItem.Profile.route
        ) {
            ProfileTab()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val tabs = listOf(
        NavigationItem.Home,
        NavigationItem.Profile
    )
    BottomNavigation(
        backgroundColor = Orange,
        contentColor = Black200
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        tabs.forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = tab.icon), contentDescription = tab.title) },
                label = { Text(text = tab.title) },
                selectedContentColor = White,
                unselectedContentColor = White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == tab.route,
                onClick = {
                    navController.navigate(tab.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}