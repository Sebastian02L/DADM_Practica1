package com.example.dadm_juego1_grupoa.Scenes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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

        composable(route = Screen.GameOptions.route){
            BodyContentGameOptions(navController)
        }

        //Cuando se cree la pantalla de juego se tiene que cabiar el start destination.
        composable(route = Screen.Score.route+"/{playerName}/{nQuestions}/{correctAnswers}/{points}/{timePerQuestion}/{category}",
            arguments = listOf(
                navArgument("playerName"){type = NavType.StringType},
                navArgument("nQuestions"){type = NavType.IntType},
                navArgument("correctAnswers"){type = NavType.IntType},
                navArgument("points"){type = NavType.IntType},
                navArgument("timePerQuestion"){type = NavType.StringType},
                navArgument("category") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName")?:""
            val nQuestions = backStackEntry.arguments?.getInt("nQuestions")?:0
            val correctAnswers = backStackEntry.arguments?.getInt("correctAnswers")?:0
            val points = backStackEntry.arguments?.getInt("points")?:0
            val timePerQuestion : List<Int> = backStackEntry.arguments?.getString("timePerQuestion")?.split(",")?.map{it.toInt()}?: emptyList()
            val category = backStackEntry.arguments?.getString("category") ?: ""
            BodyContentScore(navController, playerName, nQuestions, correctAnswers, points, timePerQuestion, category)
        }

        //
        composable(route = Screen.Game.route+"/{playerName}/{category}/{difficulty}/{nQuestions}",
            arguments = listOf(
                navArgument("playerName") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("nQuestions") { type = NavType.IntType }
            )
        ){ backStackEntry ->
            val playerName = backStackEntry.arguments?.getString("playerName") ?: ""
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: ""
            val nQuestions = backStackEntry.arguments?.getInt("nQuestions") ?: 0
            BodyContentGame(navController, playerName, category, difficulty, nQuestions)
        }
    }
}