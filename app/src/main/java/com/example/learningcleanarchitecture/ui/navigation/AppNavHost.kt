package com.example.learningcleanarchitecture.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.learningcleanarchitecture.ui.NavigationItem
import com.example.learningcleanarchitecture.ui.screen.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController, snackBar:(String) ->Unit){
    NavHost(
        navController,
        startDestination = NavigationItem.HomeTab.route
    ) {

        navigation(
            route = NavigationItem.HomeTab.route,
            startDestination = NavigationItem.HomeTab.HomeScreen.route
        ) {
            composable(route = NavigationItem.HomeTab.HomeScreen.route) {
                // todo 該当のcomposable UI screen
                HomeScreen(snackBar = snackBar)
            }
        }
    }
}