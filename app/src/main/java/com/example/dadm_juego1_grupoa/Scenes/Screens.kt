package com.example.dadm_juego1_grupoa.Scenes
//Esta clase representa el grafo de navegacion del programa, teniendo un "object" por cada
//pantalla del videojuego. Cada objeto guarda con string con su ruta única.
sealed class Screen(val route: String){
    object MainMenu : Screen("MainMenu")
    object GameOptions : Screen("GameOptions")
    object Ranking : Screen("Ranking")
    object Score : Screen("Score")
    object Game : Screen("Game")
}