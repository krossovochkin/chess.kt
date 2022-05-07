package com.krossovochkin.chess

import kotlin.test.Test

class BishopTest {

    @Test
    fun `if zero square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Bf4"
        )
    }

    @Test
    fun `if one square rank move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Bf5"
        )
    }

    @Test
    fun `if one square file move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Bg4"
        )
    }

    @Test
    fun `if two square rank move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Bf6"
        )
    }

    @Test
    fun `if two square file move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Bh4"
        )
    }

    @Test
    fun `if one diagonal N-E square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Bg5",
            resultFen = "rnbqkbnr/pp2pppp/2pp4/6B1/3P4/8/PPP1PPPP/RN1QKBNR b KQkq - 1 3"
        )
    }

    @Test
    fun `if one diagonal N-W square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Be5",
            resultFen = "rnbqkbnr/pp2pppp/2pp4/4B3/3P4/8/PPP1PPPP/RN1QKBNR b KQkq - 1 3"
        )
    }

    @Test
    fun `if one diagonal S-E square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Bg3",
            resultFen = "rnbqkbnr/pp2pppp/2pp4/8/3P4/6B1/PPP1PPPP/RN1QKBNR b KQkq - 1 3"
        )
    }

    @Test
    fun `if one diagonal S-W square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pp2pppp/2pp4/8/3P1B2/8/PPP1PPPP/RN1QKBNR w KQkq - 0 3",
            move = "Be3",
            resultFen = "rnbqkbnr/pp2pppp/2pp4/8/3P4/4B3/PPP1PPPP/RN1QKBNR b KQkq - 1 3"
        )
    }

    @Test
    fun `if multiple diagonal N-E square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/2pppppp/1p6/p7/5B2/3P3P/PPP1PPP1/RN1QKBNR w KQkq - 0 4",
            move = "Bh6",
            resultFen = "rnbqkbnr/2pppppp/1p5B/p7/8/3P3P/PPP1PPP1/RN1QKBNR b KQkq - 1 4"
        )
    }

    @Test
    fun `if multiple diagonal N-W square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/2pppppp/1p6/p7/5B2/3P3P/PPP1PPP1/RN1QKBNR w KQkq - 0 4",
            move = "Bd6",
            resultFen = "rnbqkbnr/2pppppp/1p1B4/p7/8/3P3P/PPP1PPP1/RN1QKBNR b KQkq - 1 4"
        )
    }

    @Test
    fun `if multiple diagonal S-E square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/2pppppp/1p6/p7/5B2/3P3P/PPP1PPP1/RN1QKBNR w KQkq - 0 4",
            move = "Bh2",
            resultFen = "rnbqkbnr/2pppppp/1p6/p7/8/3P3P/PPP1PPPB/RN1QKBNR b KQkq - 1 4"
        )
    }

    @Test
    fun `if multiple diagonal S-W square move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/2pppppp/1p6/p7/5B2/3P3P/PPP1PPP1/RN1QKBNR w KQkq - 0 4",
            move = "Bd2",
            resultFen = "rnbqkbnr/2pppppp/1p6/p7/8/3P3P/PPPBPPP1/RN1QKBNR b KQkq - 1 4"
        )
    }

    @Test
    fun `if diagonal move and target square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/2pppppp/1p6/p7/5B2/3P3P/PPP1PPP1/RN1QKBNR w KQkq - 0 4",
            move = "Bc7"
        )
    }

    @Test
    fun `if diagonal move and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/2pp1ppp/1p6/p3p3/5B1P/3P4/PPP1PPP1/RN1QKBNR w KQkq - 0 5",
            move = "Bd6"
        )
    }

    @Test
    fun `if capture and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/2pp1ppp/1p6/4p3/p4B1P/3P2P1/PPP1PP2/RN1QKBNR w KQkq - 0 6",
            move = "Bxg5"
        )
    }

    @Test
    fun `if capture and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/2pp1ppp/1p6/4p3/p4B1P/3P2P1/PPP1PP2/RN1QKBNR w KQkq - 0 6",
            move = "Bxg3"
        )
    }

    @Test
    fun `if capture and intermediate square not empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/2pp1ppp/1p6/4p3/p4B1P/3P2P1/PPP1PP2/RN1QKBNR w KQkq - 0 6",
            move = "Bxc7"
        )
    }

    @Test
    fun `if capture and all intermediate squares empty and target square has enemy piece then captures`() {
        testMove(
            initialFen = "rnbqkbnr/2pp1ppp/1p6/4p3/p4B1P/3P2P1/PPP1PP2/RN1QKBNR w KQkq - 0 6",
            move = "Bxe5",
            resultFen = "rnbqkbnr/2pp1ppp/1p6/4B3/p6P/3P2P1/PPP1PP2/RN1QKBNR b KQkq - 0 6"
        )
    }
}
