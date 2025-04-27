package com.krossovochkin.chesskt.fen

import com.krossovochkin.chesskt.Game
import kotlin.test.Test
import kotlin.test.assertEquals

class FenTest {

    @Test
    fun `initial position FEN correctly deserialized and serialized`() {
        testFen(FenParser.DEFAULT_POSITION)
    }

    @Test
    fun `if castling not available for both sides then displays -`() {
        testFen("rnbq1bnr/ppppkppp/8/4p3/4P3/8/PPPPKPPP/RNBQ1BNR w - - 2 3")
    }

    private fun testFen(fen: String) {
        val game = Game.create(fen)
        requireNotNull(game)
        assertEquals(fen, FenSerializer.serialize(game.state))
    }
}
