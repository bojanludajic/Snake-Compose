import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.random.Random


@Composable
@Preview
fun App() {
    val snakeManager: SnakeManager = remember { SnakeManager() }
    val directionModifier = Modifier
        .focusable()
        .onKeyEvent {
        when {
            (it.type == KeyEventType.KeyDown && it.key == Key.W) -> snakeManager.direction = 1
            (it.type == KeyEventType.KeyDown && it.key == Key.A) -> snakeManager.direction = 2
            (it.type == KeyEventType.KeyDown && it.key == Key.S) -> snakeManager.direction = 3
            (it.type == KeyEventType.KeyDown && it.key == Key.D) -> snakeManager.direction = 0

            else -> false
        }
        true
    }
    var showSnackBar by mutableStateOf(false)
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .then(directionModifier)
                .weight(5f)
        ) {
            for (rowIndex in 0 until 15) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for (boxIndex in 0 until 15) {
                        val bgColor: Color
                        when {
                            snakeManager.snakeList.value.first().first == rowIndex && snakeManager.snakeList.value.first().second == boxIndex -> bgColor = Color.White
                            snakeManager.snakeList.value.contains(Pair(rowIndex, boxIndex)) -> bgColor = Color.Black
                            boxIndex == snakeManager.appleX && rowIndex == snakeManager.appleY -> bgColor = Color.Red
                            else -> bgColor = when {
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.75f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if(snakeManager.direction != 0) snakeManager.direction = 2
                },
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text("^")
            }
            Row(

            ) {

                Button(
                    onClick = {
                       if(snakeManager.direction != 1)  snakeManager.direction = 3
                    },
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text("<")
                }
                Button(
                    onClick = {
                        if(snakeManager.direction != 3) snakeManager.direction = 1
                    },
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Text(">")
                }
            }
            Button(
                onClick = {
                    if(snakeManager.direction != 2) snakeManager.direction = 0
                },
                modifier = Modifier
                    .padding(top = 5.dp)
            ) {
                Text("v")
            }
            Text(
                text = "SCORE: ${snakeManager.size - 2}",
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(start = 40.dp)
            )
        }
    }

    if(snakeManager.gameOver) {
        GameEnd(snakeManager) {
            snakeManager.gameOver = false
            snakeManager.startMoving()
            snakeManager.resetTimer = 5
        }
    }
}

@Composable
fun GameEnd(
    snakeManager: SnakeManager,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .height(50.dp),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(Unit) {
            while(snakeManager.resetTimer > 0) {
                delay(1000)
                snakeManager.decrementTimer()
            }
            onDismiss()
        }
        Text(
            text = "GAME OVER! SCORE: ${snakeManager.size-2}  RESTARTING IN ${snakeManager.resetTimer} SECONDS"
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
