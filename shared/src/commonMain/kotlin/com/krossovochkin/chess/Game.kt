package com.krossovochkin.chess

import com.krossovochkin.chess.delegate.*
import com.krossovochkin.chess.fen.FenParser
import com.krossovochkin.chess.fen.FenParser.DEFAULT_POSITION

class Game private constructor(
    initialState: GameState,
    private val castlingDelegate: CastlingDelegate,
    private val enPassantDelegate: EnPassantDelegate,
    private val halfMoveCountDelegate: HalfMoveCountDelegate,
    private val moveCountDelegate: MoveCountDelegate,
    private val attackStateDelegate: AttackStateDelegate,
    private val moveAvailabilityDelegate: MoveAvailabilityDelegate,
    private val moveDelegate: MoveDelegate,
    private val gameOverDelegate: GameOverDelegate,
) {
    private val board: Board = initialState.board
    var isWhiteTurn: Boolean = initialState.isWhiteTurn
        private set
    private val currentColor: Piece.Color
        get() = if (isWhiteTurn) Piece.Color.White else Piece.Color.Black

    internal val state: GameState
        get() = GameState(
            board = board,
            isWhiteTurn = isWhiteTurn,
            whiteCastleState = castlingDelegate.getCastleState(Piece.Color.White),
            blackCastleState = castlingDelegate.getCastleState(Piece.Color.Black),
            enPassantSquare = enPassantDelegate.enPassantSquare,
            halfMoveCount = halfMoveCountDelegate.currentMove,
            moveCount = moveCountDelegate.currentMove,
        )

    private var gameResult: GameResult? = null
        set(value) {
            field = value
            if (value != null) {
                gameResultCallback?.invoke(value)
            }
        }
    private var gameResultCallback: ((GameResult) -> Unit)? = null

    val isGameOver: Boolean
        get() = gameResult != null
    val isDraw: Boolean
        get() = gameResult is GameResult.Draw
    val isStalemate: Boolean
        get() = gameResult is GameResult.Draw.Stalemate
    val isThreefoldRepetition: Boolean
        get() = gameResult is GameResult.Draw.ThreefoldRepetition
    val isInsufficientMaterial: Boolean
        get() = gameResult is GameResult.Draw.InsufficientMaterial
    val isCheckmate: Boolean
        get() = gameResult is GameResult.Checkmate
    val winner: Piece.Color?
        get() {
            val result = gameResult
            return if (result is GameResult.Checkmate) {
                result.winnerColor
            } else {
                null
            }
        }

    val isCheck: Boolean
        get() = attackStateDelegate.isKingUnderAttack(board, currentColor)

    fun availableMoves(square: Square? = null): List<Move> {
        return moveAvailabilityDelegate.getAvailableMoves(board, currentColor, square)
    }

    fun getPiece(square: Square): Piece? {
        return board.get(square)
    }

    fun move(move: Move): Boolean {
        if (isGameOver) {
            return false
        }

        return moveDelegate
            .move(
                board = board,
                move = move,
                color = currentColor,
            )
            .also {
                when (it) {
                    is MoveResult.Success -> println("${it.move}")
                    is MoveResult.Error -> println("Failure: $move, reason=${it.reason}")
                }
            }
            .let { it is MoveResult.Success }
            .also {
                if (it) {
                    isWhiteTurn = !isWhiteTurn
                }
                gameResult = gameOverDelegate.checkResult(state = state)
            }
    }

    fun setGameResultCallback(callback: (GameResult) -> Unit) {
        this.gameResultCallback = callback
    }

    companion object {

        fun create(fen: String = DEFAULT_POSITION): Game? {
            val initialState = FenParser.parse(fen) ?: return null

            var moveDelegate: MoveDelegate? = null
            val attackStateDelegate = AttackStateDelegate { moveDelegate!! }
            val enPassantDelegate =
                EnPassantDelegate(initialState.enPassantSquare)
            val castlingDelegate = CastlingDelegate(
                attackStateDelegate = attackStateDelegate,
                whiteCastleState = initialState.whiteCastleState,
                blackCastleState = initialState.blackCastleState,
            )
            val halfMoveCountDelegate = HalfMoveCountDelegate(initialState.halfMoveCount)
            val moveCountDelegate = MoveCountDelegate(initialState.moveCount)
            moveDelegate = MoveDelegate(
                enPassantDelegate = enPassantDelegate,
                attackStateDelegate = attackStateDelegate,
                castlingDelegate = castlingDelegate,
                halfMoveCountDelegate = halfMoveCountDelegate,
                moveCountDelegate = moveCountDelegate,
            )
            val moveAvailabilityDelegate = MoveAvailabilityDelegate(
                moveDelegate = moveDelegate
            )
            val threefoldRepetitionDelegate = ThreefoldRepetitionDelegate()
            val insufficientMaterialDelegate = InsufficientMaterialDelegate()
            val gameOverDelegate = GameOverDelegate(
                threefoldRepetitionDelegate = threefoldRepetitionDelegate,
                insufficientMaterialDelegate = insufficientMaterialDelegate,
                halfMoveCountDelegate = halfMoveCountDelegate,
                moveAvailabilityDelegate = moveAvailabilityDelegate,
                attackStateDelegate = attackStateDelegate,
            )

            return Game(
                initialState = initialState,
                castlingDelegate = castlingDelegate,
                enPassantDelegate = enPassantDelegate,
                halfMoveCountDelegate = halfMoveCountDelegate,
                moveCountDelegate = moveCountDelegate,
                attackStateDelegate = attackStateDelegate,
                moveAvailabilityDelegate = moveAvailabilityDelegate,
                moveDelegate = moveDelegate,
                gameOverDelegate = gameOverDelegate,
            )
        }
    }
}
