package data

import androidx.compose.runtime.*
import data.repository.GameRepository
import kotlinx.coroutines.*
import kotlin.random.Random

class SnakeManager() {

    val gameRepository by mutableStateOf(GameRepository())
    var size by mutableStateOf(3)
    var HEADX by mutableStateOf(7)
    var HEADY by mutableStateOf(7)
    var direction by mutableStateOf(3)
    val directions = arrayOf(
        intArrayOf(0,-1),
        intArrayOf(-1,0),
        intArrayOf(0,1),
        intArrayOf(1,0)
    )
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var job: Job? = null
    var appleX by mutableStateOf(Random.nextInt(30))
    var appleY by mutableStateOf(Random.nextInt(30))
    var snakeList = mutableStateOf(listOf(Pair(7,9),Pair(7,8),Pair(7,7)))
    var gameOver by mutableStateOf(false)
    var resetTimer by mutableStateOf(5)
    var currentPlayer: Int by mutableStateOf(0)

    fun stop() {
        job?.cancel()
    }

    fun startMoving() {
        size = 3
        appleX = Random.nextInt(25)
        appleY = Random.nextInt(25)
        snakeList.value = listOf(Pair(7,9),Pair(7,8),Pair(7,7))
        HEADX = 7
        HEADY = 7
        direction = 2
        job = scope.launch {
            while(true) {
                delay(150)
                HEADX = (snakeList.value.first().first + directions[direction][0] + 25) % 25;
                HEADY = (snakeList.value.first().second + directions[direction][1] + 25) % 25;
                if(snakeList.value.contains(Pair(HEADX,HEADY))) {
                    gameOver = true
                    gameRepository.insertNewScore(currentPlayer,size - 2)
                    stop()
                }
                if(HEADX == appleX && HEADY == appleY) {
                    size++;
                    appleX = Random.nextInt(25)
                    appleY = Random.nextInt(25)
                    while(snakeList.value.find { it == Pair(appleX,appleY) } == Pair(appleX,appleY) ) {
                        appleX = Random.nextInt(25)
                        appleY = Random.nextInt(25)
                    }

                }
                snakeList.value = listOf(Pair(HEADX,HEADY)) + snakeList.value.take(size - 1)
            }
        }
    }

    fun decrementTimer() {
        resetTimer--
    }

    fun oppositeDirection(dir: Int): Int {
        return when(dir) {
            0 -> 2
            1 -> 3
            2 -> 0
            3 -> 1
            else -> throw IllegalArgumentException("Invalid direction!")
        }
    }



}