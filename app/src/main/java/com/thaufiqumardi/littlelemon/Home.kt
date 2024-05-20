package com.thaufiqumardi.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun Home(navController: NavController, database: AppDatabase) {
  val databaseMenuItems by database.menuItemDao().getAll().observeAsState(initial = emptyList())
  Column {
    TopAppBar(navController = navController)
    HeroSection(menuItemsLocal = databaseMenuItems)
  }
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
    modifier = Modifier
      .fillMaxWidth(1.5f)
      .background(color = colorResource(id = R.color.primary_1))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        stringResource(id = R.string.restaurant_name),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.primary_2)
      )
      Text(
        stringResource(id = R.string.restaurant_city),
        style = MaterialTheme.typography.bodyLarge,
        color = colorResource(id = R.color.secondary_3)
      )
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .padding(top = 16.dp)
      ) {
        Text(
          text = stringResource(id = R.string.restaurant_desc),
          style = MaterialTheme.typography.bodySmall,
          modifier = Modifier
            .fillMaxWidth(0.7f),
          color = colorResource(id = R.color.secondary_3)
        )
        Image(
          painter = painterResource(id = R.drawable.hero_image),
          contentDescription = "Hero Image",
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(150.dp)
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
      var searchPhrase by remember { mutableStateOf("") }
      OutlinedTextField(
        value = searchPhrase,
        onValueChange = { searchPhrase = it },
        label = { Text("Search for your favorite food") },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon"
          )
        },
        modifier = Modifier
          .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = colorResource(id = R.color.white),
          unfocusedContainerColor = colorResource(id = R.color.white),
        )
      )
      if (searchPhrase.isNotEmpty()) {
        menuItems = menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
      }
    }
    Spacer(modifier = Modifier.height(16.dp))
  }
  MenuItems(menuItems = menuItems)

}

@Composable
fun MenuItems(menuItems: List<MenuItemRoom>){
  LazyColumn {
    items(
      items = menuItems,
      itemContent = { menuItem ->
        MenuItemCard(menuItem = menuItem)
      }
    )
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuItemCard() {
  MenuItemCard(
    menuItem = MenuItemRoom(
      1,
      "title",
      10.0,
      "desc",
      "category",
      "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true"
    )
  )
}
@Preview(showBackground = true)
@Composable
fun PreviewHome() {
  val navController = rememberNavController()
  Home(navController = navController, database =  MainActivity.getDatabase(LocalContext.current))
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(menuItem: MenuItemRoom) {
  Card(
    modifier = Modifier.padding(16.dp)
  ) {
    Row {
      Column {
        Text(text = menuItem.title)
        Text(text = menuItem.desc)
        Text(text = menuItem.price.toString())
      }
      GlideImage(
        model = menuItem.image,
        contentDescription = "Menu Item Image",
        modifier = Modifier.size(100.dp)
      )
    }
  }
}