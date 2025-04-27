package com.krossovochkin.chesskt

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import chesskt.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

private data class Config(
    val theme: BoardTheme,
    val cellSize: Dp,
) {
    data class BoardTheme(
        val lightColor: Color,
        val darkColor: Color,
    )
}

@Composable
@Preview
fun App(cellSize: Dp = 64.dp) {
    MaterialTheme {
        val config = remember {
            Config(
                theme = Config.BoardTheme(
                    lightColor = Color.Gray,
                    darkColor = Color.DarkGray,
                ),
                cellSize = cellSize,
            )
        }
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
    PromotionSelector(config, pendingMoveData) { invokeMove(it) }
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
private fun PromotionSelector(config: Config, promotionData: MoveData?, onSelected: (MoveData) -> Unit) {
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
                            modifier = Modifier
                                .width(config.cellSize).height(config.cellSize)
                                .clickable {
                                    onSelected(promotionData.copy(promotionPiece = type))
                                },
                            painter = painterResource(piece.drawableResource),
                            contentDescription = piece.contentDescription,
                            colorFilter = ColorFilter.tint(
                                when (piece.color) {
                                    Piece.Color.White -> Color.White
                                    Piece.Color.Black -> Color.Black
                                }
                            )
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
    var offsetPx by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier.width(config.cellSize).height(config.cellSize)
            .offset { IntOffset(offsetPx.x.roundToInt(), offsetPx.y.roundToInt()) }
            .pointerInput(piece, square) {
                detectDragGestures(
                    onDragEnd = {
                        val cellDiffX = (offsetPx.x / config.cellSize.toPx()).roundToInt()
                        val cellDiffY = (offsetPx.y / config.cellSize.toPx()).roundToInt()

                        runCatching {
                            Square(file = square.file + cellDiffX, rank = square.rank - cellDiffY)
                        }.getOrNull()?.let { toSquare ->
                            onMove(square, toSquare)
                        }

                        offsetPx = Offset.Zero
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetPx += dragAmount
                    }
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.width(config.cellSize).height(config.cellSize),
            painter = painterResource(piece.drawableResource),
            contentDescription = piece.contentDescription,
            colorFilter = ColorFilter.tint(
                when (piece.color) {
                    Piece.Color.White -> Color.White
                    Piece.Color.Black -> Color.Black
                }
            )
        )
    }
}

private val Piece.drawableResource: DrawableResource
    get() = when (type) {
        Piece.Type.Pawn -> Res.drawable.piece_pawn
        Piece.Type.Knight -> Res.drawable.piece_knight
        Piece.Type.Bishop -> Res.drawable.piece_bishop
        Piece.Type.Rook -> Res.drawable.piece_rook
        Piece.Type.Queen -> Res.drawable.piece_queen
        Piece.Type.King -> Res.drawable.piece_king
    }

private val Piece.contentDescription: String
    get() = "${color.toString().lowercase()} ${type.toString().lowercase()}"
