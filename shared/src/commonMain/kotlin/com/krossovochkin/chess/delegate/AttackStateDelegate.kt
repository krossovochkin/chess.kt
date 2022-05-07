package com.krossovochkin.chess.delegate

import com.krossovochkin.chess.*
import com.krossovochkin.chess.Board

internal class AttackStateDelegate(
    private val moveDelegateProvider: () -> MoveDelegate
) {
    private val moveDelegate by lazy { moveDelegateProvider() }
    private val tempBoard = Board()

    fun getKingAttackState(
        board: Board,
        color: Piece.Color,
    ): SquareAttackState {
        val kingSquares =
            board.findPiecesSquares { it.type == Piece.Type.King && it.color == color }
        check(kingSquares.size == 1)
        val kingSquare = kingSquares.keys.first()

        return getSquareAttackState(
            board = board,
            square = kingSquare,
            color = color.invert(),
        )
    }

    fun getSquareAttackState(
        board: Board,
        square: Square,
        color: Piece.Color,
    ): SquareAttackState {
        val attackSquares = board
            .findPiecesSquares { it.color == color }
            .filter { (fromSquare, piece) ->
                moveDelegate.checkMove(
                    board = board,
                    tempBoard = tempBoard,
                    move = Move.GeneralMove(
                        piece = piece.type,
                        fromFile = fromSquare.file,
                        fromRank = fromSquare.rank,
                        toFile = square.file,
                        toRank = square.rank,
                        isCapture = null
                    ),
                    color = color,
                ) is MoveResult.Success || moveDelegate.checkMove(
                    board = board,
                    tempBoard = tempBoard,
                    move = Move.PromotionMove(
                        fromFile = fromSquare.file,
                        toFile = square.file,
                        toRank = square.rank,
                        // no need to check all promotion pieces
                        // if possible to promote, then doesn't matter to what piece
                        promotionPiece = Piece.Type.Queen,
                        isCapture = null
                    ),
                    color = color,
                ) is MoveResult.Success
            }

        return if (attackSquares.isEmpty()) {
            SquareAttackState.NotAttacked
        } else {
            SquareAttackState.UnderAttack("square $square under attack by ${attackSquares.keys.joinToString()}")
        }
    }

    fun isKingUnderAttack(
        board: Board,
        color: Piece.Color,
    ): Boolean {
        val kingAttackState = getKingAttackState(board, color)

        return kingAttackState is SquareAttackState.UnderAttack
    }

    sealed class SquareAttackState {
        object NotAttacked : SquareAttackState()

        data class UnderAttack(
            val reason: String,
        ) : SquareAttackState()
    }
}
