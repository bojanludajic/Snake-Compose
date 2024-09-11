
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.*
import ui.screens.Game
import ui.screens.Menu


@Composable
fun App() {
    var gameScreen by remember { mutableStateOf("Menu") }

    when(gameScreen) {
        "Menu" -> Menu() { gameScreen = "Game" }
        "Game" -> Game()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
