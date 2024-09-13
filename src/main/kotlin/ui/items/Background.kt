package ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.theme.DarkOliveGreen2
import ui.theme.DarkOliveGreen7

@Composable
fun Background() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        for(rowIndex in 1 until 25) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                for(boxIndex in 1 until 25) {
                    val bgColor = if((rowIndex + boxIndex) % 2 == 0) DarkOliveGreen2 else DarkOliveGreen7
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
}