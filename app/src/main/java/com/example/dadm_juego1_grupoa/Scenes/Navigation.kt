package com.example.dadm_juego1_grupoa.Scenes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

//Esta funcion contiene las llamadas a los composables de las distintas pantallas del videojuego
@Composable
fun Navigation(){
    //Conseguimos una referencia al navController, el cual pasaremos a las pantallas para
    // poder cambiar desde ellas la pantalla mostrada.
    val navController = rememberNavController()

    //Creamos el NavHost indicandole que el destino inicial debe el menu principal
    NavHost(navController, startDestination = Screen.MainMenu.route){

        //Por cada pantalla, la añadimos al "NavGraph" con el metodo "composable()"
        //indicando la ruta unica y la llamada a la funcion
        composable(route = Screen.MainMenu.route){
            BodyContent(navController)
        }

        composable(route = Screen.Ranking.route){
            BodyContentRanking(navController)
        }

        composable(route = Screen.GameOptions.route){
            BodyContentGameOptions(navController)
        }

        //En el caso de tener que pasar parametros de una pantalla a otra, estos deben escribirse en
        //la ruta de la pantalla. Luego mediante los "navArgument" se debe define el tipo de dato de cada parametro y una clave que los identifique
        //Posteriorment en el backStackEntry se define como se debe extraer el valor de estos.
        composable(route = Screen.Score.route+"/{playerName}/{nQuestions}/{correctAnswers}/{points}/{timePerQuestion}/{category}",
            arguments = listOf(
                navArgument("playerName"){type = NavType.StringType},
                navArgument("nQuestions"){type = NavType.IntType},
                navArgument("correctAnswers"){type = NavType.IntType},
                navArgument("points"){type = NavType.IntType},
                navArgument("timePerQuestion"){type = NavType.StringType},
                navArgument("category") { type = NavType.StringType }
            )
        ){ backStackEntry ->    //:? sirve para definir valores por defecto a las variables en caso de que no se consiga ningun valor
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