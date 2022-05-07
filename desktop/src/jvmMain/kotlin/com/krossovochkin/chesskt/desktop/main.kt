package com.krossovochkin.chesskt.desktop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.krossovochkin.chess.Game
import com.krossovochkin.chess.Move
import com.krossovochkin.chess.Piece
import com.krossovochkin.chess.Square
import kotlin.math.roundToInt

private data class Config(
    val theme: BoardTheme,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val cellSize: Dp,
    val pieceSize: Dp,
) {
    data class BoardTheme(
        val lightColor: Color,
        val darkColor: Color,
    )
}


fun main() = application {
    val config = remember {
        Config(
            theme = Config.BoardTheme(
                lightColor = Color.Gray,
                darkColor = Color.DarkGray,
            ),
            screenWidth = 528.dp,
            screenHeight = 551.dp,
            cellSize = 64.dp,
            pieceSize = 60.dp,
        )
    }
    Window(
        state = rememberWindowState(width = config.screenWidth, height = config.screenHeight),
        onCloseRequest = ::exitApplication,
        title = "chess.kt",
    ) {
        Game(config = config)
    }
}

@Composable
private fun Game(config: Config) {
    fun Game.getPieces(): Map<Square, Piece> {
        val map = mutableMapOf<Square, Piece>()
        for (rank in 0 until 8) {
            for (file in 0 until 8) {
                val square = Square(file = file, rank = rank)
                val piece = this.getPiece(square)
                if (piece != null) {
                    map[square] = piece
                }
            }
        }
        return map
    }

    val game = remember { Game.create()!! }
    var pieces: Map<Square, Piece> by remember { mutableStateOf(game.getPieces()) }
    var pendingMoveData: MoveData? by remember { mutableStateOf(null) }

    fun invokeMove(moveData: MoveData) {
        if (moveData.requiresPromotion) {
            pendingMoveData = moveData
        } else {
            pendingMoveData = null
            game.move(
                with(moveData) {
                    Move.create(
                        piece = piece,
                        from = fromSquare,
                        to = toSquare,
                        promotionPiece = promotionPiece,
                    )
                }
            ).also { isMoved -> if (isMoved) pieces = game.getPieces() }
        }
    }

    Board(config = config)
    Pieces(
        config = config,
        pieces = pieces,
        onMove = { piece, fromSquare, toSquare ->
            invokeMove(MoveData(piece, fromSquare, toSquare, null))
        }
    )
    PromotionSelector(pendingMoveData) { invokeMove(it) }
}

private data class MoveData(
    val piece: Piece,
    val fromSquare: Square,
    val toSquare: Square,
    val promotionPiece: Piece.Type?
) {
    val requiresPromotion: Boolean
        get() {
            if (piece.type == Piece.Type.Pawn) {
                val promotionRank = when (piece.color) {
                    Piece.Color.White -> 7
                    Piece.Color.Black -> 0
                }
                if (toSquare.rank == promotionRank) {
                    return promotionPiece == null
                }
            }
            return false
        }
}

@Composable
private fun PromotionSelector(promotionData: MoveData?, onSelected: (MoveData) -> Unit) {
    if (promotionData != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.background(Color.LightGray)
                    .padding(8.dp),
            ) {
                listOf(Piece.Type.Rook, Piece.Type.Knight, Piece.Type.Bishop, Piece.Type.Queen)
                    .forEach { type ->
                        val piece = Piece(type, promotionData.piece.color)
                        Image(
                            modifier = Modifier.clickable {
                                onSelected(promotionData.copy(promotionPiece = type))
                            },
                            painter = painterResource(piece.imagePath),
                            contentDescription = piece.contentDescription
                        )
                    }
            }
        }
    }
}

@Composable
private fun Board(config: Config) {
    Column {
        for (rank in 0 until 8) {
            Row {
                for (file in 0 until 8) {
                    Surface(
                        modifier = Modifier.width(config.cellSize).height(config.cellSize),
                        color = if ((rank + file) % 2 == 0) config.theme.lightColor else config.theme.darkColor
                    ) {}
                }
            }
        }
    }
}

@Composable
private inline fun Pieces(
    config: Config,
    pieces: Map<Square, Piece>,
    crossinline onMove: (piece: Piece, fromSquare: Square, toSquare: Square) -> Unit
) {
    Column {
        for (rank in 7 downTo 0) {
            Row {
                for (file in 0 until 8) {
                    val square = Square(file = file, rank = rank)
                    val piece = pieces[square]
                    if (piece != null) {
                        Piece(config, piece, square) { fromSquare, toSquare ->
                            onMove(piece, fromSquare, toSquare)
                        }
                    } else {
                        Box(modifier = Modifier.width(config.cellSize).height(config.cellSize))
                    }
                }
            }
        }
    }
}

@Composable
private inline fun Piece(
    config: Config,
    piece: Piece,
    square: Square,
    crossinline onMove: (fromSquare: Square, toSquare: Square) -> Unit,
) {
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier.width(config.cellSize).height(config.cellSize)
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .pointerInput(piece, square) {
                detectDragGestures(
                    onDragEnd = {
                        val cellDiffX = (offset.x / config.cellSize.value).roundToInt()
                        val cellDiffY = (offset.y / config.cellSize.value).roundToInt()

                        runCatching {
                            Square(file = square.file + cellDiffX, rank = square.rank - cellDiffY)
                        }.getOrNull()?.let { toSquare ->
                            onMove(square, toSquare)
                        }

                        offset = Offset.Zero
                    },
                    onDrag = { change, dragAmount ->
                        change.consumeAllChanges()
                        offset += dragAmount
                    }
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.width(config.pieceSize).height(config.pieceSize),
            painter = painterResource(piece.imagePath),
            contentDescription = piece.contentDescription
        )
    }
}

private val Piece.imagePath: String
    get() = when (color) {
        Piece.Color.White -> when (type) {
            Piece.Type.Pawn -> "wP.svg"
            Piece.Type.Knight -> "wN.svg"
            Piece.Type.Bishop -> "wB.svg"
            Piece.Type.Rook -> "wR.svg"
            Piece.Type.Queen -> "wQ.svg"
            Piece.Type.King -> "wK.svg"
        }
        Piece.Color.Black -> when (type) {
            Piece.Type.Pawn -> "bP.svg"
            Piece.Type.Knight -> "bN.svg"
            Piece.Type.Bishop -> "bB.svg"
            Piece.Type.Rook -> "bR.svg"
            Piece.Type.Queen -> "bQ.svg"
            Piece.Type.King -> "bK.svg"
        }
    }

private val Piece.contentDescription: String
    get() = "${color.toString().lowercase()} ${type.toString().lowercase()}"
