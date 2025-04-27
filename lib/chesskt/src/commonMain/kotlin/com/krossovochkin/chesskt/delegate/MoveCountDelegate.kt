package com.krossovochkin.chesskt.delegate

import com.krossovochkin.chesskt.Piece

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
