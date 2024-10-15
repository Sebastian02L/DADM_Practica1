package com.example.dadm_juego1_grupoa.Scenes

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
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
fun BodyContentRanking(navController: NavController){
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

        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
            modifier = Modifier.padding(vertical=4.dp, horizontal = 8.dp)
        ){
            CardContent("tipo", modifier = Modifier)

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



    /*val buttonPadding by animateDpAsState (
            if(expanded.value)48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioHighBouncy,
                stiffness = Spring.StiffnessLow
            )
        ); //para animación
        Surface (color = MaterialTheme.colorScheme.primary ,
            modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        {

     */
    Row (modifier=Modifier.padding(12.dp)
        .animateContentSize(animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow))
    )
    {
        Column(modifier = modifier.weight(1f)
            //.padding(bottom = buttonPadding)
            .padding((12.dp))
        ){
            Text(
                text = "Hello $name !",
                modifier = modifier
            )
            Text(text = name,
                style = MaterialTheme.typography.headlineMedium, //modificacion del materialTheme
            )
            if (expanded.value) {
                Text(text = "texto composicion colores, " + "Con mas texto de tema")
            }


        }
        /* ElevatedButton(onClick = { expanded.value = !expanded.value } )
         {
             Text(if(!expanded.value) "Soy Un Botón Inutil" else "Sigo sin hacer mucho")
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

/*@Preview(showBackground = true)
@Composable
fun RankingPrev() {
    DADM_juego1_GrupoATheme {
        BodyContentRanking()
    }
}*/