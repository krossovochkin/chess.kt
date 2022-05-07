package com.krossovochkin.chess.delegate

import com.krossovochkin.chess.GameResult
import com.krossovochkin.chess.GameState
import com.krossovochkin.chess.fen.FenSerializer

internal class GameOverDelegate(
    private val threefoldRepetitionDelegate: ThreefoldRepetitionDelegate,
    private val insufficientMaterialDelegate: InsufficientMaterialDelegate,
    private val halfMoveCountDelegate: HalfMoveCountDelegate,
    private val moveAvailabilityDelegate: MoveAvailabilityDelegate,
    private val attackStateDelegate: AttackStateDelegate,
) {

    fun checkResult(
        state: GameState,
    ): GameResult? {
        if (threefoldRepetitionDelegate.check(FenSerializer.serialize(state))) {
            return GameResult.Draw.ThreefoldRepetition
        }
        if (insufficientMaterialDelegate.check(state.board)) {
            return GameResult.Draw.InsufficientMaterial
        }
        if (halfMoveCountDelegate.check()) {
            return GameResult.Draw.FiftyMoves
        }

        val availableMoves = moveAvailabilityDelegate.getAvailableMoves(state.board, state.currentColor)
        if (availableMoves.isEmpty()) {
            return if (attackStateDelegate.isKingUnderAttack(state.board, state.currentColor)) {
                GameResult.Checkmate(state.currentColor.invert())
            } else {
                GameResult.Draw.Stalemate
            }
        }

        return null
    }
}
