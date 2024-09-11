import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlin.random.Random




@Composable
@Preview
fun App() {
    val snakeManager: SnakeManager = remember { SnakeManager() }
//    BasicTextField(
//        value = "",
//        onValueChange = {},
//        modifier = Modifier
//
//    )
    var focusRequester = remember { FocusRequester() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .onKeyEvent {
                when {
                    (it.type == KeyEventType.KeyDown && it.key == Key.W) -> {
                        if(snakeManager.direction != 2) snakeManager.direction = 0
                        true
                    }
                    (it.type == KeyEventType.KeyDown && it.key == Key.A) -> {
                        if(snakeManager.direction != 3) snakeManager.direction = 1
                        true
                    }
                    (it.type == KeyEventType.KeyDown && it.key == Key.S) -> {
                        if(snakeManager.direction != 0) snakeManager.direction = 2
                        true
                    }
                    (it.type == KeyEventType.KeyDown && it.key == Key.D) -> {
                        if(snakeManager.direction != 1) snakeManager.direction = 3
                        true
                    }
                    else -> false
                }
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
                    // .then(directionModifier)
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
                                snakeManager.snakeList.value.first().first == boxIndex && snakeManager.snakeList.value.first().second == rowIndex -> bgColor =
                                    RoyalPurple

                                snakeManager.snakeList.value.contains(Pair(boxIndex, rowIndex)) -> bgColor = DarkPurple
                                boxIndex == snakeManager.appleX && rowIndex == snakeManager.appleY -> bgColor =
                                    Color.Red

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
