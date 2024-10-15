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

        //Cuando se cree la pantalla de juego se tiene que cabiar el start destination.
        composable(route = Screen.Score.route){
            BodyContentScore(navController)
        }
    }





}