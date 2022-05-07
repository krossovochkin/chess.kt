package com.krossovochkin.chess

sealed class GameResult {

    data class Checkmate(
        val winnerColor: Piece.Color
    ) : GameResult()

    sealed class Draw : GameResult() {

        object Stalemate : Draw()

        object ThreefoldRepetition : Draw()

        object InsufficientMaterial : Draw()

        object FiftyMoves : Draw()
    }
}
