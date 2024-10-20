package com.example.dadm_juego1_grupoa.Scenes

//import androidx.compose.material.icons.filled.ExpandMore
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dadm_juego1_grupoa.R
import com.example.dadm_juego1_grupoa.dataBase.AppDatabase
import com.example.dadm_juego1_grupoa.dataBase.Ranking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun BodyContentScore(navController: NavController, playerName : String, nQuestions : Int, correctAnswers : Int, points : Int, timePerQuestion : List<Int>, category : String){
    //Efecto de degradado para el fondo
    val colors = listOf(
        MaterialTheme.colorScheme.background, // Azul claro
        MaterialTheme.colorScheme.surface // Color rosado claro
    ) //Colores del degradado
    val brush = Brush.linearGradient(colors)

    //variables:
    //Se utiliza rememberSaveable para mantener el estado y se obtiene el contexto de la aplicación
    //Conexión con la base de datos para guardar en el ranking al finalizar la partida
    val context = LocalContext.current
    var totalTime : Int by rememberSaveable { mutableStateOf(0) }
    var averageTime : Int by rememberSaveable { mutableStateOf(0) }
    val database = AppDatabase.getDatabase(context)

    //Insertar el ranking de forma asíncrona en la base de datos
    LaunchedEffect (Unit){
        CoroutineScope (Dispatchers.IO).launch{
            database.rankingDao().insertarRanking(Ranking(nombre = playerName, puntuacion = points, categoria = category))
        }
    }

    //Se calculan las estadisticas del tiempo total y tiempo medio por pegunta
    var sumTime = 0
    for (time in timePerQuestion){
        sumTime += time
    }
    totalTime = sumTime
    averageTime = sumTime / timePerQuestion.size

    //Columna que almacena los textos y tarjetas de la interfaz
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush), //Aplicar degradado
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        //Texto para mostrar el titulo de la pantalla
        CustomText("RESULTADOS", 40, modifier = Modifier.weight(0.5f))

        //Tarjeta para mostrar la puntuacion total
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent //Color transparente para que se vea el efecto de gradiente
            ),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 8.dp).weight(0.75f)
        ) {
            //Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                CardContent("Puntuación Total: " + points, Modifier.padding(5.dp))
            }
        }

        //Tarjeta para mostrar el numero de respuestas correctas
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent //Color transparente para que se vea el efecto de gradiente
            ),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 8.dp).weight(0.75f)
        ) {
            //Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                CardContent(
                    "Respuestas correctas: " + correctAnswers + "/$nQuestions",
                    modifier = Modifier
                )
            }
        }

        //Tarjeta para mostrar las estadisticas del tiempo
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent //Color transparente para que se vea el efecto de gradiente
            ),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 8.dp).weight(0.75f)
        ) {
            //Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background( // Aplica el gradiente de fondo
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                CardContent(
                    "Tiempo total: " + totalTime + "\n \n" + "Tiempo Medio: " + averageTime,
                    modifier = Modifier
                )

            }
        }

        //Boton para pasar a la pantalla del juego, paso de escenas con  navController
        CustomElevatedButton(
            "Volver a jugar", Modifier.weight(0.5f),
            onClick = {
                navController.navigate(Screen.GameOptions.route)

            }
        ) { }
        //Boton para pasar almenu principal, paso de escenas con  navController
        CustomElevatedButton(
            "Volver al menú", Modifier.weight(0.5f),
            onClick = {
                navController.navigate(Screen.MainMenu.route)
            }
        ) { }


    }
    }

//Muestra el contenido de las tarjetas de manera estilizada
@Composable
private fun CardContent(name:String, modifier: Modifier = Modifier)
{
    //Organiza la información de la tarjeta en una fila y columna, centrando el texto
    Row (modifier=modifier
    )
    {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center //modificacion del materialTheme
            )

        }
    }
}



