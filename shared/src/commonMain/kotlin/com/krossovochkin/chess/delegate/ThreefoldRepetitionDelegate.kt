package com.krossovochkin.chess.delegate

internal class ThreefoldRepetitionDelegate {

    private val map = mutableMapOf<String, Int>()

    fun check(fen: String): Boolean {
        val position = fen.substringBefore(' ')
        map[position] = (map[position] ?: 0) + 1

        return map[position] == 3
    }
}
