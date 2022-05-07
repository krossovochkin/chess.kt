package com.krossovochkin.chess

import kotlin.test.Test

class QueenTest {

    @Test
    fun `if zero square move then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qf4"
        )
    }

    @Test
    fun `if one square rank move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qf5",
            resultFen = "r1bqkbnr/pppppppp/n7/5Q2/8/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if one square file move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qe4",
            resultFen = "r1bqkbnr/pppppppp/n7/8/4Q3/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if two square rank move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qf6",
            resultFen = "r1bqkbnr/pppppppp/n4Q2/8/8/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if two square file move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qd4",
            resultFen = "r1bqkbnr/pppppppp/n7/8/3Q4/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if one diagonal N-E square move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qg5",
            resultFen = "r1bqkbnr/pppppppp/n7/6Q1/8/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if one diagonal N-W square move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qe5",
            resultFen = "r1bqkbnr/pppppppp/n7/4Q3/8/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if one diagonal S-E square move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qg3",
            resultFen = "r1bqkbnr/pppppppp/n7/8/8/3P2Q1/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if one diagonal S-W square move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qe3",
            resultFen = "r1bqkbnr/pppppppp/n7/8/8/3PQ3/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if multiple diagonal N-E square move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qh6",
            resultFen = "r1bqkbnr/pppppppp/n6Q/8/8/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if multiple diagonal N-W square move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qd6",
            resultFen = "r1bqkbnr/pppppppp/n2Q4/8/8/3P4/PPP1PPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if multiple diagonal S-E square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qh2",
            resultFen = "rnbqkbnr/pppppppp/8/8/8/3P3P/PPP1PPPQ/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if multiple diagonal S-W square move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/8/5Q2/3P4/PPP1PPPP/RNB1KBNR w KQkq - 5 4",
            move = "Qd2",
            resultFen = "r1bqkbnr/pppppppp/n7/8/8/3P4/PPPQPPPP/RNB1KBNR b KQkq - 6 4"
        )
    }

    @Test
    fun `if diagonal move and target square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qc7"
        )
    }

    @Test
    fun `if diagonal move and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/5Q1P/3P4/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qd6"
        )
    }

    @Test
    fun `if diagonal capture and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/5Q1P/3P4/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qxf3"
        )
    }

    @Test
    fun `if diagonal capture and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/5Q1P/3P4/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qxc1"
        )
    }

    @Test
    fun `if diagonal capture and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/5Q1P/3P4/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qxc7"
        )
    }

    @Test
    fun `if diagonal capture and all intermediate squares empty and target square has enemy piece then captures`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qxc7",
            resultFen = "rnbqkbnr/ppQppppp/8/8/8/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 0 5"
        )
    }

    @Test
    fun `if one rank N move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qf5",
            resultFen = "rnbqkbnr/pppppppp/8/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if one rank S move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qf3",
            resultFen = "rnbqkbnr/pppppppp/8/8/8/3P1Q1P/PPP1PPP1/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if one file W move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qe4",
            resultFen = "rnbqkbnr/pppppppp/8/8/4Q3/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if one file E move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qg4",
            resultFen = "rnbqkbnr/pppppppp/8/8/6Q1/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if multiple rank N move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qf6",
            resultFen = "rnbqkbnr/pppppppp/5Q2/8/8/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if multiple rank S move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 3 6",
            move = "Qf3",
            resultFen = "r1bqkbnr/pppppppp/n7/8/8/3P1Q1P/PPP1PPP1/RNB1KBNR b KQkq - 4 6"
        )
    }

    @Test
    fun `if multiple file W move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qd4",
            resultFen = "rnbqkbnr/pppppppp/8/8/3Q4/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if multiple file E move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/5Q2/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 1 5",
            move = "Qh4",
            resultFen = "rnbqkbnr/pppppppp/8/8/7Q/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 2 5"
        )
    }

    @Test
    fun `if straight move and target square not empty then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/pppppppp/n7/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 3 6",
            move = "Qf7"
        )
    }

    @Test
    fun `if straight move and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppppp1pp/5p2/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qf7"
        )
    }

    @Test
    fun `if straight capture and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppppp1pp/5p2/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qxc5"
        )
    }

    @Test
    fun `if straight capture and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppppp1pp/5p2/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qxf2"
        )
    }

    @Test
    fun `if straight capture and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppppp1pp/5p2/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 0 6",
            move = "Qxf8"
        )
    }

    @Test
    fun `if straight capture and all intermediate squares empty and target square has enemy piece then captures`() {
        testMove(
            initialFen = "r1bqkbnr/pppppppp/n7/5Q2/8/3P3P/PPP1PPP1/RNB1KBNR w KQkq - 3 6",
            move = "Qxf7",
            resultFen = "r1bqkbnr/pppppQpp/n7/8/8/3P3P/PPP1PPP1/RNB1KBNR b KQkq - 0 6"
        )
    }
}
