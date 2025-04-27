package com.krossovochkin.chesskt

import com.krossovochkin.chesskt.Move.Companion.asMove
import com.krossovochkin.chesskt.fen.FenSerializer
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal fun testMove(
    initialFen: String,
    move: String,
    resultFen: String
) {
    val game = Game.create(initialFen)
    requireNotNull(game)
    assertTrue(game.move(move.asMove()!!))
    assertEquals(resultFen, FenSerializer.serialize(game.state))
}

internal fun testMoves(
    initialFen: String,
    vararg moves: String,
    resultFen: String,
) {
    val game = Game.create(initialFen)
    requireNotNull(game)
    moves.forEach { move ->
        assertTrue(game.move(move.asMove()!!))
    }
    assertEquals(resultFen, FenSerializer.serialize(game.state))
}

internal fun testNoMove(
    initialFen: String,
    move: String
) {
    val game = Game.create(initialFen)
    requireNotNull(game)
    val moveResult = game.move(move.asMove()!!)
    assertEquals(initialFen, FenSerializer.serialize(game.state))
    assertFalse(moveResult)
}
