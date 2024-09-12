package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.SnakeManager
import data.model.GameData
import data.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ui.theme.DarkOliveGreen2
import ui.theme.DarkOliveGreen7

@Composable
fun ScoresScreen(
 snakeManager: SnakeManager,
 onNavigate: (String) -> Unit
) {
    val gameRepository: GameRepository = GameRepository()
    var gameDataList by remember { mutableStateOf(emptyList<GameData>()) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            gameDataList = gameRepository.getGames()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            for (rowIndex in 1 until 15) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    for (boxIndex in 1 until 15) {
                        val bgColor = if ((rowIndex + boxIndex) % 2 == 0) DarkOliveGreen2 else DarkOliveGreen7
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
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        onNavigate("Menu")
                    },
                    modifier = Modifier
                        .padding(
                            end = 215.dp,
                            top = 3.dp,
                            start = 5.dp
                        )
                ) {
                    Text("Back")
                }
                Text(
                    text = "Top Scores",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
            for(game in gameDataList
                .sortedByDescending{ it.score }
                .take(7)
            ) {
                Text(
                    text = "${game.userName}      ${game.score}",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}