package com.codingtroops.foodies.navigation

import com.codingtroops.foodies.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_baseline_home_24, "Home")
    object Profile : NavigationItem("profile", R.drawable.ic_baseline_self_improvement_24, "Profile")
}