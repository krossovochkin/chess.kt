package com.krossovochkin.chess

import com.krossovochkin.chess.Move.Companion.asMove
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseMoveTest {

    @Test
    fun `pawn simple move parsed`() {
        assertEquals(
            actual = "e3".asMove(),
            expected = Move.GeneralMove(
                piece = Piece.Type.Pawn,
                fromFile = null,
                fromRank = null,
                toFile = 'e'.toFile()!!,
                toRank = '3'.toRank()!!,
                isCapture = false
            ),
        )
    }

    @Test
    fun `pawn capture move parsed`() {
        assertEquals(
            actual = "exf3".asMove(),
            expected = Move.GeneralMove(
                piece = Piece.Type.Pawn,
                fromFile = 'e'.toFile()!!,
                fromRank = null,
                toFile = 'f'.toFile()!!,
                toRank = '3'.toRank()!!,
                isCapture = true
            )
        )
    }

    @Test
    fun `knight simple move parsed`() {
        assertEquals(
            actual = "Ne3".asMove(),
            expected = Move.GeneralMove(
                piece = Piece.Type.Knight,
                fromFile = null,
                fromRank = null,
                toFile = 'e'.toFile()!!,
                toRank = '3'.toRank()!!,
                isCapture = false
            ),
        )
    }

    @Test
    fun `knight capture move parsed`() {
        assertEquals(
            actual = "Nxe3".asMove(),
            expected = Move.GeneralMove(
                piece = Piece.Type.Knight,
                fromFile = null,
                fromRank = null,
                toFile = 'e'.toFile()!!,
                toRank = '3'.toRank()!!,
                isCapture = true
            ),
        )
    }

    @Test
    fun `knight collide move parsed`() {
        assertEquals(
            actual = "Nbd2".asMove(),
            expected = Move.GeneralMove(
                piece = Piece.Type.Knight,
                fromFile = 'b'.toFile()!!,
                fromRank = null,
                toFile = 'd'.toFile()!!,
                toRank = '2'.toRank()!!,
                isCapture = false
            ),
        )
    }

    @Test
    fun `knight collide capture move parsed`() {
        assertEquals(
            actual = "Nbxd2".asMove(),
            expected = Move.GeneralMove(
                piece = Piece.Type.Knight,
                fromFile = 'b'.toFile()!!,
                fromRank = null,
                toFile = 'd'.toFile()!!,
                toRank = '2'.toRank()!!,
                isCapture = true
            ),
        )
    }

    @Test
    fun `short castle move parsed`() {
        assertEquals(
            actual = "0-0".asMove(),
            expected = Move.CastleMove(Move.CastleMove.CastleType.Short)
        )
    }

    @Test
    fun `long castle move parsed`() {
        assertEquals(
            actual = "0-0-0".asMove(),
            expected = Move.CastleMove(Move.CastleMove.CastleType.Long)
        )
    }

    @Test
    fun `promotion move parsed`() {
        assertEquals(
            actual = "d8=Q".asMove(),
            expected = Move.PromotionMove(
                fromFile = null,
                toFile = 'd'.toFile()!!,
                toRank = '8'.toRank()!!,
                promotionPiece = Piece.Type.Queen,
                isCapture = false
            )
        )
    }

    @Test
    fun `promotion capture parsed`() {
        assertEquals(
            actual = "dxc8=Q".asMove(),
            expected = Move.PromotionMove(
                fromFile = 'd'.toFile()!!,
                toFile = 'c'.toFile()!!,
                toRank = '8'.toRank()!!,
                promotionPiece = Piece.Type.Queen,
                isCapture = true
            )
        )
    }
}
