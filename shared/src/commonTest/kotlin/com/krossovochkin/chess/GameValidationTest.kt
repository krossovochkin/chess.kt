package com.krossovochkin.chess

import com.krossovochkin.chess.fen.FenParser
import com.krossovochkin.chess.fen.FenSerializer
import com.krossovochkin.chess.pgn.PgnParser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameValidationTest {

    @Test
    fun `test not finished game`() {
        test(
            pgn = """
            [Event "F/S Return Match"]
            [Site "Belgrade, Serbia JUG"]
            [Date "1992.11.04"]
            [Round "29"]
            [White "Fischer, Robert J."]
            [Black "Spassky, Boris V."]
            [Result "1/2-1/2"]

            1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 {This opening is called the Ruy Lopez.}
            4. Ba4 Nf6 5. O-O Be7 6. Re1 b5 7. Bb3 d6 8. c3 O-O 9. h3 Nb8 10. d4 Nbd7
            11. c4 c6 12. cxb5 axb5 13. Nc3 Bb7 14. Bg5 b4 15. Nb1 h6 16. Bh4 c5 17. dxe5
            Nxe4 18. Bxe7 Qxe7 19. exd6 Qf6 20. Nbd2 Nxd6 21. Nc4 Nxc4 22. Bxc4 Nb6
            23. Ne5 Rae8 24. Bxf7+ Rxf7 25. Nxf7 Rxe1+ 26. Qxe1 Kxf7 27. Qe3 Qg5 28. Qxg5
            hxg5 29. b3 Ke6 30. a3 Kd6 31. axb4 cxb4 32. Ra5 Nd5 33. f3 Bc8 34. Kf2 Bf5
            35. Ra7 g6 36. Ra6+ Kc5 37. Ke1 Nf4 38. g3 Nxh3 39. Kd2 Kb5 40. Rd6 Kc5 41. Ra6
            Nf2 42. g4 Bd3 43. Re6 1/2-1/2
        """.trimIndent(),
            initialFen = FenParser.DEFAULT_POSITION,
            resultFen = "8/8/4R1p1/2k3p1/1p4P1/1P1b1P2/3K1n2/8 b - - 2 43",
        ) {
            assertFalse(isGameOver)
        }
    }

    @Test
    fun `test checkmate game`() {
        test(
            pgn = """
            1. d4 e5 2. dxe5 f6 3. exf6 d5 4. fxg7 d4 5. gxf8=N Kxf8 6. Qxd4 Qe7 7. h4 c5 8. Rh3 c4 9. Rf3+ Nf6 10. Rxf6+ Kg8 11. Qd5+ Qf7 12. Qxf7#
        """.trimIndent(),
            initialFen = FenParser.DEFAULT_POSITION,
            resultFen = "rnb3kr/pp3Q1p/5R2/8/2p4P/8/PPP1PPP1/RNB1KBN1 b Q - 0 12",
        ) {
            assertTrue(isGameOver)
            assertTrue(isCheckmate)
            assertEquals(
                expected = Piece.Color.White,
                actual = winner
            )
        }
    }

    @Test
    fun `test stalemate game`() {
        test(
            pgn = """
                1. d4 d5 2. e4 dxe4 3. Nf3 exf3 4. c4 Qxd4 5. Qd2 Qxd2+ 6. Kxd2 e5 7. Be2 fxe2 8. c5 e1=Q+ 9. Rxe1 e4 10. f3 Be7 11. h3 exf3 12. h4 fxg2 13. Rf1 gxf1=Q 14. c6 Nxc6 15. b4 Nxb4 16. a3 Qf4+ 17. Kc3 Qxc1+ 18. Kb3 Qxb1+ 19. Ka4 Qxa1 20. h5 a6 21. h6 b6 22. hxg7 Nf6 23. gxh8=Q+ Bf8 24. Qg7 Be6 25. Qh6 Ng8 26. Qg7 Bd5 27. Qh6 Nxh6
            """.trimIndent(),
            initialFen = FenParser.DEFAULT_POSITION,
            resultFen = "r3kb2/2p2p1p/pp5n/3b4/Kn6/P7/8/q7 w q - 0 28"
        ) {
            assertTrue(isGameOver)
            assertTrue(isStalemate)
        }
    }

    private inline fun test(
        pgn: String,
        initialFen: String,
        resultFen: String,
        check: Game.() -> Unit,
    ) {
        val game = Game.create(initialFen)!!
        val moves = PgnParser.parse(pgn).moves

        moves.forEach { move ->
            assertTrue(game.move(move))
        }

        assertEquals(
            expected = resultFen,
            actual = FenSerializer.serialize(game.state)
        )

        game.check()
    }
}
