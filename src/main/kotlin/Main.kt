import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.*
import data.SnakeManager
import data.repository.GameRepository
import ui.navigation.NavigationGraph
import ui.screens.Game
import ui.screens.Menu
import ui.screens.ScoresScreen




fun main() = application {
    val gameRepository = GameRepository()
    val snakeManager =  remember { SnakeManager() }
    Window(
        title = "Snake",
        onCloseRequest = ::exitApplication
    ) {
        NavigationGraph(
        )
    }
}
