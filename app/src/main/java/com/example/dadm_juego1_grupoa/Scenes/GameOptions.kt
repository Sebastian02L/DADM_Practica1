package com.example.dadm_juego1_grupoa.Scenes

import android.graphics.Paint.Align
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.createFontFamilyResolver
import com.example.dadm_juego1_grupoa.dataBase.AppDatabase
import com.example.dadm_juego1_grupoa.dataBase.UserConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Variables globales donde se almacena la ultima configuracion utilizada
//Estas variables se pasan a la siguiente escena para realizar las consultas a la BD
var playerName by mutableStateOf("")
var selectedCategory by mutableStateOf("")
var selectedNumber by mutableStateOf(15)
var selectedDifficulty by mutableStateOf("Fácil")

var userConfiguration : UserConfig? = null

@Composable
fun BodyContentGameOptions(navController: NavController){

    LoadLastConfiguration()

    val colors = listOf(
        MaterialTheme.colorScheme.background, // Azul claro
        MaterialTheme.colorScheme.surface // Color rosado claro
    ) // Colores del degradado
    val brush = Brush.linearGradient(colors)


    //Variable que actualiza lo que escribe el jugador en el inputfield
    var playerNameInput by remember { mutableStateOf("") }

    //Columna que almacenará las tarjetas de la interfaz
    Column(modifier = Modifier.fillMaxSize()
        .background(brush)
        .padding(top = 40.dp), //Distancia a la zona superior de la pantalla
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally){

        //Fila con el boton para salir y titulo de la pantalla
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){
            //Boton de salir

            //Padding con lo que tiene arriba 
            CreateMainTitleCard("AJUSTES DE LA PARTIDA", Modifier.padding(start = 25.dp, end= 10.dp).weight(6f))
        }

        //Tarjeta de Seleccionar Categoria   //Padding con lo que tiene arriba y con lo que tiene debajo
        CreateTitleCard("Categoría de las Preguntas", Modifier.padding(top = 10.dp, bottom = 10.dp).weight(1f))

        //Card desplegable para seleccionar la categoria
        Dropdown()

        //Tarjeta de Cantidad de Preguntas
        CreateTitleCard("Cantidad de Preguntas", Modifier.padding( bottom = 10.dp).weight(1f))

        //Fila para seleccionar cantidad de preguntas
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center){
            CreateNumberButton(10)
            CreateNumberButton(15)
            CreateNumberButton(20)
        }

        //Tarjeta de seleccion de dificultad
        CreateTitleCard("Dificultad de las Preguntas", Modifier.padding( bottom = 10.dp).weight(1f))

        //Fila para seleccionar difficultad de las preguntas
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center){
            CreateStringButton("Fácil")
            CreateStringButton("Media")
            CreateStringButton("Díficil")
        }

        //Tarjeta de insertar nombre
        CreateTitleCard("Nombre del Jugador", Modifier.padding( bottom = 10.dp).weight(1f))
        TextField(value = playerNameInput,
            modifier = Modifier.padding(bottom = 20.dp),
            onValueChange = { playerNameInput = it
                playerName = playerNameInput},
            label = {Text( text = if(playerName == ""){ "Introduce tu nombre..." }
                                else { playerName}, color = Color.Black) })

        Row {
            ElevatedButton(
                onClick = { navController.navigate(Screen.MainMenu.route) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(60.dp) //
                    .border(
                        width = 5.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    )
                    .weight(0.3f)

            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "<",
                        fontFamily = FontFamily.Default,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }


            StartGameButton("¡Comenzar Partida!", navController, Modifier.weight(1.5f).padding(bottom = 40.dp))

        }
    }
}

