package com.krossovochkin.chess.delegate

import com.krossovochkin.chess.Move
import com.krossovochkin.chess.Piece

class HalfMoveCountDelegate(
    currentMove: Int,
) {

    var currentMove: Int = currentMove
        private set

    fun update(move: Move) {
        when {
            move.isCapture == true -> {
                currentMove = 0
            }
            move is Move.GeneralMove && move.piece == Piece.Type.Pawn -> {
                currentMove = 0
            }
            move is Move.PromotionMove -> {
                currentMove = 0
            }
            else -> {
                currentMove++
            }
        }
    }

    fun check(): Boolean {
        return currentMove == 50
    }
}
