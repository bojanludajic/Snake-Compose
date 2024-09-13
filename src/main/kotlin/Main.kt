import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.navigation.NavigationGraph


fun main() = application {
    Window(
        title = "Snake",
        onCloseRequest = ::exitApplication
    ) {
        NavigationGraph()
    }
}
