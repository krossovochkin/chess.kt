package com.krossovochkin.chesskt

sealed class Move {

    abstract val isCapture: Boolean?

    data class CastleMove(
        val type: CastleType
    ) : Move() {

        override val isCapture: Boolean = false

        enum class CastleType {
            Short,
            Long
        }

        override fun toString(): String {
            return when (type) {
                CastleType.Short -> "O-O"
                CastleType.Long -> "O-O-O"
            }
        }
    }

    data class GeneralMove(
        val piece: Piece.Type,
        val fromFile: Int?,
        val fromRank: Int?,
        val toFile: Int,
        val toRank: Int,
        override val isCapture: Boolean?,
    ) : Move() {

        override fun toString(): String {
            return buildString {
                append(piece.value)

                fromFile.toFileChar()?.let(::append)
                fromRank.toRankChar()?.let(::append)

                if (isCapture == true) {
                    append('x')
                }

                toFile.toFileChar()?.let(::append)
                toRank.toRankChar()?.let(::append)
            }
        }
    }

    data class PromotionMove(
        val fromFile: Int?,
        val toFile: Int,
        val toRank: Int,
        val promotionPiece: Piece.Type,
        override val isCapture: Boolean?,
    ) : Move() {

        init {
            check(promotionPiece != Piece.Type.King)
            check(promotionPiece != Piece.Type.Pawn)
        }

        internal fun toGeneralMove(): GeneralMove {
            return GeneralMove(
                piece = Piece.Type.Pawn,
                fromFile = fromFile,
                fromRank = when (toRank) {
                    7 -> 6
                    0 -> 1
                    else -> null
                },
                toFile = toFile,
                toRank = toRank,
                isCapture = isCapture
            )
        }

        override fun toString(): String {
            return "${toGeneralMove()}=${promotionPiece.value}"
        }
    }

    companion object {

        fun create(
            piece: Piece,
            from: Square,
            to: Square,
            promotionPiece: Piece.Type? = null
        ): Move {
            if (piece.type == Piece.Type.King) {
                if (from.file == 4) {
                    if (to.file == 2) {
                        return CastleMove(CastleMove.CastleType.Long)
                    }
                    if (to.file == 6) {
                        return CastleMove(CastleMove.CastleType.Short)
                    }
                }
            }
            if (piece.type == Piece.Type.Pawn && promotionPiece != null) {
                return PromotionMove(
                    fromFile = from.file,
                    toFile = to.file,
                    toRank = to.rank,
                    promotionPiece = promotionPiece,
                    isCapture = null,
                )
            }
            return GeneralMove(
                piece = piece.type,
                fromFile = from.file,
                fromRank = from.rank,
                toFile = to.file,
                toRank = to.rank,
                isCapture = null,
            )
        }

        fun String.asMove(): Move? {
            var index = 0
            if (this.isEmpty()) return null

            if (this == "0-0" || this == "O-O") {
                return CastleMove(CastleMove.CastleType.Short)
            }
            if (this == "0-0-0" || this == "O-O-O") {
                return CastleMove(CastleMove.CastleType.Long)
            }

            val type = when (this.getOrNull(index)) {
                'R' -> Piece.Type.Rook
                'N' -> Piece.Type.Knight
                'K' -> Piece.Type.King
                'B' -> Piece.Type.Bishop
                'Q' -> Piece.Type.Queen
                else -> Piece.Type.Pawn
            }

            if (type != Piece.Type.Pawn) {
                index++
            }

            var file1: Int? = null
            if (this.getOrNull(index) in 'a'..'h') {
                file1 = this[index++].toFile()
            }

            var rank1: Int? = null
            if (this.getOrNull(index) in '1'..'8') {
                rank1 = this[index++].toRank()
            }

            var isCapture = false
            if (this.getOrNull(index) == 'x') {
                isCapture = true
                index++
            }

            val file2: Int? = this.getOrNull(index)?.toFile()
            if (file2 != null) {
                index++
            }
            val rank2: Int? = this.getOrNull(index)?.toRank()
            if (rank2 != null) {
                index++
            }

            val (fromFile, toFile) = if (file2 == null) {
                null to file1
            } else {
                file1 to file2
            }
            val (fromRank, toRank) = if (rank2 == null) {
                null to rank1
            } else {
                rank1 to rank2
            }

            if (type == Piece.Type.Pawn && isCapture && fromFile == null) {
                return null
            }

            val promotion = this.getOrNull(index++)

            if (promotion == '=') {
                val promotionPiece = when (this.getOrNull(index++)) {
                    'Q' -> Piece.Type.Queen
                    'B' -> Piece.Type.Bishop
                    'N' -> Piece.Type.Knight
                    'R' -> Piece.Type.Rook
                    else -> null
                }
                if (type != Piece.Type.Pawn) {
                    return null
                }
                if (toFile == null) {
                    return null
                }
                if (toRank != 7 && toRank != 0) {
                    return null
                }
                if (promotionPiece == null) {
                    return null
                }
                return PromotionMove(
                    fromFile = fromFile,
                    toFile = toFile,
                    toRank = toRank,
                    promotionPiece = promotionPiece,
                    isCapture = isCapture,
                )
            }

            return GeneralMove(
                piece = type,
                fromFile = fromFile,
                fromRank = fromRank,
                toFile = toFile ?: return null,
                toRank = toRank ?: return null,
                isCapture = isCapture
            )
        }
    }
}

sealed class MoveResult {
    data class Success(
        val move: Move,
    ) : MoveResult()

    data class Error(
        val reason: String,
    ) : MoveResult()
}
