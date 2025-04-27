package com.krossovochkin.chesskt.delegate

import com.krossovochkin.chesskt.Board
import com.krossovochkin.chesskt.Move
import com.krossovochkin.chesskt.Piece
import com.krossovochkin.chesskt.Square.Companion.asSquare

internal class CastlingDelegate(
    private var whiteCastleState: CastleState,
    private var blackCastleState: CastleState,
    private val attackStateDelegate: AttackStateDelegate,
) {

    fun getCastleState(color: Piece.Color): CastleState {
        return when (color) {
            Piece.Color.White -> whiteCastleState
            Piece.Color.Black -> blackCastleState
        }
    }

    fun update(
        board: Board,
        move: Move,
        color: Piece.Color,
    ) {
        when (move) {
            is Move.CastleMove -> {
                update(color) { it.copy(isCastled = true) }
            }
            is Move.GeneralMove -> {
                var isKingMoved: Boolean? = null
                var isLongRookMoved: Boolean? = null
                var isShortRookMoved: Boolean? = null

                if (move.piece == Piece.Type.King) {
                    isKingMoved = true
                }

                when (color) {
                    Piece.Color.White -> {
                        if (board.get("a1".asSquare()!!)?.type != Piece.Type.Rook) {
                            isLongRookMoved = true
                        }
                        if (board.get("h1".asSquare()!!)?.type != Piece.Type.Rook) {
                            isShortRookMoved = true
                        }
                    }
                    Piece.Color.Black -> {
                        if (board.get("a8".asSquare()!!)?.type != Piece.Type.Rook) {
                            isLongRookMoved = true
                        }
                        if (board.get("h8".asSquare()!!)?.type != Piece.Type.Rook) {
                            isShortRookMoved = true
                        }
                    }
                }

                update(color) {
                    it.copy(
                        isKingMoved = isKingMoved ?: it.isKingMoved,
                        isLongRookMoved = isLongRookMoved ?: it.isLongRookMoved,
                        isShortRookMoved = isShortRookMoved ?: it.isShortRookMoved,
                    )
                }
            }
            is Move.PromotionMove -> {
                var isLongRookMoved: Boolean? = null
                var isShortRookMoved: Boolean? = null
                when (color) {
                    Piece.Color.White -> {
                        if (board.get("a1".asSquare()!!)?.type != Piece.Type.Rook) {
                            isLongRookMoved = true
                        }
                        if (board.get("h1".asSquare()!!)?.type != Piece.Type.Rook) {
                            isShortRookMoved = true
                        }
                    }
                    Piece.Color.Black -> {
                        if (board.get("a8".asSquare()!!)?.type != Piece.Type.Rook) {
                            isLongRookMoved = true
                        }
                        if (board.get("h8".asSquare()!!)?.type != Piece.Type.Rook) {
                            isShortRookMoved = true
                        }
                    }
                }

                update(color) {
                    it.copy(
                        isLongRookMoved = isLongRookMoved ?: it.isLongRookMoved,
                        isShortRookMoved = isShortRookMoved ?: it.isShortRookMoved,
                    )
                }
            }
        }
    }

    fun getCastlingAvailability(
        board: Board,
        color: Piece.Color,
        castleType: Move.CastleMove.CastleType,
    ): CastlingAvailability {
        val castleState = getCastleState(color)
        if (castleState.isCastled) {
            return CastlingAvailability.NotAvailable("already castled")
        }
        if (castleState.isKingMoved) {
            return CastlingAvailability.NotAvailable("king moved")
        }
        if (attackStateDelegate.getKingAttackState(
                board,
                color
            ) is AttackStateDelegate.SquareAttackState.UnderAttack
        ) {
            return CastlingAvailability.NotAvailable("king under check")
        }

        if (castleType == Move.CastleMove.CastleType.Long) {
            if (castleState.isLongRookMoved) {
                return CastlingAvailability.NotAvailable("long rook moved")
            }

            val rookSquare = when (color) {
                Piece.Color.White -> "a1"
                Piece.Color.Black -> "a8"
            }.asSquare()!!
            if (board.get(rookSquare) != Piece(Piece.Type.Rook, color)) {
                return CastlingAvailability.NotAvailable("rook is not on required square")
            }

            val attackSquares = when (color) {
                Piece.Color.White -> listOf("c1", "d1")
                Piece.Color.Black -> listOf("c8", "d8")
            }
            if (attackSquares.any {
                    attackStateDelegate.getSquareAttackState(
                        board,
                        it.asSquare()!!,
                        color.invert(),
                    ) is AttackStateDelegate.SquareAttackState.UnderAttack
                }) {
                return CastlingAvailability.NotAvailable("square is under attack")
            }

            val emptySquares = when (color) {
                Piece.Color.White -> listOf("b1", "c1", "d1")
                Piece.Color.Black -> listOf("b8", "c8", "d8")
            }
            if (emptySquares.any { board.get(it.asSquare()!!) != null }) {
                return CastlingAvailability.NotAvailable("square is not empty")
            }
        }
        if (castleType == Move.CastleMove.CastleType.Short) {
            if (castleState.isShortRookMoved) {
                return CastlingAvailability.NotAvailable("short rook moved")
            }

            val rookSquare = when (color) {
                Piece.Color.White -> "h1"
                Piece.Color.Black -> "h8"
            }.asSquare()!!
            if (board.get(rookSquare) != Piece(Piece.Type.Rook, color)) {
                return CastlingAvailability.NotAvailable("root is not on required square")
            }

            val attackSquares = when (color) {
                Piece.Color.White -> listOf("f1", "g1")
                Piece.Color.Black -> listOf("f8", "g8")
            }
            if (attackSquares.any {
                    attackStateDelegate.getSquareAttackState(
                        board,
                        it.asSquare()!!,
                        color.invert(),
                    ) is AttackStateDelegate.SquareAttackState.UnderAttack
                }) {
                return CastlingAvailability.NotAvailable("square is under attack")
            }

            val emptySquares = when (color) {
                Piece.Color.White -> listOf("f1", "g1")
                Piece.Color.Black -> listOf("f8", "g8")
            }
            if (emptySquares.any { board.get(it.asSquare()!!) != null }) {
                return CastlingAvailability.NotAvailable("square is not empty")
            }
        }

        return CastlingAvailability.Available
    }

    private inline fun update(color: Piece.Color, block: (CastleState) -> CastleState) {
        val newState = block(getCastleState(color))
        when (color) {
            Piece.Color.White -> whiteCastleState = newState
            Piece.Color.Black -> blackCastleState = newState
        }
    }

    data class CastleState(
        val isKingMoved: Boolean = false,
        val isLongRookMoved: Boolean = false,
        val isShortRookMoved: Boolean = false,
        val isCastled: Boolean = false
    ) {
        constructor(isKingCastleAvailable: Boolean, isQueenCastleAvailable: Boolean) :
            this(
                isKingMoved = !isQueenCastleAvailable && !isKingCastleAvailable,
                isLongRookMoved = !isQueenCastleAvailable,
                isShortRookMoved = !isKingCastleAvailable,
                isCastled = false
            )

        val isKingCastleAvailable: Boolean
            get() = !isKingMoved && !isCastled && !isShortRookMoved

        val isQueenCastleAvailable: Boolean
            get() = !isKingMoved && !isCastled && !isLongRookMoved
    }

    sealed class CastlingAvailability {

        object Available : CastlingAvailability()

        data class NotAvailable(
            val reason: String,
        ) : CastlingAvailability()
    }
}
