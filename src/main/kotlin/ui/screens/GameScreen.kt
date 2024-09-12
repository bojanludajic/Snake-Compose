package ui.screens

import ui.theme.DarkOliveGreen2
import ui.theme.DarkOliveGreen7
import ui.theme.DarkPurple
import ui.theme.RoyalPurple
import data.SnakeManager
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun Game(

) {
    val snakeManager: SnakeManager = remember { SnakeManager() }
    val focusRequester = remember { FocusRequester() }
    var lastDirectionChange by remember { mutableStateOf(0L) }
    val debounceDelay = 75L
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .onKeyEvent {
                val currentTime = System.currentTimeMillis()
                if(currentTime - lastDirectionChange > debounceDelay) {
                    val newDirection = when {
                        it.type == KeyEventType.KeyDown && it.key == Key.W -> 0
                        it.type == KeyEventType.KeyDown && it.key == Key.A -> 1
                        it.type == KeyEventType.KeyDown && it.key == Key.S -> 2
                        it.type == KeyEventType.KeyDown && it.key == Key.D -> 3
                        else -> null
                    }
                    newDirection?.let { newDir ->
                        if(snakeManager.direction != snakeManager.oppositeDirection(newDir)) {
                            snakeManager.direction = newDir
                            lastDirectionChange = currentTime
                        }

                    }
                }
                true
            }
            .focusable()
    ) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
            ) {
                for (rowIndex in 0 until 15) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        for (boxIndex in 0 until 15) {
                            val bgColor = when {
                                snakeManager.snakeList.value.first().first == boxIndex && snakeManager.snakeList.value.first().second == rowIndex -> RoyalPurple
                                snakeManager.snakeList.value.contains(Pair(boxIndex, rowIndex)) -> DarkPurple
                                boxIndex == snakeManager.appleX && rowIndex == snakeManager.appleY -> Color.Red

                                else -> when {
                                    (boxIndex + rowIndex) % 2 == 0 -> DarkOliveGreen2
                                    else -> DarkOliveGreen7
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .background(bgColor)
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {
                                Text("")
                            }
                        }
                    }
                }
            }
        }

        Text(
            text = if(!snakeManager.gameOver) "SCORE: ${snakeManager.size - 2}" else "",
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .padding(8.dp),
            color = Color.White,
        )
    }
    if (snakeManager.gameOver) {
        GameEndDialog(snakeManager) {
            snakeManager.gameOver = false
            snakeManager.startMoving()
            snakeManager.resetTimer = 5
        }
    }
}

