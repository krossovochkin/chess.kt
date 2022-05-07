package com.krossovochkin.chess.pgn

import com.krossovochkin.chess.Move.Companion.asMove

object PgnParser {

    fun parse(pgn: String): PgnData {
        var current = pgn

        // remove all info about game
        current = current.replace("\\[.+]".toRegex(), "")
        // replace all comments
        current = current.replace("\\{.+}".toRegex(), "")
        // remove all move numbers
        current = current.replace("\\d*\\. ".toRegex(), "")
        // remove all new lines
        current = current.replace("\n", " ")
        // remove all additional info
        current = current.replace("[+#!?]".toRegex(), "")
        // trim leading spaces
        current = current.trim()

        return PgnData(
            moves = current.split(' ').mapNotNull { it.asMove() }
        )
    }
}
