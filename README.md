# chess.kt

[![pipeline](https://github.com/krossovochkin/chess.kt/actions/workflows/pipeline.yml/badge.svg?branch=main)](https://github.com/krossovochkin/chess.kt/actions/workflows/pipeline.yml)
[![maven](https://img.shields.io/maven-central/v/com.krossovochkin.chesskt/chesskt)](https://mvnrepository.com/artifact/com.krossovochkin.chesskt/chesskt)

Kotlin multiplatform chess backend  

[Try it](https://krossovochkin.com/apps/chesskt/)

# Integration

```kotlin
implementation("com.krossovochkin.chesskt:chesskt:x.x.x")
```

# Usage

## Create a game

Main class that holds all the game state is `Game`.  
Create it with `Game#create` method providing optional FEN of the starting position.  
If no FEN is provided then initial position will be used.

```kotlin
val game = Game.create()
```

## Moves

To make a move one should call `Game#move` with instance of the `Move` class.  
For easier usage one can use extension `String#asMove` that parses usual PGN notation move and generates corresponding instance of `Move` class.  
Calling `Game#move` will return `true` if move was successful or `false` otherwise.  

```kotlin
val isSuccess = game.move("Nbxd2".asMove())
```

## Getting piece

To get piece on the particular square of the board one should call `Game#getPiece` providing square to check.  
For easier usage one can use extension `String#asSquare` that generates corresponding instance of `Square` from file-rank string.  
Calling `Game#getPiece` will return piece if it is on the given square or `null` otherwise.  

```kotlin
val piece = game.getPiece("e4".asSquare())
```

## Getting available moves

To get available moves call `Game#availableMoves` providing optional starting square.  
List of available moves will be returned. If there are no moves empty list will be returned.  

```kotlin
val availableMoves = game.availableMoves("d4".asSquare())
```

## Game end

To get notified on game end set up callback via `Game#setGameResultCallback`.  
When game ends callback will be triggered with type of ending.

```kotlin
game.setGameResultCallback { result ->
    if (result is GameResult.Draw) {
        // do sth
    }
}
```

# Test App

Repository contains test compose multiplatform app for demo and testing purposes.  
There is no any chess moves engine there.  
The test app is simple, is not intended for production use and is not designed to have a lot of features.

<image src="/img/chess_initial.png" width=300/>
<image src="/img/chess_checkmate.png" width=300/>

Web version can be found [here](https://krossovochkin.com/apps/chesskt/)

# License
Copyright © Vasya Drobushkov  
License Apache 2.0
