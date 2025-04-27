package com.krossovochkin.chesskt

import kotlin.test.Test

class CastleTest {


    @Test
    fun `if short castle and castling not available then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/p7/B1p3pp/1p1pppB1/Q1PPPP2/8/PP4PP/RN2K1NR w KQkq - 0 10",
            move = "0-0"
        )
    }

    @Test
    fun `if short castle and castling available then castles`() {
        testMove(
            initialFen = "r1bqkbnr/p2n4/B1p2ppp/1p1pp1B1/Q1PPPP2/2N4N/PP4PP/R3K2R w KQkq - 2 10",
            move = "0-0",
            resultFen = "r1bqkbnr/p2n4/B1p2ppp/1p1pp1B1/Q1PPPP2/2N4N/PP4PP/R4RK1 b kq - 3 10"
        )
    }

    @Test
    fun `if long castle and castling not available then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "0-0-0"
        )
    }

    @Test
    fun `if long castle and castling available then castles`() {
        testMove(
            initialFen = "r1bqkbnr/p2n4/B1p2ppp/1p1pp1B1/Q1PPPP2/2N4N/PP4PP/R3K2R w KQkq - 2 10",
            move = "0-0-0",
            resultFen = "r1bqkbnr/p2n4/B1p2ppp/1p1pp1B1/Q1PPPP2/2N4N/PP4PP/2KR3R b kq - 3 10"
        )
    }

    @Test
    fun `if both kings moved then castling not available`() {
        testMoves(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            moves = arrayOf("e4", "e5", "Ke2", "Ke7"),
            resultFen = "rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3",
        )
    }
}
