package com.krossovochkin.chesskt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalDensity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(screenSizePx = resources.displayMetrics.widthPixels)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
