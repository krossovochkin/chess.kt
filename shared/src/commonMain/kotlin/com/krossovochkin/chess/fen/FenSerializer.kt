package com.krossovochkin.chess.fen

import com.krossovochkin.chess.GameState
import com.krossovochkin.chess.Piece
import com.krossovochkin.chess.Square

internal object FenSerializer {

    fun serialize(state: GameState): String {
        return buildString {
            var emptyCount = 0
            for (rank in 7 downTo 0) {
                for (file in 0..7) {
                    val piece = state.board.get(Square(file = file, rank = rank))

                    if (piece != null && emptyCount > 0) {
                        append(emptyCount)
                        emptyCount = 0
                    }

                    val pieceType = when (piece?.type) {
                        Piece.Type.Pawn -> 'p'
                        Piece.Type.Knight -> 'n'
                        Piece.Type.Bishop -> 'b'
                        Piece.Type.Rook -> 'r'
                        Piece.Type.Queen -> 'q'
                        Piece.Type.King -> 'k'
                        null -> {
                            emptyCount++
                            null
                        }
                    }

                    if (piece != null) {
                        append(
                            when (piece.color) {
                                Piece.Color.White -> pieceType!!.uppercase()
                                Piece.Color.Black -> pieceType!!.lowercase()
                            }
                        )
                    }
                }
                if (emptyCount > 0) {
                    append(emptyCount)
                    emptyCount = 0
                }
                if (rank != 0) {
                    append("/")
                }
            }

            append(" ")

            append(if (state.isWhiteTurn) 'w' else 'b')

            append(" ")

            var isCastlingAvailable = false
            if (state.whiteCastleState.isKingCastleAvailable) {
                isCastlingAvailable = true
                append('K')
            }
            if (state.whiteCastleState.isQueenCastleAvailable) {
                isCastlingAvailable = true
                append('Q')
            }
            if (state.blackCastleState.isKingCastleAvailable) {
                isCastlingAvailable = true
                append('k')
            }
            if (state.blackCastleState.isQueenCastleAvailable) {
                isCastlingAvailable = true
                append('q')
            }
            if (!isCastlingAvailable) {
                append('-')
            }

            append(' ')

            append(state.enPassantSquare?.toString() ?: '-')

            append(' ')

            append(state.halfMoveCount)

            append(' ')

            append(state.moveCount)
        }
    }
}
