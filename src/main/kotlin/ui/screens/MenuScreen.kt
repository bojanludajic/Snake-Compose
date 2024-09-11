package ui.screens

import ui.theme.DarkOliveGreen2
import ui.theme.DarkOliveGreen7
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Menu(
    onAccept: () -> Unit
) {
    var tfText by remember { mutableStateOf("") }
    val MAX_NAME_LENGTH = 20

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            for(rowIndex in 1 until 15) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for(boxIndex in 1 until 15) {
                        val bgColor = if((rowIndex + boxIndex) % 2 == 0) DarkOliveGreen2 else DarkOliveGreen7
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(bgColor)
                                .fillMaxHeight()
                        ) {
                        }
                    }
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            TextField(
                value = tfText,
                onValueChange = { newValue ->
                    if (newValue.length <= MAX_NAME_LENGTH || newValue.length < tfText.length) {
                        tfText = newValue
                    }
                    println(tfText)
                },
                modifier = Modifier
                    .background(Color.White),
                textStyle = TextStyle(textAlign = TextAlign.Center),
                maxLines = 1,
                label = { Text(
                    text = "Name:"
                ) }
            )
            Button(
                onClick = { if(tfText != "") onAccept() }
            ) {
                Text(
                    text = "PLAY"
                )
            }
        }

    }
}