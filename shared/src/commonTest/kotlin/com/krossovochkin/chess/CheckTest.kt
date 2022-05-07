package com.krossovochkin.chess

import kotlin.test.Test

class CheckTest {

    @Test
    fun `if king move and king after move is under attack then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p7/B1p2ppp/3pn1B1/Q1PPPK2/1pN3PN/PP5P/R6R w kq - 1 14",
            move = "Kf5"
        )
    }

    @Test
    fun `if king move and king after capture is under attack then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p7/B1p2ppp/3pn1B1/Q1PPPK2/1pN3PN/PP5P/R6R w kq - 1 14",
            move = "Kxe5",
        )
    }

    @Test
    fun `if non-king move and king after move is under attack then no move`() {
        testNoMove(
            initialFen = "r1bqk1nr/p7/B1pb1pp1/3pP1Bp/Q1P1PKP1/1pN4N/PP5P/R6R w kq - 0 16",
            move = "exf6"
        )
    }
}
