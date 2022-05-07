package com.krossovochkin.chess.fen

import com.krossovochkin.chess.*
import com.krossovochkin.chess.delegate.CastlingDelegate

internal object FenParser {

    const val DEFAULT_POSITION = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

    fun parse(fen: String): GameState? {
        var rank = 7
        var file = 0

        val board = Board()
        var isWhiteTurn = true
        var isWhiteKingCastleAvailable = false
        var isWhiteQueenCastleAvailable = false
        var isBlackKingCastleAvailable = false
        var isBlackQueenCastleAvailable = false
        var enPassantFile: Int? = null
        var enPassantRank: Int? = null
        var halfMoveCount: Int? = null
        var moveCount: Int? = null

        /**
         * 0 - board position
         * 1 - turn
         * 2 - castling
         * 3 - en passant
         * 4 - halfmove clock
         * 5 - fullmove clock
         */
        var state: Int = 0

        fen.forEach { c ->
            when (state) {
                0 -> {
                    val piece = when (c) {
                        'p' -> Piece(Piece.Type.Pawn, Piece.Color.Black)
                        'P' -> Piece(Piece.Type.Pawn, Piece.Color.White)
                        'r' -> Piece(Piece.Type.Rook, Piece.Color.Black)
                        'R' -> Piece(Piece.Type.Rook, Piece.Color.White)
                        'n' -> Piece(Piece.Type.Knight, Piece.Color.Black)
                        'N' -> Piece(Piece.Type.Knight, Piece.Color.White)
                        'b' -> Piece(Piece.Type.Bishop, Piece.Color.Black)
                        'B' -> Piece(Piece.Type.Bishop, Piece.Color.White)
                        'q' -> Piece(Piece.Type.Queen, Piece.Color.Black)
                        'Q' -> Piece(Piece.Type.Queen, Piece.Color.White)
                        'k' -> Piece(Piece.Type.King, Piece.Color.Black)
                        'K' -> Piece(Piece.Type.King, Piece.Color.White)
                        else -> null
                    }

                    when {
                        piece != null -> {
                            board.set(Square(file = file, rank = rank), piece)
                            file++
                        }
                        c == '/' -> {
                            rank--
                            file = 0
                        }
                        c.digitToIntOrNull() != null -> {
                            file += c.digitToInt()
                        }
                        c == ' ' -> state++
                        else -> {
                            println("Unknown char '$c' on state '$state'")
                            return null
                        }
                    }
                }
                1 -> {
                    when (c) {
                        'w' -> isWhiteTurn = true
                        'b' -> isWhiteTurn = false
                        ' ' -> state++
                        else -> {
                            println("Unknown char '$c' on state '$state'")
                            return null
                        }
                    }
                }
                2 -> {
                    when (c) {
                        'K' -> isWhiteKingCastleAvailable = true
                        'Q' -> isWhiteQueenCastleAvailable = true
                        'k' -> isBlackKingCastleAvailable = true
                        'q' -> isBlackQueenCastleAvailable = true
                        '-' -> Unit
                        ' ' -> state++
                        else -> {
                            println("Unknown char '$c' on state '$state'")
                            return null
                        }
                    }
                }
                3 -> {
                    when (c) {
                        '-' -> Unit
                        in 'a'..'h' -> {
                            enPassantFile = c.toFile()
                        }
                        in '0'..'9' -> {
                            enPassantRank = c.toRank()
                        }
                        ' ' -> {
                            if (enPassantFile == null && enPassantRank != null || enPassantFile != null && enPassantRank == null) {
                                return null
                            }
                            state++
                        }
                        else -> {
                            println("Unknown char '$c' on state '$state'")
                            return null
                        }
                    }
                }
                4 -> {
                    when (c) {
                        in '0'..'9' -> {
                            halfMoveCount = if (halfMoveCount == null) {
                                c.digitToInt()
                            } else {
                                halfMoveCount!! * 10 + c.digitToInt()
                            }
                        }
                        ' ' -> state++
                        else -> {
                            println("Unknown char '$c' on state '$state'")
                            return null
                        }
                    }
                }
                5 -> {
                    when (c) {
                        in '0'..'9' -> {
                            moveCount = if (moveCount == null) {
                                c.digitToInt()
                            } else {
                                moveCount!! * 10 + c.digitToInt()
                            }
                        }
                        ' ' -> state++
                        else -> {
                            println("Unknown char '$c' on state '$state'")
                            return null
                        }
                    }
                }
                6 -> {
                    return@forEach
                }
            }
        }

        return GameState(
            board = board,
            isWhiteTurn = isWhiteTurn,
            whiteCastleState = CastlingDelegate.CastleState(
                isKingCastleAvailable = isWhiteKingCastleAvailable,
                isQueenCastleAvailable = isWhiteQueenCastleAvailable,
            ),
            blackCastleState = CastlingDelegate.CastleState(
                isKingCastleAvailable = isBlackKingCastleAvailable,
                isQueenCastleAvailable = isBlackQueenCastleAvailable,
            ),
            enPassantSquare = if (enPassantFile != null && enPassantRank != null) {
                Square(
                    file = enPassantFile!!,
                    rank = enPassantRank!!,
                )
            } else {
                null
            },
            halfMoveCount = halfMoveCount!!,
            moveCount = moveCount!!,
        )
    }
}
