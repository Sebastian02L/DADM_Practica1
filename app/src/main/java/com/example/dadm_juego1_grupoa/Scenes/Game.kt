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
import com.example.dadm_juego1_grupoa.dataBase.AppDatabase
import com.example.dadm_juego1_grupoa.dataBase.Pregunta
import com.example.dadm_juego1_grupoa.ui.theme.DADM_juego1_GrupoATheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*@Preview
@Composable
fun Preview(){
    DADM_juego1_GrupoATheme {
        BodyContentGame()
    }
}*/

@Composable
fun BodyContentGame(navController: NavController, playerName : String, category : String, difficulty : String, nQuestions : Int){



    //ESTILOS
    val colors = listOf(
        MaterialTheme.colorScheme.background, // Azul claro
        MaterialTheme.colorScheme.surface // Color rosado claro
    ) // Colores del degradado
    val brush = Brush.linearGradient(colors)

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    var questionsCompleted: Int by rememberSaveable { mutableStateOf(1) }
    var correctAnswers: Int by rememberSaveable { mutableStateOf(0) }

    var points: Int by rememberSaveable { mutableStateOf(0) }
    var time: Int by rememberSaveable { mutableStateOf(30) }
    var timePerQuestion: MutableList<Int> by remember { mutableStateOf(mutableListOf()) }

    // Variables para almacenar las preguntas y respuestas
    var questionsAndAnswers = remember { mutableStateOf(listOf<Pair<String, List<String>>>()) }
    var pointsPerQuestion: List<Int> by rememberSaveable { mutableStateOf(listOf()) }
    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    var selectedAnswer by rememberSaveable { mutableStateOf<String?>(null) }
    var answerColor by remember { mutableStateOf(Color.White) }
    var areButtonsEnabled by rememberSaveable { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()

    // Lanzamos la consulta a la base de datos en un hilo secundario utilizando coroutines
    LaunchedEffect(difficulty) {
        CoroutineScope(Dispatchers.IO).launch {
            var questionsDataBase: List<Pregunta> = when (difficulty) {
                "Fácil" -> database.preguntaDao()
                    .obtenerPreguntasPorDificultad(category, "Fácil", nQuestions)

                "Media" -> database.preguntaDao()
                    .obtenerPreguntasPorDificultad(category, "Media", (nQuestions * 0.6).toInt()) +
                        database.preguntaDao().obtenerPreguntasPorDificultad(
                            category,
                            "Fácil",
                            (nQuestions * 0.4).toInt()
                        )

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

            val qaList = questionsDataBase.map { question ->
                question.pregunta to listOf(question.respuestaC, question.respuestaI1, question.respuestaI2, question.respuestaI3)
            }

            val pointsList: MutableList<Int> = mutableListOf()

            for (question in questionsDataBase){
                pointsList.add(question.puntos)
            }

            // Actualizamos el estado en el hilo principal
            withContext(Dispatchers.Main) {
                questionsAndAnswers.value = qaList
                pointsPerQuestion = pointsList
            }
        }
    }

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
                navController.navigate(Screen.Score.route+"/${playerName}/${nQuestions}/${correctAnswers}/${points}/${timePerQuestion.joinToString(",")}/${category}"){popUpTo(Screen.GameOptions.route){inclusive = true} }
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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

            QACard(
                questionsAndAnswers.value[currentQuestionIndex].first,
                onClick = {},
                modifier = Modifier.weight(1.5f),
                color = MaterialTheme.colorScheme.primary
            )

            val correctAnswer = questionsAndAnswers.value[currentQuestionIndex].second[0]
            val answers = remember(currentQuestionIndex) {
                questionsAndAnswers.value[currentQuestionIndex].second.shuffled()
            }
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

                            coroutineScope.launch {
                                delay(2000L)
                                questionsCompleted++
                                if (answer == correctAnswer) {
                                    points += pointsPerQuestion[currentQuestionIndex] - (30-time)
                                    correctAnswers++
                                }

                                if (currentQuestionIndex < questionsAndAnswers.value.lastIndex) {
                                    currentQuestionIndex++
                                } else {
                                    navController.navigate(Screen.Score.route+"/${playerName}/${nQuestions}/${correctAnswers}/${points}/${timePerQuestion.joinToString(",")}/${category}"){popUpTo(Screen.Game.route){inclusive = true} }
                                }
                                selectedAnswer = null
                                answerColor = Color.White
                                areButtonsEnabled = true
                                time = 30
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    color = if (selectedAnswer == answer) answerColor else Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                border = BorderStroke(5.dp, Color.Black),
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Clock Icon",
                            tint = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(8.dp)
                        )
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
    else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Cargando preguntas...", style = TextStyle(fontSize = 24.sp))
        }
    }
}

@Composable
fun QACard(qa : String, modifier : Modifier = Modifier, isAnswer : Boolean = false, onClick: () -> Unit, color: Color){
    Card(colors = CardDefaults.cardColors(color), border = BorderStroke(5.dp, Color.Black), modifier = modifier.padding(vertical = 10.dp, horizontal = 4.dp)){
        Box(modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight()
            .clickable(enabled = onClick != {}, onClick = onClick), contentAlignment = Alignment.Center) {
            Text(
                text = "$qa",
                style = TextStyle(
                    fontSize = if (isAnswer) (20-"$qa".length * 0.1).sp else (28-"$qa".length * 0.1).sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = Int.MAX_VALUE,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}