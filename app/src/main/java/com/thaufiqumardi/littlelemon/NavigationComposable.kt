package com.thaufiqumardi.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationComposable(navController: NavHostController) {
  NavHost(
    navController = navController,
    startDestination = OnboardingDestination.route
  ){
    composable(OnboardingDestination.route) { Onboarding(navController = navController)}
    composable(HomeDestination.route) { Home(navController = navController) }
    composable(ProfileDestination.route) { Profile(navController = navController)}
  }
}