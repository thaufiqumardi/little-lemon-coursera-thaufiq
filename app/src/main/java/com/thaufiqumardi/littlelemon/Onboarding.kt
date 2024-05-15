package com.thaufiqumardi.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun Onboarding(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var isEmailValid by rememberSaveable {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .background(color = colorResource(id = R.color.primary_1))
                .height(150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Let's get to know you",
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(id = R.color.white),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier
            .align(alignment = Alignment.Start)
            .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Text(
                text= "Personal Information",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it},
                label = {
                    Text(text = "Firstname", style=MaterialTheme.typography.bodyMedium)
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it},
                label = {
                    Text(text = "Lastname", style=MaterialTheme.typography.bodyMedium)
                },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = isEmailValid(it)
                },
                label = { Text("Email Address", style = MaterialTheme.typography.bodyMedium) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = !isEmailValid,
                supportingText = {
                    if (!isEmailValid) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Invalid email",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    if (!isEmailValid)
                        Icon(Icons.Filled.Info,"error", tint = MaterialTheme.colorScheme.error)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
             if(firstName.isEmpty() && lastName.isEmpty() && email.isEmpty()) {
                 Toast.makeText(context, "Registration unsuccessful, Please enter all data", Toast.LENGTH_SHORT).show()
             } else {
                 sharedPreferences.edit(commit = true) { putString(FIRST_NAME, firstName) }
                 sharedPreferences.edit(commit = true) { putString(LAST_NAME, lastName) }
                 sharedPreferences.edit(commit = true) { putString(EMAIL, email) }

                 Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                 navController.navigate(HomeDestination.route)
             }
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = colorResource(id = R.color.black),
                containerColor = colorResource(id = R.color.primary_2),
                disabledContainerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(text = "Register")
        }
    }
}

fun isEmailValid(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun PreviewOnboarding() {
    val navController = rememberNavController()
    Onboarding(navController = navController)
}