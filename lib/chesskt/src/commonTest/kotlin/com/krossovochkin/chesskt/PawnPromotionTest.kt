package com.krossovochkin.chesskt

import kotlin.test.Test

class PawnPromotionTest {

    @Test
    fun `if moves to promotion square without promotion then no move`() {
        testNoMove(
            initialFen = "rnb2bnr/pppPkppp/8/8/4p3/8/PPP2PPP/RNBQKBNR w KQ - 1 6",
            move = "d8",
        )
    }

    @Test
    fun `if captures to promotion square without promotion then promotes`() {
        testNoMove(
            initialFen = "rnb2bnr/pppPkppp/8/8/4p3/8/PPP2PPP/RNBQKBNR w KQ - 1 6",
            move = "dxc8",
        )
    }

    @Test
    fun `if promotes with move to queen then promotes`() {
        testMove(
            initialFen = "rnb2bnr/pppPkppp/8/8/4p3/8/PPP2PPP/RNBQKBNR w KQ - 1 6",
            move = "d8=Q",
            resultFen = "rnbQ1bnr/ppp1kppp/8/8/4p3/8/PPP2PPP/RNBQKBNR b KQ - 0 6"
        )
    }

    @Test
    fun `if promotes with capture to queen then promotes`() {
        testMove(
            initialFen = "rnb2bnr/pppPkppp/8/8/4p3/8/PPP2PPP/RNBQKBNR w KQ - 1 6",
            move = "dxc8=Q",
            resultFen = "rnQ2bnr/ppp1kppp/8/8/4p3/8/PPP2PPP/RNBQKBNR b KQ - 0 6"
        )
    }
}
