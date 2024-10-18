package com.example.dadm_juego1_grupoa.Scenes

//import androidx.compose.material.icons.filled.ExpandMore
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    val colors = listOf(Color(0xFF1F6D78), Color(0xFFFFFFFF)) // Colores del degradado
    val brush = Brush.sweepGradient(colors, Offset.Zero)

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

    val mediaPlayer = MediaPlayer.create(context, R.raw.sonidoboton1)
    mediaPlayer.setVolume(5000.0f, 5000.0f)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush), // Color degradado fondo
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(20.dp) // Añadir padding al Row si es necesario
    ) {

        //Texto
        Text(
            text = "RESULTADOS",
            style = TextStyle(
                fontSize = 55.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = Color(0xFFEFB8C8),
            //modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Color transparente para que se vea el gradiente
            ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            // Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background( // Aplica el gradiente de fondo
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF42A5F5), // Azul claro
                                Color(0xFFEFB8C8)  // Color rosado claro
                            )
                        )
                    )
                    .clip(RoundedCornerShape(16.dp)) // Aplica el recorte a las esquinas
            ) {
                CardContent("Puntuación Total: " + points, modifier = Modifier)
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Color transparente para que se vea el gradiente
            ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            // Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background( // Aplica el gradiente de fondo
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF42A5F5), // Azul claro
                                Color(0xFFEFB8C8)  // Color rosado claro
                            )
                        )
                    )
                    .clip(RoundedCornerShape(16.dp)) // Aplica el recorte a las esquinas
            ) {
                CardContent("Respuestas correctas: " + correctAnswers +"/$nQuestions", modifier = Modifier)
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent // Color transparente para que se vea el gradiente
            ),
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            // Usamos un Box para aplicar el fondo y el clip
            Box(
                modifier = Modifier
                    .background( // Aplica el gradiente de fondo
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF42A5F5), // Azul claro
                                Color(0xFFEFB8C8)  // Color rosado claro
                            )
                        )
                    )
                    .clip(RoundedCornerShape(16.dp)) // Aplica el recorte a las esquinas
            ) {
                CardContent("Tiempo total: " + totalTime + "\n \n" +"TiempoMedio: " +averageTime, modifier = Modifier)
                //CardContent("Tiempo total: \n\n Tiempo medio:", modifier = Modifier)
            }
        }

        ElevatedButton(
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
}
}
@Composable
private fun CardContent(name:String, modifier: Modifier)
{

    Row (modifier=Modifier.padding(24.dp)
    )
    {
        Column(modifier = modifier.weight(1f)
            //.padding(bottom = buttonPadding)
            .padding((24.dp))
        ){

            Text(text = name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center //modificacion del materialTheme
            )

        }

    }
}



