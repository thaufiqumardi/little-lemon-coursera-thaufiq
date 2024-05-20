package com.thaufiqumardi.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationComposable(navController: NavHostController, database: AppDatabase) {
  val hasUserData = hasUserDataInSharedPreference()
  NavHost(
    navController = navController,
    startDestination = if(hasUserData) HomeDestination.route else OnboardingDestination.route
  ){
    composable(OnboardingDestination.route) { Onboarding(navController = navController)}
    composable(HomeDestination.route) { Home(navController = navController, database = database) }
    composable(ProfileDestination.route) { Profile(navController = navController)}
  }
}

@Composable
fun hasUserDataInSharedPreference() : Boolean {
  val context = LocalContext.current
  val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
  val email = sharedPreferences.getString(EMAIL, "")?: ""
  return email.isNotBlank()
}