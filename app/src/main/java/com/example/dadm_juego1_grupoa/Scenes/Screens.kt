package com.example.dadm_juego1_grupoa.Scenes

sealed class Screen(val route: String){
    object MainMenu : Screen("MainMenu")
    object GameOptions : Screen("GameOptions")
    object Ranking : Screen("Ranking")
    object Score : Screen("Score")
}