package com.example.dadm_juego1_grupoa.Scenes

sealed class Screen(val route: String){
    object MainMenu : Screen("MainMenu")
    object Ranking : Screen("Ranking")
    object Score : Screen("Score")
}