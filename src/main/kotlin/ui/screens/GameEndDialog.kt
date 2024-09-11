package ui.screens

import data.SnakeManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun GameEndDialog(
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