package com.krossovochkin.chesskt

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlin.math.min
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    val body = document.body!!
    ComposeViewport(body) {
        App(screenSizePx = min(body.clientWidth, body.clientHeight))
    }
}
