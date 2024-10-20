package com.example.dadm_juego1_grupoa.Scenes

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dadm_juego1_grupoa.MainActivity
import com.example.dadm_juego1_grupoa.dataBase.AppDatabase
import com.example.dadm_juego1_grupoa.dataBase.Pregunta
import com.example.dadm_juego1_grupoa.ui.theme.DADM_juego1_GrupoATheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Función principal que gestiona el contenido del juego
@Composable
fun BodyContentGame(navController: NavController, playerName : String, category : String, difficulty : String, nQuestions : Int){

    //ESTILOS
    //Definición de los colores del degradado que se usará como fondo
    val colors = listOf( //Colores del degradado
        MaterialTheme.colorScheme.background, // Azul claro
        MaterialTheme.colorScheme.surface // Color rosado claro
    ) //Definicion del degradado para el fondo
    val brush = Brush.linearGradient(colors)

    //Variables para la gestión del acceso a la base de datos
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    //Variables mutables para manejar el estado del progreso del juego
    var questionsCompleted: Int by rememberSaveable { mutableStateOf(1) }
    var time: Int by rememberSaveable { mutableStateOf(30) }

    //Variables para mostrar estadisticas en la pantalla de resultados
    var correctAnswers: Int by rememberSaveable { mutableStateOf(0) }
    var points: Int by rememberSaveable { mutableStateOf(0) }
    var timePerQuestion: MutableList<Int> by remember { mutableStateOf(mutableListOf()) }

    // Variables para almacenar las preguntas y respuestas
    var questionsAndAnswers = remember { mutableStateOf(listOf<Pair<String, List<String>>>()) }
    var pointsPerQuestion: List<Int> by rememberSaveable { mutableStateOf(listOf()) }
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    var selectedAnswer by rememberSaveable { mutableStateOf<String?>(null) }
    var answerColor by remember { mutableStateOf(Color.White) }
    var areButtonsEnabled by rememberSaveable { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    //Lanzamos la consulta a la base de datos en un hilo secundario utilizando coroutines
    //Efecto lanzado para cargar preguntas desde la base de datos en segundo plano
    LaunchedEffect(difficulty) {
        CoroutineScope(Dispatchers.IO).launch {
            //Según la dificultad, se filtran las preguntas con un porcentaje ajustado

            var questionsDataBase: List<Pregunta> = when (difficulty) {
                //En dificultad facil, se muestran preguntas faciles
                "Fácil" -> database.preguntaDao()
                    //En dificultad media, se combinan preguntas medias y faciles
                    .obtenerPreguntasPorDificultad(category, "Fácil", nQuestions)
                "Media" -> database.preguntaDao()
                    .obtenerPreguntasPorDificultad(category, "Media", (nQuestions * 0.6).toInt()) +
                        database.preguntaDao().obtenerPreguntasPorDificultad(
                            category,
                            "Fácil",
                            (nQuestions * 0.4).toInt()
                        )
                //En dificultad difícil, se combinan preguntas dificiles y medias
                else -> database.preguntaDao().obtenerPreguntasPorDificultad(
                    category,
                    "Difícil",
                    (nQuestions * 0.6).toInt()
                ) +
                        database.preguntaDao().obtenerPreguntasPorDificultad(
                            category,
                            "Media",
                            (nQuestions * 0.4).toInt()
                        )
            }

            //Mapeo de las preguntas con sus respuestas correctas e incorrectas
            val qaList = questionsDataBase.map { question ->
                question.pregunta to listOf(question.respuestaC, question.respuestaI1, question.respuestaI2, question.respuestaI3)
            }

            //Recopilación de puntos por cada pregunta
            val pointsList: MutableList<Int> = mutableListOf()
            for (question in questionsDataBase){
                pointsList.add(question.puntos)
            }

            //Actualización del estado de las preguntas y los puntos en el hilo principal
            withContext(Dispatchers.Main) {
                questionsAndAnswers.value = qaList
                pointsPerQuestion = pointsList
            }
        }
    }

    //Un efecto (LaunchedEffect) controla el tiempo disponible para responder a cada pregunta
    //Si el jugador no responde a tiempo, se pasa automáticamente a la siguiente pregunta
    //El temporizador se reduce cada segundo hasta llegar a cero
    if (questionsAndAnswers.value.isNotEmpty()) {
        LaunchedEffect(currentQuestionIndex) {
            while (time > 0) {
                delay(1000L)
                if (selectedAnswer == null) {
                    time--
                }
            }
            questionsCompleted++
            if (currentQuestionIndex < questionsAndAnswers.value.lastIndex) {
                currentQuestionIndex++
            } else {
                //Navegación a la pantalla de resultados una vez completada la partida
                navController.navigate(Screen.Score.route+"/${playerName}/${nQuestions}/${correctAnswers}/${points}/${timePerQuestion.joinToString(",")}/${category}"){popUpTo(Screen.Game.route+"/{playerName}/{category}/{difficulty}/{nQuestions}"){inclusive = true} }
            }
            selectedAnswer = null
            answerColor = Color.White
            areButtonsEnabled = true
            timePerQuestion.add(30)
            time = 30
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Mostrar el progreso de las preguntas y los puntos obtenidos en la parte superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //Tarjeta que muestra la cantidad de preguntas completadas
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    border = BorderStroke(5.dp, Color.Black),
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "$questionsCompleted/$nQuestions",
                            style = TextStyle(fontSize = 40.sp),
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                        )
                    }
                }
                //Tarjeta que muestra los puntos obtenidos hasta el momento
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    border = BorderStroke(5.dp, Color.Black),
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "$points",
                                style = TextStyle(fontSize = 40.sp),
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                            )
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star Icon",
                                tint = Color.Yellow,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(40.dp)
                            )
                        }
                    }
                }
            }
            //Las preguntas y respuestas se cargan de la base de datos
            //Mostrar la pregunta actual y las respuestas correspondientes
            QACard(
                questionsAndAnswers.value[currentQuestionIndex].first,
                onClick = {},
                modifier = Modifier.weight(2f),
                color = MaterialTheme.colorScheme.primary
            )
            //Respuestas aleatorizadas para la pregunta actual
            val correctAnswer = questionsAndAnswers.value[currentQuestionIndex].second[0]
            val answers = remember(currentQuestionIndex) {
                questionsAndAnswers.value[currentQuestionIndex].second.shuffled()
            }
            //Mostrar cada respuesta como una tarjeta
            answers.forEach { answer ->
                QACard(
                    answer,
                    isAnswer = true,
                    onClick = {
                        if (areButtonsEnabled) {
                            selectedAnswer = answer
                            answerColor = if (answer == correctAnswer) Color.Green else Color.Red
                            areButtonsEnabled = false
                            timePerQuestion.add(30 - time)

                            //Reproducir sonido de respuesta correcta o incorrecta
                            if (answer == correctAnswer) {
                                (context as MainActivity).playCorrectAswer() // Asegúrate de que lo llamas desde la instancia correcta
                            } else {
                                (context as MainActivity).playbadAnswer()
                            }

                            coroutineScope.launch {
                                delay(2000L) //Pausa antes de avanzar a la siguiente pregunta
                                questionsCompleted++
                                if (answer == correctAnswer) {
                                    points += pointsPerQuestion[currentQuestionIndex] - (30-time)
                                    correctAnswers++
                                }

                                //Avanzar a la siguiente pregunta o navegar a la pantalla de resultados
                                if (currentQuestionIndex < questionsAndAnswers.value.lastIndex) {
                                    currentQuestionIndex++
                                } else {
                                    navController.navigate(Screen.Score.route+"/${playerName}/${nQuestions}/${correctAnswers}/${points}/${timePerQuestion.joinToString(",")}/${category}"){popUpTo(Screen.Game.route+"/{playerName}/{category}/{difficulty}/{nQuestions}"){inclusive = true} }
                                }
                                selectedAnswer = null
                                answerColor = Color.White
                                areButtonsEnabled = true
                                time = 30
                            }
                        }
                    },
                    //Cada respuesta se muestra como una tarjeta. El color de la tarjeta cambia dependiendo
                    //de si coincide con la respuesta seleccionada o no.
                    modifier = Modifier.weight(1f),
                    color = if (selectedAnswer == answer) answerColor else Color.White
                )
            }
            //Un espacio invisible para proporcionar espacio adicional entre las respuestas y el temporizador
            Spacer(modifier = Modifier.weight(1f))

            //Tarjeta que muestra el temporizador en la parte inferior de la pantalla.
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                border = BorderStroke(5.dp, Color.Black),
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                //Contenedor para alinear el contenido (el temporizador) dentro de la tarjeta
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    //Fila que contiene el ícono del reloj y el texto del temporizador
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        //Icono del reloj
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Clock Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(8.dp)
                        )
                        //Texto que muestra el tiempo restante
                        Text(
                            "$time",
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                            style = TextStyle(fontSize = 40.sp)
                        )
                    }
                }
            }
        }
    }
    //Si las preguntas aún no están cargadas, se muestra un mensaje de "Cargando preguntas..."
    else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Cargando preguntas...", style = TextStyle(fontSize = 24.sp))
        }
    }
}
//Función que define la tarjeta para mostrar preguntas y respuestas
@Composable
fun QACard(qa : String, modifier : Modifier = Modifier, isAnswer : Boolean = false, onClick: () -> Unit, color: Color){
    //Definición de la tarjeta con sus colores y bordes
    Card(colors = CardDefaults.cardColors(color), border = BorderStroke(5.dp, Color.Black), modifier = modifier.padding(vertical = 10.dp, horizontal = 4.dp)){
        //Contenedor para alinear el contenido dentro de la tarjeta
        Box(modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight()
            .clickable(enabled = onClick != {}, onClick = onClick), contentAlignment = Alignment.Center) {
            //Texto que muestra la pregunta o respuesta
            Text(
                text = "$qa", //El texto de la pregunta o respuesta
                style = TextStyle(
                    //// Ajusta el tamaño de la fuente dependiendo si es respuesta (más pequeño) o pregunta (más grande) y la longitud del texto
                    fontSize = if (isAnswer) (20-"$qa".length * 0.1).sp else (28-"$qa".length * 0.1).sp,
                    textAlign = TextAlign.Center //Alinea el texto al centro
                ),
                maxLines = Int.MAX_VALUE, //Permite un número máximo de líneas ilimitado
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}