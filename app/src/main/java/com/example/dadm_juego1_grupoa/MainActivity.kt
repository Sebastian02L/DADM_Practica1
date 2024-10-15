package com.example.dadm_juego1_grupoa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dadm_juego1_grupoa.Scenes.BodyContent
import com.example.dadm_juego1_grupoa.Scenes.Navigation
import com.example.dadm_juego1_grupoa.Scenes.Screen
import com.example.dadm_juego1_grupoa.ui.theme.DADM_juego1_GrupoATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DADM_juego1_GrupoATheme {
                Navigation()
            }
        }
    }
}

/*
@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.MainMenu.route){
        composable(route = Screen.MainMenu.route){
            BodyContent(navController)
        }
    }
}*/
