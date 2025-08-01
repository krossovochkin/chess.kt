package com.krossovochkin.chesskt

import androidx.compose.runtime.*
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.math.min
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.w3c.dom.HTMLElement

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val size = remember { getContainerSize() }

        if (size != null) {
            App(screenSizePx = size)
        } else {
            Text("Loading...")
        }
    }
}

private fun getContainerSize(): Int? {
    val iframe = windowWithFrameElement.frameElement
    return if (iframe != null) {
        min(iframe.offsetWidth, iframe.offsetHeight)
    } else {
        min(window.innerWidth, window.innerHeight)
    }.takeIf { it > 0 }
}

external interface WindowWithFrameElement {
    val frameElement: HTMLElement?
}

val windowWithFrameElement: WindowWithFrameElement = js("window")
