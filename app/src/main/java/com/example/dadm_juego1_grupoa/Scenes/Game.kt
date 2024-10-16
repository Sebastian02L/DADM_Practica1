package com.example.dadm_juego1_grupoa.Scenes

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dadm_juego1_grupoa.ui.theme.DADM_juego1_GrupoATheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun Preview(){
    DADM_juego1_GrupoATheme {
        BodyContentGame()
    }
}

@Composable
fun BodyContentGame(/*navController: NavController*/){
    val colors = listOf(Color(0xFF1F6D78), Color(0xFFFFFFFF)) // Colores del degradado
    val brush = Brush.sweepGradient(colors, Offset.Zero)

    val numberOfQuestions: Int = 20
    var questionsCompleted : Int by rememberSaveable{ mutableStateOf(1) }

    var points : Int by rememberSaveable { mutableStateOf(0) }
    var time : Int by rememberSaveable { mutableStateOf(30) }
    var timePerQuestion : MutableList<Int> by remember { mutableStateOf(mutableListOf()) }

    val questionsAndAnwers = listOf(
        "Pregunta 1" to listOf("Respuesta 1.1", "Respuesta 1.2", "Respuesta 1.3", "Respuesta correcta"),
        "Pregunta 2" to listOf("Respuesta correcta", "Respuesta 2.2", "Respuesta 2.3", "Respuesta 2.4")
    )

    var currentQuestionIndex by rememberSaveable { mutableStateOf(0) }
    var selectedAnswer by rememberSaveable { mutableStateOf<String?>(null) }

    var answerColor by remember { mutableStateOf(Color.White) }
    var areBottonsEnabled by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(currentQuestionIndex){
        while(time > 0){
            delay(1000L)
            if (selectedAnswer == null) {
                time--
            }
        }
        questionsCompleted++
        if(currentQuestionIndex < questionsAndAnwers.lastIndex){
            currentQuestionIndex++
        }else{
            currentQuestionIndex = 0
        }
        selectedAnswer = null
        answerColor = Color.White
        areBottonsEnabled = true
        timePerQuestion.add(30)
        time = 30
    }

    Column(modifier = Modifier.fillMaxSize().background(brush), horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.fillMaxWidth().padding(top = 60.dp, bottom = 40.dp), horizontalArrangement = Arrangement.SpaceBetween){
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), border = BorderStroke(5.dp, Color.Black), modifier = Modifier.padding(start = 5.dp)){
                Box(modifier = Modifier.fillMaxWidth(0.4f).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                    Text(
                        "$questionsCompleted/$numberOfQuestions",
                        style = TextStyle(fontSize = 40.sp),
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                    )
                }
            }
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), border = BorderStroke(5.dp, Color.Black), modifier = Modifier.padding(end = 5.dp)){
                Box(modifier = Modifier.fillMaxWidth(0.7f).background(MaterialTheme.colorScheme.primary)) {
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
                            modifier = Modifier.padding(end = 8.dp).size(40.dp)
                        )
                    }
                }
            }
        }

        QACard(questionsAndAnwers[currentQuestionIndex].first, onClick = {}, modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.primary)

        var answers = questionsAndAnwers[currentQuestionIndex].second
        answers.forEach{
            answer ->
            QACard(answer, isAnswer = true, onClick = {
                if (areBottonsEnabled){
                    selectedAnswer = answer
                    answerColor = if (answer == "Respuesta correcta") Color.Green else Color.Red
                    areBottonsEnabled = false
                    timePerQuestion.add(30 - time)

                    kotlinx.coroutines.GlobalScope.launch{
                        delay(2000L)
                        questionsCompleted++
                        if (answer == "Respuesta correcta"){
                            points += 100
                        }

                        if (currentQuestionIndex < questionsAndAnwers.lastIndex){
                            currentQuestionIndex++
                        }else{
                            currentQuestionIndex = 0
                        }
                        selectedAnswer = null
                        answerColor = Color.White
                        areBottonsEnabled = true
                        time = 30
                    }
                }
            }, modifier = Modifier.weight(1f), color = if (selectedAnswer == answer) answerColor else Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), border = BorderStroke(5.dp, Color.Black), modifier = Modifier.padding(bottom = 5.dp).align(Alignment.CenterHorizontally)){
            Box(modifier = Modifier.fillMaxWidth(0.5f).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
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

@Composable
fun QACard(qa : String, modifier : Modifier = Modifier, isAnswer : Boolean = false, onClick: () -> Unit, color: Color){
    Card(colors = CardDefaults.cardColors(color), border = BorderStroke(5.dp, Color.Black), modifier = modifier.padding(vertical = 8.dp, horizontal = 4.dp)){
        Box(modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight().clickable(enabled = onClick != {}, onClick = onClick), contentAlignment = Alignment.Center) {
            Text(
                "$qa",
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                style = TextStyle(fontSize = if (isAnswer) 20.sp else 40.sp)
            )
        }
    }
}