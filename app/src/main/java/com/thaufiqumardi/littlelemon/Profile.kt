package com.thaufiqumardi.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Profile(navController: NavController) {
  val context = LocalContext.current
  val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
  val firstName = sharedPreferences.getString(FIRST_NAME, "First Name")
  val lastName = sharedPreferences.getString(LAST_NAME, "Last Name")
  val email = sharedPreferences.getString(EMAIL, "something@gmail.com")

  Column(
    Modifier.fillMaxSize(), Arrangement.Top, Alignment.CenterHorizontally
  ) {
    Image(
      painter = painterResource(id = R.drawable.profile),
      contentDescription = "Profile Photo",
      modifier = Modifier.padding(top= 16.dp)
    )
    Text(text="Personal Information", style = MaterialTheme.typography.titleLarge)
    Text("First Name: $firstName", modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyMedium)
    Text("Last Name: $lastName", modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyMedium)
    Text("Email: $email", modifier = Modifier.padding(top = 8.dp),
      style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.weight(9.5f))
    Button(onClick = {
      sharedPreferences.edit().clear().apply()
      navController.navigate(OnboardingDestination.route) {
        popUpTo(OnboardingDestination.route) { inclusive = true}
      }
      },
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical=16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
          contentColor = colorResource(id = R.color.black),
          containerColor = colorResource(id = R.color.primary_2),
          disabledContainerColor = MaterialTheme.colorScheme.error
        )
      ) {
        Text(text = "Log out")
      }
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
  val navController = rememberNavController()
  Profile(navController = navController)
}