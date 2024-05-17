package com.thaufiqumardi.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Home(navController: NavController, database: AppDatabase) {
  val databaseMenuItems by database.menuItemDao().getAll().observeAsState(initial = emptyList())
  TopAppBar(navController = navController)
  HeroSection(menuItemsLocal = databaseMenuItems)
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

@Composable
fun HeroSection(menuItemsLocal: List<MenuItemRoom>) {
  var menuItems = menuItemsLocal
  var selectedCategory by remember { mutableStateOf("") }

  Column(
    modifier = Modifier.padding(5.dp)
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxWidth(1.5f)
        .background(color = colorResource(id = R.color.primary_1))
    ) {
      Text(
        stringResource(id = R.string.restaurant_name),
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.primary_2)
      )
      Text(
        stringResource(id = R.string.restaurant_city),
        fontSize = 24.sp,
        color = colorResource(id = R.color.secondary_3)
      )
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 10.dp)
      ) {
        Text(
          text = stringResource(id = R.string.restaurant_desc),
          style = MaterialTheme.typography.bodySmall,
          modifier = Modifier
            .padding(bottom = 28.dp, end = 20.dp)
            .fillMaxWidth(0.6f),
          color = colorResource(id = R.color.secondary_3)
        )
        Image(
          painter = painterResource(id = R.drawable.hero_image),
          contentDescription = "Hero Image",
          modifier = Modifier
            .fillMaxWidth(0.5F)
            .clip(RoundedCornerShape(10.dp))
        )
      }
    }
  }
}
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
  val navController = rememberNavController()
  Home(navController)
}