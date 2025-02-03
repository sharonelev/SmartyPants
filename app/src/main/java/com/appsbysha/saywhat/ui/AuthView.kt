package com.appsbysha.saywhat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import com.appsbysha.saywhat.viewmodels.AuthViewModel


/**
 * Created by sharone on 03/02/2025.
 */


@Composable
fun AuthView(viewModel: AuthViewModel) {
    val emailState = viewModel.email.collectAsState()
    val passwordState = viewModel.password.collectAsState()

    val context = LocalContext.current // Get the context

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { viewModel.updateEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { viewModel.updatePassword(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.signInUser()
                if (emailState.value.isNotEmpty() && passwordState.value.isNotEmpty()) {
                    Toast.makeText(context, "Signing in...", Toast.LENGTH_SHORT).show()
                    // Perform actual sign-in logic here (e.g., API call)
                } else {
                    Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                }


            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White) // Example styling
        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(8.dp))


        TextButton(
            onClick = {
               viewModel.createNewUser()
                // we'll just show a Toast.
                Toast.makeText(context, "Creating account...", Toast.LENGTH_SHORT).show()
                // Navigate to the create account screen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create Account", color = Color.Gray) // Example styling
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
   // AuthView()
}