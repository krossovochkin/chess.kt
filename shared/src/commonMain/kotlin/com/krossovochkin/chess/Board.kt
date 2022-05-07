package com.krossovochkin.chess

internal class Board {
    private val board: Array<Array<Piece?>> = arrayOf(
        arrayOf(null, null, null, null, null, null, null, null),
        arrayOf(null, null, null, null, null, null, null, null),
        arrayOf(null, null, null, null, null, null, null, null),
        arrayOf(null, null, null, null, null, null, null, null),
        arrayOf(null, null, null, null, null, null, null, null),
        arrayOf(null, null, null, null, null, null, null, null),
        arrayOf(null, null, null, null, null, null, null, null),
        arrayOf(null, null, null, null, null, null, null, null)
    )

    fun set(square: Square, piece: Piece?) {
        board[square.rank][square.file] = piece
    }

    fun clear(square: Square) {
        set(square, null)
    }

    fun get(square: Square): Piece? {
        return board[square.rank][square.file]
    }

    fun copyInto(destination: Board) {
        for (rank in this.board.indices) {
            for (file in this.board[rank].indices) {
                destination.set(Square(file = file, rank = rank), board[rank][file])
            }
        }
    }

    fun findFromSquares(
        piece: Piece, move: Move.GeneralMove
    ): List<Square> {
        val squares = mutableListOf<Square>()
        for (rank in 7 downTo 0) {
            for (file in 0..7) {
                val square = Square(file = file, rank = rank)
                val pieceOnBoard = get(square)

                val isAvailable =
                    pieceOnBoard == piece && (move.fromFile == null || move.fromFile == file) && (move.fromRank == null || move.fromRank == rank)
                if (isAvailable) {
                    squares.add(square)
                }
            }
        }
        return squares
    }

    inline fun findPiecesSquares(predicate: (Piece) -> Boolean): Map<Square, Piece> {
        val result = mutableMapOf<Square, Piece>()
        for (rank in 7 downTo 0) {
            for (file in 0..7) {
                val square = Square(file = file, rank = rank)
                val pieceOnBoard = get(square)

                if (pieceOnBoard != null && predicate(pieceOnBoard)) {
                    result[square] = pieceOnBoard
                }
            }
        }
        return result
    }
}
