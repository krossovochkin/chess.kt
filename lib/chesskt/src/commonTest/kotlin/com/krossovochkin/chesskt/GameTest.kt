package com.krossovochkin.chesskt

import com.krossovochkin.chesskt.Move.Companion.asMove
import com.krossovochkin.chesskt.fen.FenParser
import com.krossovochkin.chesskt.fen.FenSerializer
import kotlin.test.Test
import kotlin.test.assertTrue

class GameTest {

    @Test
    fun `create game with default position successfully created`() {
        val game = Game.create()

        requireNotNull(game)
        check(!game.isGameOver)
        check(game.isWhiteTurn)

        assertEquals(
            expectedFen = FenParser.DEFAULT_POSITION,
            actualFen = FenSerializer.serialize(game.state)
        )
    }

    @Test
    fun `white pawn initial position move two squares successfully moves`() {
        val game = Game.create()
        requireNotNull(game)

        assertTrue(game.move("e4".asMove()!!))

        assertEquals(
            expectedFen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1",
            actualFen = FenSerializer.serialize(game.state)
        )
    }

    @Test
    fun `black pawn initial position move two squares successfully moves`() {
        val game = Game.create("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1")
        requireNotNull(game)

        assertTrue(game.move("c5".asMove()!!))

        assertEquals(
            expectedFen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2",
            actualFen = FenSerializer.serialize(game.state)
        )
    }

    @Test
    fun `white knight initial position move successfully moves`() {
        val game = Game.create("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
        requireNotNull(game)

        assertTrue(game.move("e4".asMove()!!))
        assertTrue(game.move("c5".asMove()!!))
        assertTrue(game.move("Nf3".asMove()!!))

        assertEquals(
            expectedFen = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2",
            actualFen = FenSerializer.serialize(game.state)
        )
    }

    @Test
    fun `when three same positions arise then game ends with threefold repetition`() {
        val game = Game.create("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
        requireNotNull(game)

        listOf(
            "d4", "d5",
            "Qd3", "Qd6",
            "Qd1", "Nf6",
            "Qd3", "Ng8",
            "Qd1", "Nf6",
            "Qd3", "Ng8",
        ).forEach {
            assertTrue(game.move(it.asMove()!!))
        }

        assertTrue(game.isThreefoldRepetition)
        assertEquals(
            expectedFen = "rnb1kbnr/ppp1pppp/3q4/3p4/3P4/3Q4/PPP1PPPP/RNB1KBNR w KQkq - 10 7",
            actualFen = FenSerializer.serialize(game.state)
        )
    }

    @Test
    fun `if only two kings left then game ends with insufficient material draw`() {
        val game = Game.create("8/8/3k4/8/2Kr4/8/8/8 w - - 0 1")
        requireNotNull(game)

        assertTrue(game.move("Kxd4".asMove()!!))

        assertTrue(game.isInsufficientMaterial)
        assertEquals(
            expectedFen = "8/8/3k4/8/3K4/8/8/8 b - - 0 1",
            actualFen = FenSerializer.serialize(game.state)
        )
    }

    private fun assertEquals(expectedFen: String, actualFen: String) {
        kotlin.test.assertEquals(expectedFen, actualFen)
    }
}
