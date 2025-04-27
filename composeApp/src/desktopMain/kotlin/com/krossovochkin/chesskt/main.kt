package com.krossovochkin.chesskt

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication(
    state = WindowState(width = 512.dp, height = 512.dp),
    resizable = false,
    undecorated = true,
    title = "chess.kt"
) {
    App()
}