package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.model.GameData
import data.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ui.items.Background

@Composable
fun ScoresScreen(
 onNavigate: (String) -> Unit
) {
    val gameRepository: GameRepository = GameRepository()
    var gameDataList = gameRepository.getGames()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Background()
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