package com.example.dadm_juego1_grupoa.Scenes

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import androidx.compose.material3.Surface
import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

//import androidx.compose.material.icons.filled.ExpandMore
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
fun BodyContentRanking(navController: NavController,
                       categorias : List<String> = listOf( "Entretenimiento", "Cultura General"))
{
    //ESTILOS
    val colors = listOf(Color(0xFF1F6D78), Color(0xFFFFFFFF)) // Colores del degradado
    val brush = Brush.sweepGradient(colors, Offset.Zero)

    //VARIABLES
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush), // Color degradado fondo
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp) // Añadir padding al Row si es necesario
        ) {
            //Icono de corona delantero
            Icon(
                //painter = painterResource(id = R.drawable.ic_bar_chart), // Reemplaza con tu recurso de ícono
                painter = painterResource(id = R.drawable.chess_queen), // Reemplaza con tu recurso de ícono
                contentDescription = "Crown",
                tint = MaterialTheme.colorScheme.primary, // Aplica el color del tema
                modifier = Modifier.size(50.dp) // Ajusta el tamaño según sea necesario
                //    .align(Alignment.CenterVertically)
            )

            //Titulo del juego
            Text(
                text = "RANKING",
                style = TextStyle(
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold,
                ),
                color = Color(0xFFE8FA22),
                //modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
            )

            //Icono corona final
            Icon(
                //painter = painterResource(id = R.drawable.ic_bar_chart), // Reemplaza con tu recurso de ícono
                painter = painterResource(id = R.drawable.chess_queen), // Reemplaza con tu recurso de ícono
                contentDescription = "Crown",
                tint = MaterialTheme.colorScheme.primary, // Aplica el color del tema
                modifier = Modifier.size(50.dp) // Ajusta el tamaño según sea necesario
                //    .align(Alignment.CenterVertically)
            )
        }

        //TARJETAS PARA LOS RESULTADOS (2 tarjetas deplegables con 3 puestos)
        Card(colors = CardDefaults.cardColors(
            containerColor =  Color.Transparent
        ),
            modifier = Modifier.padding(vertical=0.dp, horizontal = 0.dp
            )

        ){
            for(cat in categorias){
                CardContent( name = cat, modifier=Modifier)

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
private fun CardContent(name:String, modifier: Modifier)
{
    //var expanded = false;//es local, por lo que cada vez que la interfaz cambia(vuelve a componer) se reinicia esta var
    //var expanded = remember{mutableStateOf(false)};//con esta funcion, se guarda el estado
    var expanded = rememberSaveable(){mutableStateOf(false)};//con esta funcion, se guarda el estado
    //val buttonPadding = if(expanded.value)48.dp else 0.dp;
    val rotationAnimation = remember { Animatable(0f) }///////

    ///////////////////////***************////////////////////////////
    // Un efecto de lanzamiento que aplica una animación de sacudida
    LaunchedEffect(expanded.value) {
        if (!expanded.value) {
            while (true) {
                rotationAnimation.animateTo(
                    targetValue = 5f,
                    animationSpec = keyframes {
                        durationMillis = 600
                        0f at 0
                        5f at 200
                        -5f at 400
                        0f at 600
                    }
                )
                delay(2000)  // Tiempo antes de volver a iniciar la rotación
            }
        } else {
            // Detiene la animación cuando se expande
            rotationAnimation.stop()
            rotationAnimation.snapTo(0f)  // Asegúrate de estar en la posición original
        }
    }
    ///////////////////////***************////////////////////////////


    Surface (color = MaterialTheme.colorScheme.primary ,
            shape = RoundedCornerShape(16.dp),
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        {


    Row (modifier=Modifier.padding(12.dp)
        .animateContentSize(animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow))
        .clickable(onClick = { expanded.value = !expanded.value })
        .graphicsLayer { rotationZ = rotationAnimation.value } /////////////////////

    )
    {
        Column(
            modifier = modifier.weight(1f)
                //.padding(bottom = buttonPadding)
                .padding((12.dp)),

            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium, //modificacion del materialTheme

            )
            //VALORES DE LOS TRES MEJORES RESULTADOS
            if (expanded.value)
            {
                //Values de entretenimiento
                if(name == "Entretenimiento"){
                    //COGER MEJORES RESULTADOS DE QUERY
                    //*************************************************//
                    Text(text = "Result 1 ")
                    Text(text = "Result 2 ")
                    Text(text = "Result 3 ")
                }
                //Values de Cultura general
                else if(name == "Cultura General")
                {
                    //COGER MEJORES RESULTADOS DE QUERY
                    //*************************************************//
                    Text(text = "Result 4 ")
                    Text(text = "Result 5 ")
                    Text(text = "Result 6 ")
                }

            }


        }

       /* ElevatedButton(onClick = { expanded.value = !expanded.value })
        {
            Text(if (!expanded.value) "Abrir Resultados" else "Cerrar Resultados")
        }*/
        /*IconButton(onClick = {expanded.value = !expanded.value })
        {
            Icon(
                imageVector = if(expanded.value)Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if(expanded.value) {
                    stringResource((R.string.showLess))
                }else
                    stringResource((R.string.showMore))

            )
        }*/
    }
    }
}

/*@Preview(showBackground = true)
@Composable
fun RankingPrev() {
    DADM_juego1_GrupoATheme {
        BodyContentRanking()
    }
}*/