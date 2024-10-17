package com.example.dadm_juego1_grupoa.Scenes

import android.graphics.Paint.Align
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.createFontFamilyResolver

//Variables globales donde se almacena la ultima configuracion utilizada
//Estas variables se pasan a la siguiente escena para realizar las consultas a la BD
var playerName by mutableStateOf("")
var selectedCategory by mutableStateOf("")
var selectedNumber by mutableStateOf(0)
var selectedDifficulty by mutableStateOf("")

@Composable
fun BodyContentGameOptions(navController: NavController){

    val colors = listOf(Color(0xFF1F6D78), Color(0xFFFFFFFF)) // Colores del degradado
    val brush = Brush.sweepGradient(colors, Offset.Zero)
    //Variable que actualiza lo que escribe el jugador en el inputfield
    var playerNameInput by remember { mutableStateOf("") }

    //Columna que almacenará las tarjetas de la interfaz
    Column(modifier = Modifier.fillMaxSize()
        .background(brush)
        .padding(top = 25.dp), //Distancia a la zona superior de la pantalla
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){

        //Fila con el boton para salir y titulo de la pantalla
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            //Boton de salir
            ElevatedButton(
                onClick ={ navController.navigate(Screen.MainMenu.route)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier.size(50.dp)
            ){}
            //Padding con lo que tiene arriba 
            CreateMainTitleCard("Ajustes de la Partida", Modifier.padding(start = 25.dp))
        }

        //Tarjeta de Seleccionar Categoria   //Padding con lo que tiene arriba y con lo que tiene debajo
        CreateTitleCard("Categoría de las Preguntas", Modifier.padding(top = 35.dp, bottom = 10.dp))

        //Card desplegable para seleccionar la categoria
        Dropdown()

        //Tarjeta de Cantidad de Preguntas
        CreateTitleCard("Cantidad de Preguntas", Modifier.padding(top = 35.dp, bottom = 10.dp))

        //Fila para seleccionar cantidad de preguntas
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.Center){
            CreateNumberButton(10)
            CreateNumberButton(15)
            CreateNumberButton(20)
        }

        //Tarjeta de seleccion de dificultad
        CreateTitleCard("Dificultad de las Preguntas", Modifier.padding(top = 35.dp, bottom = 10.dp))

        //Fila para seleccionar difficultad de las preguntas
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.Center){
            CreateStringButton("Fácil")
            CreateStringButton("Media")
            CreateStringButton("Díficil")
        }

        //Tarjeta de insertar nombre
        CreateTitleCard("Nombre del Jugador", Modifier.padding(top = 35.dp, bottom = 10.dp))
        TextField(value = playerNameInput,
            modifier = Modifier.padding(bottom = 15.dp),
            onValueChange = { playerNameInput = it
                playerName = playerNameInput},
            label = {Text( text = if(playerName == ""){ "Introduce tu nombre..." }
                                else { playerName}, color = Color.Black) })

        StartGameButton("¡Comenzar Partida!", navController)
    }
}

@Composable
fun CreateMainTitleCard(title : String, modifier: Modifier = Modifier){
    OutlinedCard(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface ),
        border = BorderStroke(5.dp, Color.Black),
        modifier = modifier.size(width = 250.dp, height = 50.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        //Contenedor para poder alinear el text dentro de la tarjeta
        Box(
            modifier = Modifier.fillMaxSize(), // El Box ocupa todo el tamaño de la Card
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${title}",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun CreateTitleCard(title : String, modifier : Modifier = Modifier){
    OutlinedCard(
        colors = CardDefaults.cardColors(Color.White,),
        border = BorderStroke(5.dp, Color.Black),
        modifier = modifier.size(width = 270.dp, height = 50.dp),
        shape = RoundedCornerShape(30.dp)
    ) {
        //Contenedor para poder alinear el text dentro de la tarjeta
        Box(
            modifier = Modifier.fillMaxSize(), // El Box ocupa todo el tamaño de la Card
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${title}",
                modifier = Modifier,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun CreateNumberButton(number : Int){
    ElevatedButton(modifier = Modifier.size(width = 80.dp, height = 50.dp).padding(horizontal = 5.dp),
        onClick =  { selectedNumber = number },
        colors = if(selectedNumber != number) { ButtonDefaults.buttonColors(Color.White) }
                else{ ButtonDefaults.buttonColors(Color.LightGray) }) {

        Text(text = "${number}",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = Color.Black))
    }
}

@Composable
fun CreateStringButton(text : String){
    ElevatedButton(modifier = Modifier.size(width = 110.dp, height = 60.dp).padding(horizontal = 5.dp),
        onClick =  { selectedDifficulty = text },
        colors = if(selectedDifficulty != text){ ButtonDefaults.buttonColors(Color.White) }
                else{ButtonDefaults.buttonColors(Color.LightGray)}) {
        Text(text = "${text}",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold, color = Color.Black))
    }
}

@Composable
fun Dropdown() {
    // Estado para el menú desplegable
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(
        if(selectedCategory == ""){"Pulsa para seleccionar una categoria"}
        else{ selectedCategory }
    )}

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón para mostrar el menú desplegable
        ElevatedButton(modifier = Modifier.size(width = 250.dp, height = 40.dp),
            onClick =  { expanded = true },
            colors = ButtonDefaults.buttonColors(Color.White)) {
            Text(text = selectedOption,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = Color.Black))
        }
        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre el botón y el menú
        // Menú desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Cierra el menú si se hace clic fuera
        ) {
            // Opciones del menú
            val options = listOf("Entretenimiento", "Cultura General")

            for (option in options) {
                DropdownMenuItem(
                    text = { Text(text = option) }, // Cambiar el uso aquí
                    onClick = {
                        selectedOption = option // Actualiza la opción seleccionada
                        if(option == "Cultura General"){
                            selectedCategory = "CulturaGeneral"
                        }else {
                            selectedCategory = selectedOption
                        }
                        expanded = false // Cierra el menú después de seleccionar
                    }
                )
            }
        }
    }
}

@Composable
fun StartGameButton(text : String, navController: NavController){
    ElevatedButton(modifier = Modifier.size(width = 250.dp, height = 50.dp).padding(horizontal = 5.dp),
        onClick =  {
            if(selectedCategory != "" && selectedDifficulty != "" && selectedNumber != 0 && playerName != ""){
                navController.navigate(Screen.Game.route+"/${playerName}/${selectedCategory}/${selectedDifficulty}/${selectedNumber}")}},
        colors = ButtonDefaults.buttonColors(Color.White)) {
        Text(text = "${text}",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold, color = Color.Black))
    }
}

/*@Preview
@Composable
fun prev(){
    BodyContentGameOptions()
}*/
