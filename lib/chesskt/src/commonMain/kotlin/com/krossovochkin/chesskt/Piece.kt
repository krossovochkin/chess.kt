package com.krossovochkin.chesskt

data class Piece(
    val type: Type,
    val color: Color
) {

    enum class Type(internal val value: String) {
        Pawn(""),
        Knight("N"),
        Bishop("B"),
        Rook("R"),
        Queen("Q"),
        King("K")
    }

    enum class Color {
        White,
        Black
        ;

        fun invert(): Color {
            return when (this) {
                White -> Black
                Black -> White
            }
        }
    }
}
