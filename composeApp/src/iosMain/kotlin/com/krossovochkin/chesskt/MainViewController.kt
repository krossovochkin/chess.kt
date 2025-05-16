package com.krossovochkin.chesskt

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(screenSizePx: Int): UIViewController {
    return ComposeUIViewController(
        configure = { enforceStrictPlistSanityCheck = false }
    ) {
        App(screenSizePx = screenSizePx)
    }
}
