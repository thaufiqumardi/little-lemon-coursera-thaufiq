package com.thaufiqumardi.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Home(navController: NavController) {
  TopAppBar(navController = navController)
}

@Composable
fun TopAppBar(navController: NavController) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .padding(5.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(
      painter = painterResource(id = R.drawable.logo),
      contentDescription = "Little Lemon Logo",
      contentScale = ContentScale.FillWidth,
      modifier = Modifier
        .size(75.dp)
        .fillMaxWidth(0.1F)
    )
    Image(
      painter = painterResource(id = R.drawable.profile),
      contentDescription = "profile",
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .height(44.dp)
        .width(44.dp)
        .clickable { navController.navigate(ProfileDestination.route) }
    )
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome(){
  val navController = rememberNavController()
  Home(navController)
}