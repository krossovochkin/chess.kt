package com.krossovochkin.chesskt.perft

import com.krossovochkin.chesskt.Game
import com.krossovochkin.chesskt.Move
import com.krossovochkin.chesskt.Square.Companion.asSquare
import kotlin.test.Test
import kotlin.test.assertEquals

class PerftTest {

    @Test
    fun initialPosition() {
        test(
            fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            movesCount = 20,
            capturesCount = 0,
            castlesCount = 0,
            promotionsCount = 0,
        )
    }

    @Test
    fun kiwipete() {
        test(
            fen = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1",
            movesCount = 48,
            capturesCount = 8,
            castlesCount = 2,
            promotionsCount = 0,
        )
    }

    @Test
    fun position3() {
        test(
            fen = "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1 ",
            movesCount = 14,
            capturesCount = 1,
            castlesCount = 0,
            promotionsCount = 0,
        )
    }

    @Test
    fun position4() {
        test(
            fen = "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1",
            movesCount = 6,
            capturesCount = 0,
            castlesCount = 0,
            promotionsCount = 0,
        )
    }

    @Test
    fun position5() {
        test(
            fen = "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8",
            movesCount = 44,
            capturesCount = 2,
            castlesCount = 1,
            promotionsCount = 4,
        )
    }

    @Test
    fun position6() {
        test(
            fen = "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10",
            movesCount = 46,
            capturesCount = 4,
            castlesCount = 0,
            promotionsCount = 0,
        )
    }

    private fun test(
        fen: String,
        movesCount: Int,
        capturesCount: Int,
        castlesCount: Int,
        promotionsCount: Int,
    ) {
        val moves = Game.create(fen)!!
            .availableMoves()

        assertEquals(movesCount, moves.size, "moves count not match")
        assertEquals(capturesCount, moves.count { it.isCapture == true }, "captures count not match")
        assertEquals(castlesCount, moves.count { it is Move.CastleMove }, "castles count not match")
        assertEquals(promotionsCount, moves.count { it is Move.PromotionMove }, "promotions count not match")
    }
}
