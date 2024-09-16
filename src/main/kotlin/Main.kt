import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.navigation.NavigationGraph


fun main() = application {
    Window(
        title = "Snake",
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            placement = WindowPlacement.Fullscreen
        )
    ) {
        NavigationGraph()
    }
}
