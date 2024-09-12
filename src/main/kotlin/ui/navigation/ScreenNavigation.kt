package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import data.SnakeManager
import data.repository.GameRepository
import ui.screens.Game
import ui.screens.Menu
import ui.screens.ScoresScreen

@Composable
fun NavigationGraph(
) {
    var currentScreen = remember { mutableStateOf("Menu") }
    val snakeManager =  remember { SnakeManager() }
    val gameRepository = GameRepository()

    when(currentScreen.value) {
        "Menu" -> Menu(
            snakeManager,
            gameRepository
        ) { currentScreen.value = it }
        "Game" -> Game(
            snakeManager,
        )
        "Scores" -> ScoresScreen(
            snakeManager
        ) { currentScreen.value = it }
    }
}