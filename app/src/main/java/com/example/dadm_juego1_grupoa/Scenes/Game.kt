package com.example.dadm_juego1_grupoa.Scenes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.dadm_juego1_grupoa.ui.theme.DADM_juego1_GrupoATheme

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
    var questionsCompleted : Int by rememberSaveable{ mutableStateOf(0) }

    var points : Int by rememberSaveable { mutableStateOf(5000) }
    var time : Int by rememberSaveable { mutableStateOf(32) }

    var question : String by rememberSaveable { mutableStateOf("Pregunta 1") }

    Column(modifier = Modifier.fillMaxSize().background(brush), horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.fillMaxWidth().padding(top = 60.dp, bottom = 40.dp), horizontalArrangement = Arrangement.SpaceBetween){
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), modifier = Modifier.padding(start = 5.dp)){
                Text("$questionsCompleted/$numberOfQuestions", style = TextStyle(fontSize = 40.sp), modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp))
            }
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), modifier = Modifier.padding(end = 5.dp)){
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

        QuestionAnswerCards(modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.weight(1f))

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), modifier = Modifier.padding(bottom = 5.dp).align(Alignment.CenterHorizontally)){
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
fun QuestionAnswerCards(modifier : Modifier = Modifier, question : String = "Pregunta 1", answers : List<String> = listOf("Respuesta 1", "Respuesta 2", "Respuesta 3", "Respuesta 4")){
    QACard(question, modifier)
    for (answer in answers){
        QACard(answer, modifier)
    }
}

@Composable
fun QACard(qa : String, modifier : Modifier = Modifier){
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), modifier = modifier.padding(vertical = 8.dp, horizontal = 4.dp)){
        Box(modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight().background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
            Text(
                "$qa",
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp),
                style = TextStyle(fontSize = 40.sp)
            )
        }
    }
}