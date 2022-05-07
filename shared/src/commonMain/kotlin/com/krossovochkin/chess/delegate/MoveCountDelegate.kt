package com.krossovochkin.chess.delegate

import com.krossovochkin.chess.Piece

class MoveCountDelegate(
    currentMove: Int,
) {

    var currentMove: Int = currentMove
        private set

    fun update(color: Piece.Color) {
        if (color == Piece.Color.Black) {
            currentMove++
        }
    }
}
