package com.krossovochkin.chess

import kotlin.test.Test

class KnightTest {

    @Test
    fun `if one square rank move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Ng2"
        )
    }

    @Test
    fun `if one square file move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Nf1"
        )
    }

    @Test
    fun `if diagonal square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Nf2"
        )
    }

    @Test
    fun `if two square rank move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Ng3"
        )
    }

    @Test
    fun `if two square file move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Ne1"
        )
    }

    @Test
    fun `if two diagonal square move then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Ne3"
        )
    }

    @Test
    fun `if one square file and two square rank move then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Nf3",
            resultFen = "rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 1 1"
        )
    }

    @Test
    fun `if two square file and one square rank move then move`() {
        testMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/8/4P3/PPPP1PPP/RNBQKBNR w KQkq - 0 2",
            move = "Ne2",
            resultFen = "rnbqkbnr/pppp1ppp/8/4p3/8/4P3/PPPPNPPP/RNBQKB1R b KQkq - 1 2"
        )
    }

    @Test
    fun `if move and target square empty then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Nf3",
            resultFen = "rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R b KQkq - 1 1"
        )
    }

    @Test
    fun `if move and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Ne2"
        )
    }

    @Test
    fun `if move and target square has enemy piece then captures`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/8/5N2/PPPPPPPP/RNBQKB1R w KQkq - 0 2",
            move = "Ne5"
        )
    }

    @Test
    fun `if captures and target square empty then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Nxf3"
        )
    }

    @Test
    fun `if captures and target square has ally piece then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            move = "Nxe2"
        )
    }

    @Test
    fun `if captures and target square has enemy piece then captures`() {
        testMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/4p3/8/5N2/PPPPPPPP/RNBQKB1R w KQkq - 0 2",
            move = "Nxe5",
            resultFen = "rnbqkbnr/pppp1ppp/8/4N3/8/8/PPPPPPPP/RNBQKB1R b KQkq - 0 2"
        )
    }

    @Test
    fun `if move and two knights able to move to target square and no starting square then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/ppp2ppp/4p3/3p4/3P4/5N2/PPP1PPPP/RNBQKB1R w KQkq - 0 3",
            move = "Nd2"
        )
    }

    @Test
    fun `if move and two knights able to move to target square and has starting square (rank) then moves`() {
        testMove(
            initialFen = "rnbqkbnr/ppp2ppp/4p3/3p4/3P4/5N2/PPP1PPPP/RNBQKB1R w KQkq - 0 3",
            move = "Nbd2",
            resultFen = "rnbqkbnr/ppp2ppp/4p3/3p4/3P4/5N2/PPPNPPPP/R1BQKB1R b KQkq - 1 3"
        )
    }

    @Test
    fun `if move and two knights able to move to target square and has starting square (file) then moves`() {
        testMove(
            initialFen = "rnbq1bnr/ppNppkp1/5p2/8/7p/2N5/PPPPPPPP/R1BQKB1R w KQ - 1 6",
            move = "N7d5",
            resultFen = "rnbq1bnr/pp1ppkp1/5p2/3N4/7p/2N5/PPPPPPPP/R1BQKB1R b KQ - 2 6"
        )
    }

    @Test
    fun `if captures and two knights able to move to target square and no starting square then no move`() {
        testNoMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/8/7P/5N2/PPPpPPP1/RNBQKB1R w KQkq - 0 5",
            move = "Nxd2"
        )
    }

    @Test
    fun `if captures and two knights able to move to target square and has starting square (rank) then moves`() {
        testMove(
            initialFen = "rnbqkbnr/pppp1ppp/8/8/7P/5N2/PPPpPPP1/RNBQKB1R w KQkq - 0 5",
            move = "Nbxd2",
            resultFen = "rnbqkbnr/pppp1ppp/8/8/7P/5N2/PPPNPPP1/R1BQKB1R b KQkq - 0 5"
        )
    }

    @Test
    fun `if captures and two knights able to move to target square and has starting square (file) then moves`() {
        testMove(
            initialFen = "rnbq1bnr/ppN1pkp1/5p2/3p4/7p/2N4P/PPPPPPP1/R1BQKB1R w KQ - 0 7",
            move = "N7xd5",
            resultFen = "rnbq1bnr/pp2pkp1/5p2/3N4/7p/2N4P/PPPPPPP1/R1BQKB1R b KQ - 0 7"
        )
    }
}
