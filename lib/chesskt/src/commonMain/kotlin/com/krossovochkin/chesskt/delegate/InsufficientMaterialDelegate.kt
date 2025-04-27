package com.krossovochkin.chesskt.delegate

import com.krossovochkin.chesskt.Board
import com.krossovochkin.chesskt.Piece
import com.krossovochkin.chesskt.Square

internal class InsufficientMaterialDelegate {

    fun check(board: Board): Boolean {
        val whitePieces = board.findPiecesSquares { it.color == Piece.Color.White }
        val blackPieces = board.findPiecesSquares { it.color == Piece.Color.Black }

        return isKingVsKing(whitePieces = whitePieces, blackPieces = blackPieces) ||
            isKingWithMinorPieceVsKing(whitePieces = whitePieces, blackPieces = blackPieces) ||
            isKingWithTwoKnightsVsKing(whitePieces = whitePieces, blackPieces = blackPieces) ||
            isKingWithMinorPieceVsKingWithMinorPieceExceptSameColorBishops(
                whitePieces = whitePieces,
                blackPieces = blackPieces
            )
    }

    private fun isKingVsKing(
        whitePieces: Map<Square, Piece>,
        blackPieces: Map<Square, Piece>
    ): Boolean {
        return whitePieces.hasOnlyKing && blackPieces.hasOnlyKing
    }

    private fun isKingWithMinorPieceVsKing(
        whitePieces: Map<Square, Piece>,
        blackPieces: Map<Square, Piece>
    ): Boolean {
        val (smallerSide, biggerSide) = when {
            whitePieces.size > blackPieces.size -> whitePieces to blackPieces
            blackPieces.size > whitePieces.size -> blackPieces to whitePieces
            else -> return false
        }

        if (!smallerSide.hasOnlyKing) {
            return false
        }

        var minorPieceCount = 0
        biggerSide.forEach { (_, piece) ->
            when (piece.type) {
                Piece.Type.Pawn,
                Piece.Type.Rook,
                Piece.Type.Queen -> return false
                Piece.Type.Knight,
                Piece.Type.Bishop -> {
                    minorPieceCount++
                    if (minorPieceCount > 1) {
                        return false
                    }
                }
                Piece.Type.King -> Unit
            }
        }

        return true
    }

    private fun isKingWithTwoKnightsVsKing(
        whitePieces: Map<Square, Piece>,
        blackPieces: Map<Square, Piece>
    ): Boolean {
        val (smallerSide, biggerSide) = when {
            whitePieces.size > blackPieces.size -> whitePieces to blackPieces
            blackPieces.size > whitePieces.size -> blackPieces to whitePieces
            else -> return false
        }

        if (!smallerSide.hasOnlyKing) {
            return false
        }

        biggerSide.forEach { (_, piece) ->
            when (piece.type) {
                Piece.Type.Pawn,
                Piece.Type.Rook,
                Piece.Type.Queen,
                Piece.Type.Bishop -> return false
                /*
                not necessary to count exact knights count
                if it is 0 - then it is king vs king handled before
                if it is 1 - then it is king vs king with minor piece handled before
                 */
                Piece.Type.Knight,
                Piece.Type.King -> Unit
            }
        }

        return false
    }

    private fun isKingWithMinorPieceVsKingWithMinorPieceExceptSameColorBishops(
        whitePieces: Map<Square, Piece>,
        blackPieces: Map<Square, Piece>
    ): Boolean {
        if (whitePieces.size != 2) {
            return false
        }
        if (blackPieces.size != 2) {
            return false
        }

        val whiteMinorPiece = whitePieces.entries.first { it.value.type != Piece.Type.King }
        val blackMinorPiece = blackPieces.entries.first { it.value.type != Piece.Type.King }

        if (whiteMinorPiece.value.type !in listOf(Piece.Type.Knight, Piece.Type.Bishop)) {
            return false
        }
        if (blackMinorPiece.value.type !in listOf(Piece.Type.Knight, Piece.Type.Bishop)) {
            return false
        }

        if (whiteMinorPiece.value.type == Piece.Type.Bishop && blackMinorPiece.value.type == Piece.Type.Bishop) {
            if (whiteMinorPiece.key.isLight == blackMinorPiece.key.isLight) {
                return false
            }
        }

        return true
    }

    private val Map<Square, Piece>.hasOnlyKing: Boolean
        get() {
            if (size != 1) {
                return false
            }
            if (values.first().type != Piece.Type.King) {
                return false
            }

            return true
        }
}
