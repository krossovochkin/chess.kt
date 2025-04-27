package com.krossovochkin.chesskt

import kotlin.test.Test

class RookTest {

    @Test
    fun `if zero square move then no move`() {
        testNoMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Re5"
        )
    }

    @Test
    fun `if diagonal square move then no move`() {
        testNoMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rf6"
        )
    }

    @Test
    fun `if one rank N move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Re6",
            resultFen = "1nbqkbnr/2pppppp/rp2R3/p7/P7/8/1PPPPPPP/1NBQKBNR b Kk - 1 5"
        )
    }

    @Test
    fun `if one rank S move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Re4",
            resultFen = "1nbqkbnr/2pppppp/rp6/p7/P3R3/8/1PPPPPPP/1NBQKBNR b Kk - 1 5"
        )
    }

    @Test
    fun `if one file W move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rd5",
            resultFen = "1nbqkbnr/2pppppp/rp6/p2R4/P7/8/1PPPPPPP/1NBQKBNR b Kk - 1 5"
        )
    }

    @Test
    fun `if one file E move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rf5",
            resultFen = "1nbqkbnr/2pppppp/rp6/p4R2/P7/8/1PPPPPPP/1NBQKBNR b Kk - 1 5"
        )
    }

    @Test
    fun `if multiple rank N move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/r7/pp6/P7/4R3/1PPPPPPP/1NBQKBNR w Kk - 0 6",
            move = "Re5",
            resultFen = "1nbqkbnr/2pppppp/r7/pp2R3/P7/8/1PPPPPPP/1NBQKBNR b Kk - 1 6"
        )
    }

    @Test
    fun `if multiple rank S move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Re3",
            resultFen = "1nbqkbnr/2pppppp/rp6/p7/P7/4R3/1PPPPPPP/1NBQKBNR b Kk - 1 5"
        )
    }

    @Test
    fun `if multiple file W move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rc5",
            resultFen = "1nbqkbnr/2pppppp/rp6/p1R5/P7/8/1PPPPPPP/1NBQKBNR b Kk - 1 5"
        )
    }

    @Test
    fun `if multiple file E move then moves`() {
        testMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rg5",
            resultFen = "1nbqkbnr/2pppppp/rp6/p5R1/P7/8/1PPPPPPP/1NBQKBNR b Kk - 1 5"
        )
    }

    @Test
    fun `if straight move and target square not empty then no move`() {
        testNoMove(
            initialFen = "1nbqkbnr/2pppppp/r7/pp6/P7/4R3/1PPPPPPP/1NBQKBNR w Kk - 0 6",
            move = "Re7"
        )
    }

    @Test
    fun `if straight move and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "1nbqkbnr/1p1ppppp/r7/p1p1R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rb5"
        )
    }

    @Test
    fun `if capture and target square empty then no move`() {
        testNoMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rxd5"
        )
    }

    @Test
    fun `if capture and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "1nbqkbnr/2pppppp/rp6/p3R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rxe2"
        )
    }

    @Test
    fun `if capture and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "1nbqkbnr/1p1ppppp/r7/p1p1R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rxa5"
        )
    }

    @Test
    fun `if capture and all intermediate squares empty and target square has enemy piece then captures`() {
        testMove(
            initialFen = "1nbqkbnr/1p1ppppp/r7/p1p1R3/P7/8/1PPPPPPP/1NBQKBNR w Kk - 0 5",
            move = "Rxc5",
            resultFen = "1nbqkbnr/1p1ppppp/r7/p1R5/P7/8/1PPPPPPP/1NBQKBNR b Kk - 0 5"
        )
    }

    @Test
    fun `if move and two rooks able to move to target square and no starting square then no move`() {

    }

    @Test
    fun `if move and two rooks able to move to target square and has starting square (rank) then moves`() {

    }

    @Test
    fun `if move and two rooks able to move to target square and has starting square (file) then moves`() {

    }

    @Test
    fun `if capture and two rooks able to move to target square and no starting square then no move`() {

    }

    @Test
    fun `if capture and two rooks able to move to target square and has starting square (rank) then captures`() {

    }

    @Test
    fun `if capture and two rooks able to move to target square and has starting square (file) then captures`() {

    }
}
