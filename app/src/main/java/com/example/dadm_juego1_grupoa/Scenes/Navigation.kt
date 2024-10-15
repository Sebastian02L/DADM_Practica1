package com.example.dadm_juego1_grupoa.Scenes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.MainMenu.route){
        composable(route = Screen.MainMenu.route){
            BodyContent(navController)
        }

        composable(route = Screen.Ranking.route){
            BodyContentRanking(navController)
        }
    }
}