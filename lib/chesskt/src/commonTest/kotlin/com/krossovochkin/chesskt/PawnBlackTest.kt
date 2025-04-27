package com.krossovochkin.chesskt

import kotlin.test.Test

class PawnBlackTest {

    @Test
    fun `if two squares move and initial position then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 1",
            move = "e5",
            resultFen = "rnbqkbnr/pppp1ppp/8/4p3/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"
        )
    }

    @Test
    fun `if two squares move and not initial position then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/3Pp3/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 2",
            move = "e3"
        )
    }

    @Test
    fun `if one square move and initial position then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 1",
            move = "e6",
            resultFen = "rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2"
        )
    }

    @Test
    fun `if one square move and not initial position then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppp1ppp/4p3/3P4/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 2",
            move = "e5",
            resultFen = "rnbqkbnr/pppp1ppp/8/3Pp3/8/8/PPP1PPPP/RNBQKBNR w KQkq - 0 3"
        )
    }

    @Test
    fun `if zero square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 1",
            move = "e7"
        )
    }

    @Test
    fun `if negative square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 1",
            move = "e8"
        )
    }

    @Test
    fun `if more than two squares move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq - 0 1",
            move = "e4"
        )
    }

    @Test
    fun `if diagonal square then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppp2ppp/8/3Pp3/8/3P4/PPP2PPP/RNBQKBNR b KQkq - 0 3",
            move = "d4"
        )
    }

    @Test
    fun `if one square move and target square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 2",
            move = "e4"
        )
    }

    @Test
    fun `if two squares move and initial position and target square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppp1pppp/8/3pP3/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2",
            move = "e5"
        )
    }

    @Test
    fun `if two squares move and initial position and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2pppp/4P3/2pp4/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3",
            move = "e5"
        )
    }

    @Test
    fun `if capture left and target square has enemy piece then captures`() {
        testMove(
            initialFen = "rnbqkbnr/pp2pppp/4P3/2pp4/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3",
            move = "fxe6",
            resultFen = "rnbqkbnr/pp2p1pp/4p3/2pp4/8/8/PPPP1PPP/RNBQKBNR w KQkq - 0 4"
        )
    }

    @Test
    fun `if capture left and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2p1pp/4p3/2pp4/8/5P2/PPPP2PP/RNBQKBNR b KQkq - 0 4",
            move = "exd5"
        )
    }

    @Test
    fun `if capture left move and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2p1pp/4p3/2pp4/8/5P2/PPPP2PP/RNBQKBNR b KQkq - 0 4",
            move = "cxb4"
        )
    }

    @Test
    fun `if capture right and target square has enemy piece then captures`() {
        testMove(
            initialFen = "rnbqkbnr/ppp1pppp/8/3p4/3PP3/8/PPP2PPP/RNBQKBNR b KQkq - 0 2",
            move = "dxe4",
            resultFen = "rnbqkbnr/ppp1pppp/8/8/3Pp3/8/PPP2PPP/RNBQKBNR w KQkq - 0 3"
        )
    }

    @Test
    fun `if capture right and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppp1pp1p/6p1/8/3Pp2P/6P1/PPP2P2/RNBQKBNR b KQkq - 0 4",
            move = "fxe6"
        )
    }

    @Test
    fun `if capture right and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppp1pp1p/6p1/8/3Pp2P/6P1/PPP2P2/RNBQKBNR b KQkq - 0 4",
            move = "exh5"
        )
    }
}