@Composable
fun CreateMainTitleCard(title : String, modifier: Modifier = Modifier){
    OutlinedCard(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primary ),
        border = BorderStroke(5.dp, MaterialTheme.colorScheme.secondary),
        modifier = modifier.size(width = 250.dp, height = 50.dp),
        shape = MaterialTheme.shapes.medium
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        border = BorderStroke(5.dp, MaterialTheme.colorScheme.secondary),
        modifier = modifier.size(width = 270.dp, height = 50.dp),
        shape = MaterialTheme.shapes.medium
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

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón para mostrar el menú desplegable
        ElevatedButton(modifier = Modifier.size(width = 250.dp, height = 40.dp),
            onClick =  { expanded = true },
            colors = ButtonDefaults.buttonColors(Color.White)) {
            Text(text = if(selectedCategory == ""){"Pulsa para Seleccionar"}
                        else{ selectedCategory },
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
                    onClick = { // Actualiza la opción seleccionada
                        selectedCategory = option
                        expanded = false // Cierra el menú después de seleccionar
                    }
                )
            }
        }
    }
}

@Composable
fun StartGameButton(text : String, navController: NavController, modifier: Modifier = Modifier){
    //Variable que refleja si la configuracion de la partida es valida y puede avanzar a la siguiente escena
    var allValuesCorrect by remember { mutableStateOf(false)}
    allValuesCorrect = CheckNoDefaultValues()
    var canQuery by remember { mutableStateOf(false)}

    ElevatedButton(modifier = modifier.size(width = 250.dp, height = 50.dp).padding(horizontal = 5.dp),
        onClick =  {
            if(allValuesCorrect){
                canQuery = true
                navController.navigate(Screen.Game.route+"/${playerName}/${selectedCategory}/${selectedDifficulty}/${selectedNumber}"){popUpTo(Screen.GameOptions.route){inclusive = true} }}},
        colors = ButtonDefaults.buttonColors(Color.White)) {
        Text(text = "${text}",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold, color = Color.Black))
    }

    //Si podemos empezar la partida, guardamos en la base de datos la configuracion usada
    //Esto es para que solo haga la query cuando los datos de configuracion tienen valores correctos y se pulsa el boton
    if(canQuery) SaveLastConfiguration()
}

//Funcion que comprueba que los datos de la configuacion de la partida esten correctos
fun CheckNoDefaultValues(): Boolean{
   return playerName != "" && selectedDifficulty != "" && selectedNumber != 0 && selectedCategory != ""
}

//Funcion que pide a la base de datos la ultima configuracion de partida utilizada
@Composable
fun LoadLastConfiguration() {
        val context = LocalContext.current
        val database = AppDatabase.getDatabase(context)

        //Mediante una corutina hacemos la peticion, si la tabla esta vacia capturamos la excepcion, si la tabla tiene
        //datos guardados, los extraemos y los guardamos en la variable userConfiguration
        LaunchedEffect(Unit) {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val configuration: UserConfig? = database.userConfigDao().obtenerUserConfig()

                    configuration?.let {
                        playerName = configuration.nombre
                        selectedCategory = configuration.categoria
                        selectedNumber = configuration.numPreguntas
                        selectedDifficulty = configuration.dificultad
                    }
                    //Indicamos que la s intrucciones dentro del bloque {} lo ejecute el hilo principal
                    withContext(Dispatchers.Main) {
                        userConfiguration = configuration
                    }
                }
            } catch (e: Exception){}
        }
}

//Funcion que guarda la ultima configuracion de la partida en la base de datos
@Composable
fun SaveLastConfiguration(){
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    //Mediante una corutina creamos un objeto UserConfig, limpiamos la tabla y guardamos la nueva fila
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch { //Dispatchers.IO nos garantiza que no se bloquee el hilo principal con operaciones IO
            var configuration = UserConfig(
                nombre = playerName,
                categoria = selectedCategory,
                numPreguntas = selectedNumber,
                dificultad = selectedDifficulty
            )
            database.userConfigDao().deleteLastConfiguration()
            database.userConfigDao().insertarUserConfig(configuration)
        }
    }
}

/*@Preview
@Composable
fun prev(){
    BodyContentGameOptions()
}*/
