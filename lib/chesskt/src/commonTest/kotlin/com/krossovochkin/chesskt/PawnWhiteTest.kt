package com.krossovochkin.chesskt

import kotlin.test.Test

class PawnWhiteTest {

    @Test
    fun `if two squares move and initial position then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "e4",
            resultFen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"
        )
    }

    @Test
    fun `if two squares move and not initial position then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2",
            move = "d6"
        )
    }

    @Test
    fun `if one square move and initial position then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "e3",
            resultFen = "rnbqkbnr/pppppppp/8/8/8/4P3/PPPP1PPP/RNBQKBNR b KQkq - 0 1"
        )
    }

    @Test
    fun `if one square move and not initial position then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppp1ppp/4p3/8/3P4/8/PPP1PPPP/RNBQKBNR w KQkq - 0 2",
            move = "d5",
            resultFen = "rnbqkbnr/pppp1ppp/4p3/3P4/8/8/PPP1PPPP/RNBQKBNR b KQkq - 0 2"
        )
    }

    @Test
    fun `if zero square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "e2"
        )
    }

    @Test
    fun `if negative square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "e1"
        )
    }

    @Test
    fun `if more than two squares move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "e5"
        )
    }

    @Test
    fun `if diagonal square then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/p3pppp/1p1P4/8/8/1Pp4P/P2PPPP1/RNBQKBNR w KQkq - 0 6",
            move = "c3"
        )
    }

    @Test
    fun `if one square move and target square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/8/8/2N1p3/PPPPPPPP/R1BQKBNR w KQkq - 0 4",
            move = "e3"
        )
    }

    @Test
    fun `if two squares move and target square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/8/4p3/8/PPPPPPPP/RNBQKBNR w KQkq - 0 3",
            move = "e4"
        )
    }

    @Test
    fun `if two squares move and initial position and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/8/8/2N1p3/PPPPPPPP/R1BQKBNR w KQkq - 0 4",
            move = "e4"
        )
    }

    @Test
    fun `if capture left and target square has enemy piece then captures`() {
        testMove(
            initialFen = "rnbqkbnr/ppp1pppp/8/8/8/2Np4/PPPPPPPP/R1BQKBNR w KQkq - 0 4",
            move = "exd3",
            resultFen = "rnbqkbnr/ppp1pppp/8/8/8/2NP4/PPPP1PPP/R1BQKBNR b KQkq - 0 4"
        )
    }

    @Test
    fun `if capture left and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppp1pppp/8/3p4/8/3P4/PPP1PPPP/RNBQKBNR w KQkq - 0 2",
            move = "exd3"
        )
    }

    @Test
    fun `if capture left move and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "exd3"
        )
    }

    @Test
    fun `if capture right and target square has enemy piece then captures`() {
        testMove(
            initialFen = "rnbqkbnr/ppppp1pp/8/8/8/2N2p2/PPPPPPPP/R1BQKBNR w KQkq - 0 4",
            move = "exf3",
            resultFen = "rnbqkbnr/ppppp1pp/8/8/8/2N2P2/PPPP1PPP/R1BQKBNR b KQkq - 0 4"
        )
    }

    @Test
    fun `if capture right and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppp1pppp/8/3p4/8/5P2/PPPPP1PP/RNBQKBNR w KQkq - 0 2",
            move = "exf3"
        )
    }

    @Test
    fun `if capture right and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "exf3"
        )
    }
}

