package com.krossovochkin.chesskt.delegate

import com.krossovochkin.chesskt.*
import com.krossovochkin.chesskt.Board
import com.krossovochkin.chesskt.Square.Companion.asSquare
import kotlin.math.abs

internal class MoveDelegate(
    private val enPassantDelegate: EnPassantDelegate,
    private val attackStateDelegate: AttackStateDelegate,
    private val castlingDelegate: CastlingDelegate,
    private val halfMoveCountDelegate: HalfMoveCountDelegate,
    private val moveCountDelegate: MoveCountDelegate,
) {

    private val tempBoard = Board()

    private enum class MoveType {
        Normal,
        DryRun,
        Check,
    }

    fun move(
        board: Board,
        move: Move,
        color: Piece.Color,
    ): MoveResult {
        board.copyInto(tempBoard)
        return move(
            board = tempBoard,
            move = move,
            color = color,
            moveType = MoveType.Normal
        ).also {
            if (it is MoveResult.Success) {
                tempBoard.copyInto(board)
            }
        }
    }

    fun moveDryRun(
        board: Board,
        move: Move,
        color: Piece.Color,
        tempBoard: Board,
    ): MoveResult {
        board.copyInto(tempBoard)
        return move(
            board = tempBoard,
            move = move,
            color = color,
            moveType = MoveType.DryRun
        )
    }

    fun checkMove(
        board: Board,
        move: Move,
        color: Piece.Color,
        tempBoard: Board
    ): MoveResult {
        board.copyInto(tempBoard)
        return move(
            board = tempBoard,
            move = move,
            color = color,
            moveType = MoveType.Check,
        )
    }

    private fun move(
        board: Board,
        move: Move,
        color: Piece.Color,
        moveType: MoveType,
    ): MoveResult {
        return when (move) {
            is Move.CastleMove -> board.move(
                move = move,
                color = color,
                moveType = moveType,
            )
            is Move.GeneralMove -> board.move(
                move = move,
                color = color,
                moveType = moveType,
                isPromotion = false,
            )
            is Move.PromotionMove -> board.move(
                move = move,
                color = color,
                moveType = moveType,
            )
        }
    }

    private fun Board.move(
        move: Move.GeneralMove,
        color: Piece.Color,
        moveType: MoveType,
        isPromotion: Boolean,
    ): MoveResult {
        val piece = Piece(type = move.piece, color = color)
        val fromSquares = this.findFromSquares(piece, move)

        if (fromSquares.isEmpty()) {
            return MoveResult.Error("from squares are empty")
        }

        val toSquare = Square(file = move.toFile, rank = move.toRank)
        val isCapture = move.isCapture
            ?: (get(toSquare) != null || (move.piece == Piece.Type.Pawn && enPassantDelegate.isEnPassantSquare(
                toSquare
            )))
        val availableMoves =
            fromSquares.filter { piece.canMove(this, it, toSquare, isCapture, isPromotion) }

        val fromSquare = if (availableMoves.size == 1) {
            availableMoves.first()
        } else {
            return MoveResult.Error("available moves size: ${availableMoves.size}")
        }

        if (moveType == MoveType.Check) {
            return MoveResult.Success(move)
        }

        clear(fromSquare)
        set(toSquare, piece)

        if (enPassantDelegate.isEnPassantSquare(toSquare)) {
            clear(
                when (color) {
                    Piece.Color.White -> Square(
                        file = toSquare.file, rank = toSquare.rank - 1
                    )
                    Piece.Color.Black -> Square(
                        file = toSquare.file, rank = toSquare.rank + 1
                    )
                }
            )
        }

        if (attackStateDelegate.isKingUnderAttack(this, color)) {
            return MoveResult.Error("King under check")
        }

        val fixedMove = move.copy(
            fromFile = fromSquare.file,
            fromRank = fromSquare.rank,
            isCapture = isCapture,
        )

        if (moveType == MoveType.Normal) {

            castlingDelegate.update(
                board = tempBoard,
                move = fixedMove,
                color = color,
            )

            enPassantDelegate.update(
                board = this,
                move = fixedMove,
                color = color,
            )

            halfMoveCountDelegate.update(
                move = fixedMove
            )

            moveCountDelegate.update(color)
        }

        return MoveResult.Success(fixedMove)
    }

    private fun Board.move(
        move: Move.PromotionMove,
        color: Piece.Color,
        moveType: MoveType,
    ): MoveResult {
        val moveResult = this.move(
            move.toGeneralMove(),
            color,
            moveType,
            isPromotion = true
        )
        return if (moveResult is MoveResult.Success) {
            this.set(
                Square(
                    file = move.toFile, rank = when (color) {
                        Piece.Color.White -> 7
                        Piece.Color.Black -> 0
                    }
                ), Piece(type = move.promotionPiece, color = color)
            )
            return MoveResult.Success(
                move = move.copy(
                    isCapture = moveResult.move.isCapture
                )
            )
        } else {
            moveResult
        }
    }

    private fun Board.move(
        move: Move.CastleMove,
        color: Piece.Color,
        moveType: MoveType,
    ): MoveResult {
        return when (val result =
            castlingDelegate.getCastlingAvailability(this, color, move.type)) {
            CastlingDelegate.CastlingAvailability.Available -> {
                when (move.type) {
                    Move.CastleMove.CastleType.Long -> {
                        val oldRookSquare = when (color) {
                            Piece.Color.White -> "a1"
                            Piece.Color.Black -> "a8"
                        }.asSquare()!!
                        val oldKingSquare = when (color) {
                            Piece.Color.White -> "e1"
                            Piece.Color.Black -> "e8"
                        }.asSquare()!!
                        val newRookSquare = when (color) {
                            Piece.Color.White -> "d1"
                            Piece.Color.Black -> "d8"
                        }.asSquare()!!
                        val newKingSquare = when (color) {
                            Piece.Color.White -> "c1"
                            Piece.Color.Black -> "c8"
                        }.asSquare()!!

                        clear(oldRookSquare)
                        clear(oldKingSquare)
                        set(newRookSquare, Piece(Piece.Type.Rook, color))
                        set(newKingSquare, Piece(Piece.Type.King, color))
                    }
                    Move.CastleMove.CastleType.Short -> {
                        val oldRookSquare = when (color) {
                            Piece.Color.White -> "h1"
                            Piece.Color.Black -> "h8"
                        }.asSquare()!!
                        val oldKingSquare = when (color) {
                            Piece.Color.White -> "e1"
                            Piece.Color.Black -> "e8"
                        }.asSquare()!!
                        val newRookSquare = when (color) {
                            Piece.Color.White -> "f1"
                            Piece.Color.Black -> "f8"
                        }.asSquare()!!
                        val newKingSquare = when (color) {
                            Piece.Color.White -> "g1"
                            Piece.Color.Black -> "g8"
                        }.asSquare()!!

                        clear(oldRookSquare)
                        clear(oldKingSquare)
                        set(newRookSquare, Piece(Piece.Type.Rook, color))
                        set(newKingSquare, Piece(Piece.Type.King, color))
                    }
                }

                if (moveType == MoveType.Normal) {
                    castlingDelegate.update(
                        board = tempBoard,
                        move = move,
                        color = color,
                    )

                    enPassantDelegate.update(
                        board = this,
                        move = move,
                        color = color,
                    )

                    halfMoveCountDelegate.update(
                        move = move
                    )

                    moveCountDelegate.update(color)
                }

                MoveResult.Success(move)
            }
            is CastlingDelegate.CastlingAvailability.NotAvailable -> {
                MoveResult.Error("Castle not available: ${result.reason}")
            }
        }
    }

    private fun Piece.canMove(
        board: Board, from: Square, to: Square, isCapture: Boolean, isPromotion: Boolean
    ): Boolean {
        fun get(square: Square): Piece? {
            return board.get(square)
        }
        return when (type) {
            Piece.Type.Pawn -> {
                val fileDistance = abs(to.file - from.file)
                val rankDistance = if (this.color == Piece.Color.White) {
                    to.rank - from.rank
                } else {
                    from.rank - to.rank
                }

                when (fileDistance) {
                    0 -> {
                        if (get(to) != null) {
                            return false
                        }
                        return when (rankDistance) {
                            1 -> {
                                val promotionRank = when (this.color) {
                                    Piece.Color.White -> 7
                                    Piece.Color.Black -> 0
                                }
                                if (isPromotion) {
                                    true
                                } else {
                                    to.rank != promotionRank
                                }
                            }
                            2 -> {
                                val intermediateRank = when (this.color) {
                                    Piece.Color.White -> 2
                                    Piece.Color.Black -> 5
                                }
                                if (get(Square(from.file, intermediateRank)) != null) {
                                    return false
                                }
                                val initialRank = when (this.color) {
                                    Piece.Color.White -> 1
                                    Piece.Color.Black -> 6
                                }
                                from.rank == initialRank
                            }
                            else -> false
                        }
                    }
                    1 -> {
                        if (rankDistance != 1) {
                            return false
                        }
                        if (!isCapture) {
                            return false
                        }

                        val promotionRank = when (this.color) {
                            Piece.Color.White -> 7
                            Piece.Color.Black -> 0
                        }
                        if (!isPromotion && to.rank == promotionRank) {
                            return false
                        }

                        val targetPiece = get(to)
                        if (targetPiece != null && targetPiece.color != this.color) {
                            return true
                        }
                        if (enPassantDelegate.isEnPassantSquare(to)) {
                            return true
                        }
                        return false
                    }
                    else -> {
                        return false
                    }
                }
            }
            Piece.Type.Knight -> {
                val fileDistance = abs(from.file - to.file)
                val rankDistance = abs(from.rank - to.rank)

                if (!(fileDistance == 2 && rankDistance == 1 || fileDistance == 1 && rankDistance == 2)) {
                    return false
                }

                val targetPiece = get(to)
                if (!isCapture && targetPiece == null) {
                    return true
                }

                if (isCapture && targetPiece != null && targetPiece.color != this.color) {
                    return true
                }

                return false
            }
            Piece.Type.Bishop -> canBishopMove(
                board = board, from = from, to = to, isCapture = isCapture
            )
            Piece.Type.Rook -> canRookMove(
                board = board, from = from, to = to, isCapture = isCapture
            )
            Piece.Type.Queen -> {
                canBishopMove(
                    board = board, from = from, to = to, isCapture = isCapture
                ) || canRookMove(board = board, from = from, to = to, isCapture = isCapture)
            }
            Piece.Type.King -> canKingMove(
                board = board, from = from, to = to, isCapture = isCapture
            )
        }
    }

    private fun Piece.canRookMove(
        board: Board, from: Square, to: Square, isCapture: Boolean
    ): Boolean {
        fun get(square: Square): Piece? {
            return board.get(square)
        }
        if (this.type != Piece.Type.Rook && this.type != Piece.Type.Queen) {
            return false
        }

        val fileDistance = to.file - from.file
        val rankDistance = to.rank - from.rank

        if (fileDistance != 0 && rankDistance != 0) {
            return false
        }

        val targetPiece = get(to)

        if (!isCapture && targetPiece != null) {
            return false
        }
        if (isCapture && (targetPiece == null || targetPiece.color == this.color)) {
            return false
        }

        val fileDirection = when {
            fileDistance > 0 -> 1
            fileDistance < 0 -> -1
            else -> 0
        }
        val rankDirection = when {
            rankDistance > 0 -> 1
            rankDistance < 0 -> -1
            else -> 0
        }

        var currentFile = from.file + fileDirection
        var currentRank = from.rank + rankDirection

        while (currentFile != to.file || currentRank != to.rank) {
            if (get(Square(file = currentFile, rank = currentRank)) != null) {
                return false
            }
            currentFile += fileDirection
            currentRank += rankDirection
        }

        return true
    }

    private fun Piece.canBishopMove(
        board: Board, from: Square, to: Square, isCapture: Boolean
    ): Boolean {
        fun get(square: Square): Piece? {
            return board.get(square)
        }
        if (this.type != Piece.Type.Bishop && this.type != Piece.Type.Queen) {
            return false
        }

        val fileDistance = to.file - from.file
        val rankDistance = to.rank - from.rank

        if (fileDistance == 0 || rankDistance == 0) {
            return false
        }

        if (abs(fileDistance) != abs(rankDistance)) {
            return false
        }

        val targetPiece = get(to)

        if (!isCapture && targetPiece != null) {
            return false
        }
        if (isCapture && (targetPiece == null || targetPiece.color == this.color)) {
            return false
        }

        val fileDirection = if (fileDistance > 0) 1 else -1
        val rankDirection = if (rankDistance > 0) 1 else -1

        var currentFile = from.file + fileDirection
        var currentRank = from.rank + rankDirection

        while (currentFile != to.file && currentRank != to.rank) {
            if (get(Square(file = currentFile, rank = currentRank)) != null) {
                return false
            }
            currentFile += fileDirection
            currentRank += rankDirection
        }

        return true
    }

    private fun Piece.canKingMove(
        board: Board, from: Square, to: Square, isCapture: Boolean
    ): Boolean {
        fun get(square: Square): Piece? {
            return board.get(square)
        }
        if (this.type != Piece.Type.King) {
            return false
        }

        val fileDistance = to.file - from.file
        val rankDistance = to.rank - from.rank

        if (fileDistance == 0 && rankDistance == 0) {
            return false
        }
        if (abs(fileDistance) > 1) {
            return false
        }
        if (abs(rankDistance) > 1) {
            return false
        }

        val targetPiece = get(to)

        if (!isCapture && targetPiece != null) {
            return false
        }
        if (isCapture && (targetPiece == null || targetPiece.color == this.color)) {
            return false
        }

        return true
    }
}
