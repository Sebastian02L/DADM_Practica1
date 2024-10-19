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
    //ESTILOS
    val colors = listOf(
        MaterialTheme.colorScheme.background, // Azul claro
        MaterialTheme.colorScheme.surface // Color rosado claro
    ) // Colores del degradado
    val brush = Brush.linearGradient(colors)

    //VARIABLES
    val context = LocalContext.current
    var totalTime : Int by rememberSaveable { mutableStateOf(0) }
    var averageTime : Int by rememberSaveable { mutableStateOf(0) }
    val database = AppDatabase.getDatabase(context)

    LaunchedEffect (Unit){
        CoroutineScope (Dispatchers.IO).launch{
            database.rankingDao().insertarRanking(Ranking(nombre = playerName, puntuacion = points, categoria = category))
        }
    }

    var sumTime = 0
    for (time in timePerQuestion){
        sumTime += time
    }

    totalTime = sumTime
    averageTime = sumTime / timePerQuestion.size



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush), // Color degradado fondo
        //verticalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        /*
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(20.dp) // Añadir padding al Row si es necesario
            .weight(1.5f)
    ) {

        //Texto
        CustomText("RESULTADOS", 55, modifier = Modifier.weight(1.5f))
        /*
        Text(
            text = "RESULTADOS",
            style = TextStyle(
                fontSize = 55.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = Color(0xFFEFB8C8),
            //modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )*/

    }*/
        CustomText("RESULTADOS", 40, modifier = Modifier.weight(0.5f))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Color transparente para que se vea el gradiente
            ),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 8.dp).weight(0.75f)
        ) {
            // Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background( // Aplica el gradiente de fondo
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                CardContent("Puntuación Total: " + points, Modifier.padding(5.dp))
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Color transparente para que se vea el gradiente
            ),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 8.dp).weight(0.75f)
        ) {
            // Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background( // Aplica el gradiente de fondo
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                CardContent(
                    "Respuestas correctas: " + correctAnswers + "/$nQuestions",
                    modifier = Modifier
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Color transparente para que se vea el gradiente
            ),
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 8.dp).weight(0.75f)
        ) {
            // Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background( // Aplica el gradiente de fondo
                        color = MaterialTheme.colorScheme.primary
                    )
            ) {
                CardContent(
                    "Tiempo total: " + totalTime + "\n \n" + "TiempoMedio: " + averageTime,
                    modifier = Modifier
                )
                //CardContent("Tiempo total: \n\n Tiempo medio:", modifier = Modifier)
            }
        }

        CustomElevatedButton(
            "Volver a jugar", Modifier.weight(0.5f),
            onClick = {
                navController.navigate(Screen.GameOptions.route)

            }
        ) { }
        CustomElevatedButton(
            "Volver al menú", Modifier.weight(0.5f),
            onClick = {
                navController.navigate(Screen.MainMenu.route)
            }
        ) { }
        /*ElevatedButton(
            onClick = {
                mediaPlayer.start()
                navController.navigate(Screen.GameOptions.route)

            },
            modifier = Modifier.padding(6.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEFB8C8)
            )
        ) {
            Text(
                text = "Volver a jugar",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
//              fontFamily = montserratFontFamily,
                color = Color.White
            )
        }

        ElevatedButton(
            onClick = {
                navController.navigate(Screen.MainMenu.route)

            },
            modifier = Modifier.padding(6.dp),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEFB8C8)
            )
        ) {
            Text(
                text = "Volver al menú",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
//              fontFamily = montserratFontFamily,
                color = Color.White
            )
        }
        */

}
}
@Composable
private fun CardContent(name:String, modifier: Modifier = Modifier)
{

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



