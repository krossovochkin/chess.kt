package com.krossovochkin.chess

import kotlin.test.Test

class PawnEnPassantTest {

    @Test
    fun `if white en passant E available then captures`() {
        testMoves(
            initialFen = "rnbqkbnr/ppppp1pp/8/3P1p2/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 2",
            moves = arrayOf("e5", "dxe6"),
            resultFen = "rnbqkbnr/pppp2pp/4P3/5p2/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3"
        )
    }

    @Test
    fun `if white en passant W available then captures`() {
        testMoves(
            initialFen = "rnbqkbnr/ppppp1pp/8/3P1p2/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 2",
            moves = arrayOf("c5", "dxc6"),
            resultFen = "rnbqkbnr/pp1pp1pp/2P5/5p2/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 3"
        )
    }

    @Test
    fun `if black en passant E available then captures`() {
        testMoves(
            initialFen = "rnbqkbnr/ppp1pppp/8/P7/3p4/8/1PPPPPPP/RNBQKBNR w KQkq - 0 3",
            moves = arrayOf("e4", "dxe3"),
            resultFen = "rnbqkbnr/ppp1pppp/8/P7/8/4p3/1PPP1PPP/RNBQKBNR w KQkq - 0 4"
        )
    }

    @Test
    fun `if black en passant W available then captures`() {
        testMoves(
            initialFen = "rnbqkbnr/ppp1pppp/8/P7/3p4/8/1PPPPPPP/RNBQKBNR w KQkq - 0 3",
            moves = arrayOf("c4", "dxc3"),
            resultFen = "rnbqkbnr/ppp1pppp/8/P7/8/2p5/1P1PPPPP/RNBQKBNR w KQkq - 0 4"
        )
    }
}
