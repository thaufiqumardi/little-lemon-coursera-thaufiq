package com.thaufiqumardi.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.thaufiqumardi.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  private val httpClient = HttpClient(Android){
    install(ContentNegotiation){
      json(contentType = ContentType("text", "plain"))
    }
  }
  private val database by lazy { getDatabase(this) }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      LittleLemonTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          val navHostController = rememberNavController()
          NavigationComposable(navController = navHostController, database = database)
        }
      }
    }
    lifecycleScope.launch(Dispatchers.IO) {
      if(database.menuItemDao().isEmpty()) {
        saveMenuToDatabase(fetchMenu())
      }
    }
  }

  private suspend fun fetchMenu(): List<MenuItemNetwork> {
    val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    val response = httpClient.get(url).body<MenuNetwork>()
    return response.menuItems
  }

  private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
  }

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null
    fun getDatabase(context: Context): AppDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          AppDatabase::class.java,
          "little-lemon"
        ).fallbackToDestructiveMigration().build()
        INSTANCE = instance
        instance
      }
    }
  }
}