package com.krossovochkin.chesskt

data class Square(
    /**
     * a - 0,
     * h - 7
     */
    val file: Int,
    /**
     * 1 - 0
     * 8 - 7
     */
    val rank: Int
) {
    init {
        check(file in 0..7)
        check(rank in 0..7)
    }

    val isLight: Boolean
        get() = (file + rank) % 2 != 0

    companion object {
        fun String.asSquare(): Square? {
            val file = this.getOrNull(0)?.toFile()
                ?: return null
            val rank = this.getOrNull(1)?.toRank()
                ?: return null
            return Square(file = file, rank = rank)
        }
    }

    override fun toString(): String {
        return "${file.toFileChar()}${rank.toRankChar()}"
    }
}

internal fun Char.toFile(): Int? {
    return (this - 'a').takeIf { it in 0..7 }
}

internal fun Char.toRank(): Int? {
    return this.digitToIntOrNull()?.let { it - 1 }?.takeIf { it in 0..7 }
}

internal fun Int?.toFileChar(): Char? {
    return this?.let { 'a' + it }
}

internal fun Int?.toRankChar(): Char? {
    return this?.let { (it + 1).digitToChar() }
}
