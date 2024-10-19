package com.example.dadm_juego1_grupoa.Scenes

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import com.example.dadm_juego1_grupoa.MainActivity
import org.w3c.dom.Text

//Contenido de la escena Start Screen
@Composable
fun BodyContent(navController: NavController){
    //Efecto de degradado para el fondo
    val colors = listOf( //Colores del degradado
        MaterialTheme.colorScheme.background, // Azul claro
        MaterialTheme.colorScheme.surface // Color rosado claro
    )
    val brush = Brush.linearGradient(colors)

    //Variable que define el contexto local de la pantalla
    val context = LocalContext.current


    //Cuerpo de la interfaz de usuario
    //Columna que almacena el título del juego y los botones para acceder al resto de interfaces
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( //Aplica el gradiente de fondo
                brush = brush
            ),
        //Se centran los elementos
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Titulo del juego
        CustomText("TRIVIAL GAME",66,Modifier)

        //Boton para pasar a la pantalla del juego, paso de escenas con  navController
        CustomElevatedButton("Jugar",onClick = {
            navController.navigate(Screen.GameOptions.route)
        },) { }
        //Boton para pasar a la pantalla de ranking, paso de escenas con  navController
        CustomElevatedButton("Ranking",onClick = {
            navController.navigate(Screen.Ranking.route)

        },) { }
        //Botn para finalizar la aplicacion (salir)
        CustomElevatedButton("Salir",onClick = {
            (context as? Activity)?.finish()
        },) { }
    }
}

//Funcion reutilizable para crear botones con un estilo personalizado
@Composable
fun CustomElevatedButton(text: String, modifier : Modifier = Modifier, onClick: () -> Unit, function: () -> Unit) {
    val context = LocalContext.current
    ElevatedButton(
        onClick = {
            //Reproducir efecto de sonido al hacer pulsar boton
            (context as? MainActivity)?.playClickSound()
            //Ejecuta la acción del botón
            onClick()
        },
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(5.dp, MaterialTheme.colorScheme.secondary),
        colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary

        )
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Serif,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}


//Función reutilizable para crear un estilo de texto personalizado
@Composable
fun CustomText(text: String, sizeText:Int, modifier: Modifier = Modifier)  {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Serif,
            style = TextStyle(
                fontSize = sizeText.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .offset(1.dp, 1.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = text,
            fontFamily = FontFamily.Serif,
            style = TextStyle(
                fontSize = sizeText.sp,
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.offset(-1.dp, -1.dp),
            textAlign = TextAlign.Center,
        )
    }
}
