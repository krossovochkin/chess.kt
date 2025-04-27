package com.krossovochkin.chesskt.delegate

import com.krossovochkin.chesskt.*
import com.krossovochkin.chesskt.Board

internal class MoveAvailabilityDelegate(
    private val moveDelegate: MoveDelegate,
) {

    private val tempBoard = Board()

    fun getAvailableMoves(
        board: Board,
        color: Piece.Color,
        square: Square? = null,
    ): List<Move> {
        return if (square != null) {
            board.getAvailableMovesInternal(color, square)
        } else {
            board
                .findPiecesSquares { piece -> piece.color == color }
                .keys
                .flatMap { pieceSquare ->
                    board.getAvailableMovesInternal(color, pieceSquare)
                }
        }
    }

    private fun Board.getAvailableMovesInternal(
        color: Piece.Color,
        square: Square,
    ): List<Move> {
        val piece = get(square)
        if (piece == null || piece.color != color) {
            return emptyList()
        }
        val generatedMoves = mutableListOf<Move>()
        for (file in 0..7) {
            for (rank in 0..7) {
                val toSquare = Square(file = file, rank = rank)
                generatedMoves += Move.GeneralMove(
                    piece = piece.type,
                    fromFile = square.file,
                    fromRank = square.rank,
                    toFile = toSquare.file,
                    toRank = toSquare.rank,
                    isCapture = null
                )
            }
        }
        if (piece.type == Piece.Type.King) {
            generatedMoves += Move.CastleMove.CastleType.values().map { castleType ->
                Move.CastleMove(castleType)
            }
        }
        if (piece.type == Piece.Type.Pawn) {
            val prePromotionRank = when (color) {
                Piece.Color.White -> 6
                Piece.Color.Black -> 1
            }
            if (square.rank == prePromotionRank) {
                val promotionPieces =
                    listOf(Piece.Type.Rook, Piece.Type.Knight, Piece.Type.Bishop, Piece.Type.Queen)

                promotionPieces.flatMap { promotionPiece ->
                    listOf(-1, 0, 1).map { fileDiff ->
                        Move.PromotionMove(
                            fromFile = square.file + fileDiff,
                            toFile = square.file,
                            toRank = square.rank,
                            promotionPiece = promotionPiece,
                            isCapture = null
                        )
                    }

                }
            }
        }

        return generatedMoves.filter { move ->
            moveDelegate.moveDryRun(
                board = this,
                move = move,
                color = color,
                tempBoard = tempBoard,
            ) is MoveResult.Success
        }
    }
}
