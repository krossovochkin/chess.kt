package com.krossovochkin.chesskt

import kotlin.test.Test

class KingTest {

    @Test
    fun `if zero square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Ke1"
        )
    }

    @Test
    fun `if one square N move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Ke2",
            resultFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP2K1PP/RN5R b kq - 1 9"
        )
    }

    @Test
    fun `if one square S move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/4K2N/PP4PP/RN5R w kq - 4 11",
            move = "Ke2",
            resultFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/7N/PP2K1PP/RN5R b kq - 5 11"
        )
    }

    @Test
    fun `if one square E move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Kf1",
            resultFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN3K1R b kq - 1 9"
        )
    }

    @Test
    fun `if one square W move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Kd1",
            resultFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN1K3R b kq - 1 9"
        )
    }

    @Test
    fun `if one square N-E move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Kf2",
            resultFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP3KPP/RN5R b kq - 1 9"
        )
    }

    @Test
    fun `if one square N-W move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Kd2",
            resultFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP1K2PP/RN5R b kq - 1 9"
        )
    }

    @Test
    fun `if one square S-E move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/4K2N/PP4PP/RN5R w kq - 4 11",
            move = "Kf2",
            resultFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/7N/PP3KPP/RN5R b kq - 5 11"
        )
    }

    @Test
    fun `if one square S-W move then moves`() {
        testMove(
            initialFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/4K2N/PP4PP/RN5R w kq - 4 11",
            move = "Kd2",
            resultFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/7N/PP1K2PP/RN5R b kq - 5 11"
        )
    }

    @Test
    fun `if more than one square N move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Ke3"
        )
    }

    @Test
    fun `if more than one square S move then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/4K2N/PP4PP/RN5R w kq - 4 11",
            move = "Ke1"
        )
    }

    @Test
    fun `if more than one square N-E move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Kg3"
        )
    }

    @Test
    fun `if more than one square N-W move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/p7/B1p2ppp/1p1pp1B1/Q1PPPP2/7N/PP4PP/RN2K2R w KQkq - 0 9",
            move = "Kc3"
        )
    }

    @Test
    fun `if more than one square S-W move then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/4K2N/PP4PP/RN5R w kq - 4 11",
            move = "Kc1"
        )
    }

    @Test
    fun `if more than one square S-E move then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p7/Bnp2ppp/1p1pp1B1/Q1PPPP2/4K2N/PP4PP/RN5R w kq - 4 11",
            move = "Kg1"
        )
    }

    @Test
    fun `if move and target square not empty then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p2n4/B1p2ppp/3pp1B1/Q1PPPP2/1pN1K2N/PP4PP/R6R w kq - 0 12",
            move = "Ke4"
        )
    }

    @Test
    fun `if captures and target square empty then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p2n4/B1p2ppp/3pp1B1/Q1PPPP2/1pN1K2N/PP4PP/R6R w kq - 0 12",
            move = "Kxe2"
        )
    }

    @Test
    fun `if captures and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "r1bqkbnr/p2n4/B1p2ppp/3pp1B1/Q1PPPP2/1pN1K2N/PP4PP/R6R w kq - 0 12",
            move = "Kxf4"
        )
    }

    @Test
    fun `if captures and target square has enemy piece then captures`() {
        testMove(
            initialFen = "r1bqkbnr/p2n4/B1p2ppp/3p2B1/Q1PPPp2/1pN1K1PN/PP5P/R6R w kq - 0 13",
            move = "Kxf4",
            resultFen = "r1bqkbnr/p2n4/B1p2ppp/3p2B1/Q1PPPK2/1pN3PN/PP5P/R6R b kq - 0 13"
        )
    }
}
