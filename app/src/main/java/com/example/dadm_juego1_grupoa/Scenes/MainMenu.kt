package com.example.dadm_juego1_grupoa.Scenes

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.dadm_juego1_grupoa.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import com.example.dadm_juego1_grupoa.Greeting
import com.example.dadm_juego1_grupoa.ui.theme.DADM_juego1_GrupoATheme

//Escena del menú principal
//AUN NO ESTA IMPLEMENTADO EL NavController así que no furula
/*@Composable
fun StartScreen(navController: NavController){
    Scaffold {
        BodyContent(navController)
    }
}*/

//Contenido de la escena Start Screen
@Composable
fun BodyContent(/*navController: NavController*/){
    //ESTILOS
    val colors = listOf(Color(0xFF1F6D78), Color(0xFFFFFFFF)) // Colores del degradado
    val brush = Brush.sweepGradient(colors, Offset.Zero)


    //VARIABLES
    val context = LocalContext.current

    //SONIDOS
    //no implementado porque el R.raw.click aun no me funciona bien
//    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.click) }


    //CUERPO DE UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush), // Color degradado fondo
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Titulo del juego
        Text(
            text = "TRIVIAL GAME",
            style = TextStyle(
                fontSize = 66.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = Color(0xFFE8FAF6),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,

            )

        //Boton de inicio de partida
        ElevatedButton(
            onClick = {
                //FUNCION DE PASO ESCENAS CON navController (recordar usar el mediaPlayer?.start justo después)

            },
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2AA896)
            )
        ) {
            Text(
                text = "Jugar",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
//                fontFamily = montserratFontFamily,
                color = Color.White
            )
        }
        ElevatedButton(
            onClick = {
                //FUNCION DE PASO ESCENAS CON navController (recordar usar el mediaPlayer?.start justo después)

            },
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2AA896)
            )
        ) {
            Text(
                text = "Ranking",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
//                fontFamily = montserratFontFamily,
                color = Color.White
            )
        }
        ElevatedButton(
            onClick = {
                //FUNCION DE PASO ESCENAS CON navController (recordar usar el mediaPlayer?.start justo después)

            },
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2AA896)
            )
        ) {
            Text(
                text = "Salir",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun MainMenuPrev() {
    DADM_juego1_GrupoATheme {
        BodyContent()
    }
}